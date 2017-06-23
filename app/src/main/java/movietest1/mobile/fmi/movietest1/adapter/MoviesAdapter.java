package movietest1.mobile.fmi.movietest1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import movietest1.mobile.fmi.movietest1.R;
import movietest1.mobile.fmi.movietest1.model.MovieShortDescription;

/**
 * Created by user on 4.6.2017 г..
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<MovieShortDescription> moviesList;
    private Context context;

    public MoviesAdapter(List<MovieShortDescription> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieShortDescription movie = moviesList.get(position);

        holder.title.setText(context.getString(R.string.movie_title) + movie.getTitle());
        holder.year.setText(context.getString(R.string.movie_release_date) + movie.getReleaseDate());
        holder.voteCount.setText(context.getString(R.string.movie_votes) + String.valueOf(movie.getVoteCount()));
        holder.voteAverage.setText(context.getString(R.string.movie_average) + String.valueOf(movie.getVoteAverage()));
        holder.popularity.setText(context.getString(R.string.movie_popularity) +String.valueOf(movie.getPopularity()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, year, voteCount, voteAverage, popularity;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            year = (TextView) view.findViewById(R.id.year);
            voteCount = (TextView) view.findViewById(R.id.voteCount);
            voteAverage = (TextView) view.findViewById(R.id.voteAverage);
            popularity = (TextView) view.findViewById(R.id.popularity);
        }
    }
}
