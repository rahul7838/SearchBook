package in.searchbook;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SearchFragment.onSearchClickListener{
  private static final String TAG = MainActivity.class.getSimpleName();
  private static final int CONTENT_VIEW_ID = 10101010;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    SearchFragment searchFragment = new SearchFragment();

    transaction.add(R.id.fragment_container, searchFragment);
    transaction.commit();
  }

  @Override
  public void onSearchClick(String string) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    ListBookFragment listBookFragment = new ListBookFragment();
    Bundle bundle = new Bundle();
    bundle.putString("URL", string);
    listBookFragment.setArguments(bundle);
    fragmentTransaction.replace(R.id.fragment_container,listBookFragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
}