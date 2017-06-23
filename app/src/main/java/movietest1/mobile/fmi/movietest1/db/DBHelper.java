package movietest1.mobile.fmi.movietest1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import movietest1.mobile.fmi.movietest1.model.Movie;

/**
 * Created by user on 7.6.2017 г..
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MoviesDB.db";
    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "id";
    public static final String MOVIES_COLUMN_MOVIE_ID = "movie_id";
    public static final String MOVIES_COLUMN_TITLE = "title";
    public static final String MOVIES_COLUMN_YEAR = "year";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + MOVIES_TABLE_NAME + " ( " +
                MOVIES_COLUMN_ID + " integer primary key, " +
                MOVIES_COLUMN_MOVIE_ID + " integer, " +
                MOVIES_COLUMN_TITLE + " text, " +
                MOVIES_COLUMN_YEAR + " text )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if EXISTS " + MOVIES_TABLE_NAME);
        onCreate(db);
    }

    public boolean saveMovie(Movie movie) {
        if(movie != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MOVIES_COLUMN_MOVIE_ID, movie.getMovieId());
            contentValues.put(MOVIES_COLUMN_TITLE, movie.getTitle());
            contentValues.put(MOVIES_COLUMN_YEAR, movie.getYear());
            db.insert(MOVIES_TABLE_NAME, null, contentValues);
            db.close();
            return true;
        } else {
            return false;
        }
    }

    public void getMovie(int movieId) {
    }

    public List<Movie> getMovies(){
        List<Movie> movies = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+ MOVIES_COLUMN_ID + ", "
                                            + MOVIES_COLUMN_MOVIE_ID + ", "
                                            + MOVIES_COLUMN_TITLE + ", "
                                            + MOVIES_COLUMN_YEAR + " from " + MOVIES_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            int id = res.getInt(res.getColumnIndex(MOVIES_COLUMN_ID));
            //Log.d("id:", String.valueOf(id));
            String movieId = res.getString(res.getColumnIndex(MOVIES_COLUMN_MOVIE_ID));
            String title = res.getString(res.getColumnIndex(MOVIES_COLUMN_TITLE));
            //Log.d("title", title);
            String year = res.getString(res.getColumnIndex(MOVIES_COLUMN_YEAR));
            Movie m = new Movie(id, movieId, title, year);
            //Log.d("moviem:", m.toString());
            movies.add(m);
            res.moveToNext();
        }
        db.close();
        return movies;
    }

    public boolean deleteMovie(Movie m) {
        if(m != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            int affectedRows = db.delete(MOVIES_TABLE_NAME,
                    MOVIES_COLUMN_ID + " = ?  AND " + MOVIES_COLUMN_MOVIE_ID+ " = ?",
                    new String[]{String.valueOf(m.getId()), m.getMovieId()});
            db.close();
            return affectedRows > 0;
        } else {
            return false;
        }
    }
}
