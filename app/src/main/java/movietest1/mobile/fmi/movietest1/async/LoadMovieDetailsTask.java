package movietest1.mobile.fmi.movietest1.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import movietest1.mobile.fmi.movietest1.model.MovieLongDescription;
import movietest1.mobile.fmi.movietest1.utils.Parser;
import movietest1.mobile.fmi.movietest1.utils.Utils;

/**
 * Created by user on 6.6.2017 г..
 */

public class LoadMovieDetailsTask extends AsyncTask<String, Void, MovieLongDescription> {

    private DetailsListener listener;
    private ProgressBar progressBar;

    public interface DetailsListener {
        void onLoad(MovieLongDescription movie);
        void onError();
    }

    public LoadMovieDetailsTask(ProgressBar progressBar, DetailsListener listener) {
        this.listener = listener;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected MovieLongDescription doInBackground(String... params) {
        String url = "";
        String imageUrl = "";

        if(params.length>0) {
            //endpoint + movie_id
            url = params[0] + "/" + params[1] + "?api_key="+params[2] + "&language=en-US";
            imageUrl = params[3];
        }
        try{
            String jsonResponse = Utils.getResponse(url);
            if(jsonResponse != null) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject movieJsonObject = (JsonObject) parser.parse(jsonResponse);
                if(Utils.isValidMovieDetailsData(movieJsonObject)) {
                    MovieLongDescription movie = Parser.parseToMovieLongDescription(movieJsonObject);
                    String posterUrl = movie.getPosterUrl();
                    //load image
                    imageUrl = imageUrl + "/" + posterUrl;
                    Bitmap bmp = Utils.downloadPoster(imageUrl);
                    movie.setImage(bmp);
                    return movie;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(MovieLongDescription movieLongDescription) {
        progressBar.setVisibility(View.GONE);
        if(movieLongDescription != null) {
            this.listener.onLoad(movieLongDescription);
        } else {
            this.listener.onError();
        }
    }
}
