package in.searchbook;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul1993 on 12/3/2017.
 */

public class ListBookFragment extends Fragment implements AdapterView.OnItemClickListener{
  String urlString;
  View view;
  ListView listView;
  String URL;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    view = inflater.inflate(R.layout.book_list, container, false);
    listView = (ListView) view.findViewById(R.id.list_item);
    listView.setOnItemClickListener(this);
//
    URL = getArguments().getString("URL");
    FetchJsonAsyncTask fetchJsonAsyncTask = new FetchJsonAsyncTask();
    fetchJsonAsyncTask.execute();
    return view;
  }

  public void updateUi(List<Book> books) {
    ListBookAdapter adapter = new ListBookAdapter(getActivity(),0, books);
    listView.setAdapter(adapter);
//    urlString = books.get(5).webReaderLink;

  }

  public class FetchJsonAsyncTask extends AsyncTask<URL, Void, List<Book>> {

    @Override
    protected void onPostExecute(List<Book> books) {
      super.onPostExecute(books);

      updateUi(books);
    }

    @Override
    protected List<Book> doInBackground(URL... urls) {

      String jsonResponse = "";
      URL url = createUrl(URL);

      try {
        jsonResponse = makeHttpRequest(url);
      } catch (IOException e) {
        e.printStackTrace();
      }


      List<Book> bookList = extractJsonFeature(jsonResponse);

      return bookList;
    }

    private URL createUrl(String urlString) {
      URL url = null;
      if (url == null) {
        try {
          url = new URL(urlString);
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      }
      return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
      String outputString = null;
      InputStream inputStream = null;
      HttpURLConnection urlConnection = null;
      try {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(15000);
        urlConnection.setReadTimeout(20000);
        urlConnection.connect();
        inputStream = urlConnection.getInputStream();
        outputString = readFromStream(inputStream);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (urlConnection != null) {
          urlConnection.disconnect();
        }
        if (inputStream != null) {
          inputStream.close();
        }
      }
      return outputString;
    }

    private String readFromStream(InputStream inputStream) {
      StringBuilder output = new StringBuilder();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      try {
        String line = bufferedReader.readLine();
        while (line != null) {
          output.append(line);
          line = bufferedReader.readLine();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      return output.toString();
    }

    private List<Book> extractJsonFeature(String jsonResponse) {
      List<Book> books = new ArrayList<>();
      String title = null;
      String author = null;
      String webReaderLink = null;
      try {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray item = jsonObject.getJSONArray("items");
        for (int i = 0; i < item.length(); i++) {
          JSONObject bookDetail = item.getJSONObject(i);
          JSONObject volumeInfo = bookDetail.getJSONObject("volumeInfo");
          title = volumeInfo.getString("title");
          JSONArray authors = volumeInfo.getJSONArray("authors");
          for (int j = 0; j < authors.length(); j++) {
            author = authors.getString(0);
          }
          webReaderLink = bookDetail.getString("webReaderLink");
          books.add(new Book(title,author, webReaderLink));
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
      return books;
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//    urlString = "http://play.google.com/books/reader?id=1Y7jY06XNF4C&hl=&printsec=frontcover&source=gbs_api";
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
    startActivity(browserIntent);
    Toast.makeText(parent.getContext(),Integer.toString(position),Toast.LENGTH_SHORT).show();
  }
}
