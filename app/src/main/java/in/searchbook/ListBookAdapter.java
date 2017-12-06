package in.searchbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rahul1993 on 12/4/2017.
 */

class ListBookAdapter extends ArrayAdapter {

  ListBookAdapter(@NonNull Context context, int resource, @NonNull List objects) {
    super(context, resource, objects);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    if(convertView == null){
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
    }
    Book book = (Book)getItem(position);
    TextView titleView = (TextView) convertView.findViewById(R.id.title);
    TextView authorView = (TextView) convertView.findViewById(R.id.author);
    if(book != null) {
      titleView.setText(book.title);
      authorView.setText(book.authorName);
    }
    return convertView;
  }
}
