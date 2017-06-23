package movietest1.mobile.fmi.movietest1.async;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import movietest1.mobile.fmi.movietest1.model.MovieShortDescription;
import movietest1.mobile.fmi.movietest1.utils.Parser;
import movietest1.mobile.fmi.movietest1.utils.Utils;

/**
 * Created by user on 5.6.2017 г..
 */

public class LoadMovieTask extends AsyncTask<String, Void, List<MovieShortDescription>>{
    public interface Listener{
        void onLoad(List<MovieShortDescription> movies);
        void onError();
    }

    private Listener listener;
    private ProgressBar progressBar;

    public LoadMovieTask(ProgressBar progressBar, Listener listener){
        this.progressBar = progressBar;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<MovieShortDescription> doInBackground(String... params) {
        String url = "";
        if(params.length>0) {
            String encoded = Utils.encodeParams(params[1]);
            url = params[0] + "?api_key=" + params[2] + "&language=en-US&page=1&query=" + encoded;
        }
        try{
            String response = Utils.getResponse(url);
            if(response != null) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject responseJsonObject = (JsonObject) parser.parse(response);
                int page = responseJsonObject.get("page").getAsInt();
                int totalResults = responseJsonObject.get("total_results").getAsInt();
                if(page <= 0 || totalResults <= 0) {
                    return null;
                } else {
                    List<MovieShortDescription> movies = Parser.parseToMovieShortDescriptionList(responseJsonObject);
                    return movies;
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
    protected void onPostExecute(List<MovieShortDescription> movies) {
        progressBar.setVisibility(View.GONE);
        if(movies != null && movies.size() != 0) {
            listener.onLoad(movies);
        } else {
            listener.onError();
        }
    }
}
