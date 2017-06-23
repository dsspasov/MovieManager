package movietest1.mobile.fmi.movietest1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import movietest1.mobile.fmi.movietest1.R;
import movietest1.mobile.fmi.movietest1.db.DBHelper;
import movietest1.mobile.fmi.movietest1.model.Movie;

/**
 * Created by user on 9.6.2017 г..
 */

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchListHolder> {

    private Context appContext;
    private List<Movie> favouriteMovies;

    public WatchListAdapter(List<Movie> favouriteMovies, Context appContext) {
        this.favouriteMovies = favouriteMovies;
        this.appContext = appContext;
    }

    @Override
    public WatchListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watchlist_row, parent, false);

        return new WatchListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WatchListHolder holder, int position) {
        Movie movie = favouriteMovies.get(position);
        holder.id = movie.getId();
        holder.movieId = movie.getMovieId();
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());

        //Get current movie
        final int pos1 = holder.getAdapterPosition();
        final int pos2 = holder.getLayoutPosition();
        //Connect to DB
        final DBHelper db = new DBHelper(this.appContext);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteMovie(favouriteMovies.get(pos1));
                favouriteMovies.remove(pos1);
                notifyItemRemoved(pos1);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteMovies.size();
    }

    public class WatchListHolder extends RecyclerView.ViewHolder{
        private int id;
        private String movieId;
        private TextView title;
        private TextView year;
        private ImageButton deleteButton;

        public WatchListHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            year = (TextView)itemView.findViewById(R.id.releaseDate);
            deleteButton = (ImageButton)itemView.findViewById(R.id.deleteButton);
        }
    }
}
