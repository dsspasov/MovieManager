package movietest1.mobile.fmi.movietest1.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by user on 17.6.2017 г..
 */

public class Utils {

    public static String encodeParams(String s) {
        String encoded;
        try {
            encoded = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            encoded = s;
        }
        return encoded;
    }

    public static boolean isValidMovieData(JsonElement e) {
        if(e.isJsonNull()) {
            return false;
        } else {
            JsonObject object = e.getAsJsonObject();
            boolean isEmpty = object.get("id").isJsonNull() || object.get("title").isJsonNull() ||
                    object.get("vote_count").isJsonNull() || object.get("vote_average").isJsonNull() ||
                    object.get("popularity").isJsonNull() || object.get("overview").isJsonNull() ||
                    object.get("release_date").isJsonNull();
            return !isEmpty;
        }
    }

    public static boolean isValidMovieDetailsData(JsonObject o){
        if(o.isJsonNull()) {
            return false;
        } else {
            boolean isEmpty = o.get("id").isJsonNull() || o.get("title").isJsonNull() ||
                    o.get("popularity").isJsonNull() || o.get("release_date").isJsonNull() || o.get("runtime").isJsonNull() ||
                    o.get("budget").isJsonNull() || o.get("overview").isJsonNull() || o.get("poster_path").isJsonNull() ||
                    o.get("genres").isJsonNull() || o.get("genres").getAsJsonArray().size() == 0;

            return !isEmpty;
        }
    }

    private static HttpsURLConnection connectToURL(String path) throws IOException {
        URL url = new URL(path);
        HttpsURLConnection conn = null;
        conn = (HttpsURLConnection) url.openConnection();
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(10000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn;
    }

    public static String getResponse(String url){
        HttpsURLConnection conn = null;
        BufferedReader br = null;
        String response = null;
        try {
            conn = connectToURL(url);
            InputStream is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            response = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
            return response;
        }
    }

    public static Bitmap downloadPoster(String url) {
        HttpsURLConnection conn = null;
        InputStream is = null;
        Bitmap image = null;
        try {
            conn = connectToURL(url);
            is = conn.getInputStream();
            image = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null) {
                conn.disconnect();
            }
            return image;
        }
    }
}
