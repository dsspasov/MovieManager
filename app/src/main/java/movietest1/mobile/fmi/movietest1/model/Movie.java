package movietest1.mobile.fmi.movietest1.model;

/**
 * Created by user on 7.6.2017 г..
 */

public class Movie {

    private int id;
    private String movieId;
    private String title;
    private String year;

    public Movie(){

    }

    public Movie(String movieId, String title, String year) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
    }

    public Movie(int id, String movieId, String title, String year) {
        this(movieId, title, year);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Title='" + title + '\'' +
                ", year='" + year;
    }
}
