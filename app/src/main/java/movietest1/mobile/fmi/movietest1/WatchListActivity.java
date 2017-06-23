package movietest1.mobile.fmi.movietest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import movietest1.mobile.fmi.movietest1.adapter.DividerItemDecoration;
import movietest1.mobile.fmi.movietest1.adapter.WatchListAdapter;
import movietest1.mobile.fmi.movietest1.db.DBHelper;
import movietest1.mobile.fmi.movietest1.model.Movie;

/**
 * Created by user on 7.6.2017 г..
 */

public class WatchListActivity extends AppCompatActivity {

    private List<Movie> movieEntities = new ArrayList<Movie>();
    //private CollectionAdapter adapter;
    private WatchListAdapter watchListAdapter;
    private RecyclerView recyclerView;
    private DBHelper db;

    //TODO implement other on.* methods from the lifecycle of the activity.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchlist_activity);
        //Fetch data from DB
        db = new DBHelper(this.getApplicationContext());
        movieEntities = db.getMovies();
        //Adapter
        watchListAdapter = new WatchListAdapter(movieEntities, this.getApplicationContext());
        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(watchListAdapter);
        //Notification
        watchListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search :
                Intent searchIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(searchIntent, 0);
                return true;
            case R.id.collection:
                Intent collectionIntent = new Intent(getApplicationContext(), WatchListActivity.class);
                startActivityForResult(collectionIntent, 0);
                return true;
            default:
                super.onOptionsItemSelected(item);
                return true;
        }
    }
}
