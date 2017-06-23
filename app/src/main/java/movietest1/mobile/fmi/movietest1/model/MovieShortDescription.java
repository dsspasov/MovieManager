package movietest1.mobile.fmi.movietest1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by user on 5.6.2017 г..
 */

public class MovieShortDescription implements Serializable {

    /** id */
    private int id;
    /** vote_count */
    private int voteCount;
    /** vote_average */
    private double voteAverage;
    /** title */
    private String title;
    /** popularity */
    private double popularity;
    /** overview */
    private String overview;
    /** release_date */
    private String releaseDate;

    public MovieShortDescription(){

    }

    public MovieShortDescription(int id, int voteCount, double voteAverage, String title, double popularity, String overview, String releaseDate){
        this.id = id;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "MovieShortDescription{" +
                "id=" + id +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
