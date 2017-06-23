package movietest1.mobile.fmi.movietest1.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 6.6.2017 г..
 */

public class MovieLongDescription implements Serializable{

    private int id;
    private String title;
    private double popularity;
    private String releaseDate;
    private int runtime;
    private double budget;
    private List<String> genres;
    private String overview;
    private String posterUrl;
    private Bitmap image;

    public MovieLongDescription(){

    }

    public MovieLongDescription(int id, String title, double popularity, String releaseDate, int runtime, double budget, List<String> genres, String overview, String posterUrl) {
        this.id = id;
        this.title = title;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.budget = budget;
        this.genres = genres;
        this.overview = overview;
        this.posterUrl = posterUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
