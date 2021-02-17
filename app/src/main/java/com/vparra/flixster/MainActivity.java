package com.vparra.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.vparra.flixster.adapters.MovieAdapter;
import com.vparra.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing";
    public static final String TAG = "MainActivity";
    public static final String IMAGE_CONFIG_URL = "https://api.themoviedb.org/3/configuration";
    public static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final int W342_POSTER_SIZE = 3;

    List<Movie> movies;
    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        //set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        //set a layout manager on RV
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        //set params for queries
        RequestParams params = new RequestParams();
        params.put("api_key", API_KEY);

        //get all images sizes
        client.get(IMAGE_CONFIG_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    Log.d(TAG, "onSuccess");
                    JSONObject images = jsonObject.getJSONObject("images");
                    String baseUrl = images.getString("secure_base_url");
                    String posterSize = images.getJSONArray("poster_sizes")
                                        .getString(W342_POSTER_SIZE);
                    imagePath = baseUrl + posterSize;
                    Log.i(TAG, "Image path: " + imagePath);
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception when fetching image config", e);
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });


        //get all now playing movies
        client.get(NOW_PLAYING_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results, imagePath));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception when fetching now playing movies", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
    }
}