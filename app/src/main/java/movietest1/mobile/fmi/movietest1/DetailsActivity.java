package movietest1.mobile.fmi.movietest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import movietest1.mobile.fmi.movietest1.async.LoadMovieDetailsTask;
import movietest1.mobile.fmi.movietest1.db.DBHelper;
import movietest1.mobile.fmi.movietest1.model.Movie;
import movietest1.mobile.fmi.movietest1.model.MovieLongDescription;
import movietest1.mobile.fmi.movietest1.model.MovieShortDescription;

/**
 * Created by user on 5.6.2017 г..
 */

public class DetailsActivity extends AppCompatActivity {

    private Movie movieEntity;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);

        MovieShortDescription m = (MovieShortDescription) getIntent().getSerializableExtra("movie");

        if(m != null) {
            final TextView title = (TextView)findViewById(R.id.textView);
            final TextView popularity = (TextView) findViewById(R.id.textView2);
            final TextView releaseDate = (TextView)findViewById(R.id.textView3);
            final TextView runtime = (TextView)findViewById(R.id.textView4);
            final TextView budget = (TextView)findViewById(R.id.textView5);
            final TextView genres = (TextView)findViewById(R.id.textView6);
            final TextView overview = (TextView)findViewById(R.id.textView7);
            final ImageView image = (ImageView)findViewById(R.id.imageView2);

            final String movieId = String.valueOf(m.getId());
            String apiKey = getString(R.string.api_key);
            String endpoint = getString(R.string.details_endpoint);
            String posterEndpoint = getString(R.string.poster_endpoint);

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            new LoadMovieDetailsTask(progressBar, new LoadMovieDetailsTask.DetailsListener() {
                @Override
                public void onLoad(MovieLongDescription movie) {
                    String s = getString(R.string.movie_title) + movie.getTitle();
                    title.setText(s);
                    s = getString(R.string.movie_popularity) + String.valueOf(movie.getPopularity());
                    popularity.setText(s);
                    s = getString(R.string.movie_release_date) + String.valueOf(movie.getReleaseDate());
                    releaseDate.setText(s);
                    s = getString(R.string.movie_runtime) + String.valueOf(movie.getRuntime());
                    runtime.setText(s);
                    s = getString(R.string.movie_budget) + String.valueOf(movie.getBudget());
                    budget.setText(s);
                    s = getString(R.string.movie_overview) + movie.getOverview();
                    overview.setText(s);
                    StringBuilder sb = new StringBuilder();
                    List<String> movieGenres = movie.getGenres();
                    int size = movieGenres.size();
                    for(int i = 0; i<size - 1; i++) {
                        sb.append(movieGenres.get(i)).append(" | ");
                    }
                    sb.append(movieGenres.get(size-1));
                    s = getString(R.string.movie_genres) + sb.toString();
                    genres.setText(s);
                    image.setImageBitmap(movie.getImage());

                    //Set movie entity
                    movieEntity = new Movie(String.valueOf(movie.getId()), movie.getTitle(), movie.getReleaseDate());
                }

                @Override
                public void onError() {
                    Toast.makeText(getApplicationContext(),R.string.error_missing_details, Toast.LENGTH_SHORT).show();
                }
            }).execute(endpoint, movieId, apiKey, posterEndpoint);


            mydb = new DBHelper(this.getApplicationContext());

            //Add button event listener
            Button addButton = (Button) findViewById(R.id.button);
            addButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean saveResult = mydb.saveMovie(movieEntity);
                    if(saveResult) {
                        Toast.makeText(getApplicationContext(),R.string.message_movie_added, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),R.string.error_unknow, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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
