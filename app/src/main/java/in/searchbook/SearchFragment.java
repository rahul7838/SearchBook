package in.searchbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by rahul1993 on 12/3/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener{
  String queryText;
  EditText editText;
  Button searchButton;
  View view;
  onSearchClickListener listener;
  private static final String TAG = SearchFragment.class.getSimpleName();
  String urlString = "https://www.googleapis.com/books/v1/volumes?maxResults=7&q=";


  public SearchFragment(){
      // empty constructor is required.
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      listener = (onSearchClickListener) context;
    } catch (ClassCastException e){
        throw new ClassCastException(context.toString() + " must implement onSearchClickListener");
    }
  }

  public interface onSearchClickListener{
    void onSearchClick(String string);
  }

  public static SearchFragment newInstance() {
    return new SearchFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    view = inflater.inflate(R.layout.search_bar,container,false);
    searchButton = (Button) view.findViewById(R.id.search_button);
    editText = (EditText) view.findViewById(R.id.edittext_search_book);
    searchButton.setOnClickListener(this);
    return view;
  }

  @Override
  public void onClick(View v) {
    queryText = editText.getText().toString();
    Log.i(TAG, queryText);
    urlString = urlString + queryText;
    queryText = "";
//        Log.i(TAG, urlString);
    listener.onSearchClick(urlString);
  }
}

