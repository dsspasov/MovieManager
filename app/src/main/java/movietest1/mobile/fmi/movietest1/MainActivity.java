package movietest1.mobile.fmi.movietest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import movietest1.mobile.fmi.movietest1.adapter.DividerItemDecoration;
import movietest1.mobile.fmi.movietest1.adapter.MoviesAdapter;
import movietest1.mobile.fmi.movietest1.async.LoadMovieTask;
import movietest1.mobile.fmi.movietest1.listener.RecyclerTouchListener;
import movietest1.mobile.fmi.movietest1.model.MovieShortDescription;

public class MainActivity extends AppCompatActivity {

    private List<MovieShortDescription> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Button searchButton = (Button)findViewById(R.id.search_button);

        searchButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //make request for data
                movieList.clear();
                String searchTitle = ((EditText)findViewById(R.id.editText)).getText().toString();
                prepareMovieData(searchTitle);
            }
        });

        if(savedInstanceState != null) {
           movieList = (List<MovieShortDescription>)savedInstanceState.getSerializable("movieList");
        }
        mAdapter = new MoviesAdapter(movieList, this.getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        if(!movieList.isEmpty()) {
            mAdapter.notifyDataSetChanged();
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MovieShortDescription movie = movieList.get(position);

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("movieList", (Serializable) movieList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    private void prepareMovieData(String title) {
        String apiKey = getString(R.string.api_key);
        String endpoint = getString(R.string.search_endpoint);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new LoadMovieTask(progressBar, new LoadMovieTask.Listener() {
            @Override
            public void onLoad(List<MovieShortDescription> movies) {
                for(MovieShortDescription m : movies) {
                    movieList.add(m);
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(),R.string.error_missing_data, Toast.LENGTH_SHORT).show();
            }
        }).execute(endpoint, title, apiKey);
    }
}
