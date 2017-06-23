package movietest1.mobile.fmi.movietest1.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import movietest1.mobile.fmi.movietest1.model.Movie;
import movietest1.mobile.fmi.movietest1.model.MovieLongDescription;
import movietest1.mobile.fmi.movietest1.model.MovieShortDescription;

/**
 * Created by user on 17.6.2017 г..
 */

public class Parser {

    public static MovieLongDescription parseToMovieLongDescription(JsonObject movieJsonObject){
        int id = movieJsonObject.get("id").getAsInt();
        String title = movieJsonObject.get("title").getAsString();
        double popularity = movieJsonObject.get("popularity").getAsDouble();
        String releaseDate = movieJsonObject.get("release_date").getAsString();
        int runtime = movieJsonObject.get("runtime").getAsInt();
        double budget = movieJsonObject.get("budget").getAsDouble();
        String overview = movieJsonObject.get("overview").getAsString();
        String posterUrl = movieJsonObject.get("poster_path").getAsString();

        JsonArray genresArray = movieJsonObject.get("genres").getAsJsonArray();
        List<String> genres = new ArrayList<String>();
        for(JsonElement e: genresArray) {
            String genreName = e.getAsJsonObject().get("name").getAsString();
            genres.add(genreName);
        }

        MovieLongDescription movie = new MovieLongDescription();
        movie.setId(id);
        movie.setTitle(title);
        movie.setPopularity(popularity);
        movie.setReleaseDate(releaseDate);
        movie.setRuntime(runtime);
        movie.setBudget(budget);
        movie.setOverview(overview);
        movie.setPosterUrl(posterUrl);
        movie.setGenres(genres);

        return movie;
    }

    public static List<MovieShortDescription> parseToMovieShortDescriptionList(JsonObject o) {
        List<MovieShortDescription> movies = new ArrayList<MovieShortDescription>();
        JsonArray jsonMovies = o.getAsJsonArray("results");
        for(JsonElement e : jsonMovies) {
            if(Utils.isValidMovieData(e)) {
                int id = e.getAsJsonObject().get("id").getAsInt();
                String title = e.getAsJsonObject().get("title").getAsString();
                int voteCount = e.getAsJsonObject().get("vote_count").getAsInt();
                double voteAverage = e.getAsJsonObject().get("vote_average").getAsDouble();
                double popularity = e.getAsJsonObject().get("popularity").getAsDouble();
                String overview = e.getAsJsonObject().get("overview").getAsString();
                String releaseDate = e.getAsJsonObject().get("release_date").getAsString();

                MovieShortDescription movie = new MovieShortDescription(id, voteCount, voteAverage, title, popularity, overview, releaseDate);
                movies.add(movie);
            }
        }
        return movies;
    }
}
