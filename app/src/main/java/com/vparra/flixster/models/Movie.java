package com.vparra.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    double rating;
    int movieId;

    //empty constructor needed by Parcel library
    public Movie() {}
    // whoever calls this constructor will be the one to handle
    // the exception thrown by any of the jsonobject methods
    public Movie(JSONObject jsonObject, String imageURL) throws JSONException {
        //poster path is initially a url path. need to use a image loading library
        posterPath = imageURL + "/" + jsonObject.getString("poster_path");
        backdropPath = imageURL + "/" + jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray, String imageURL) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i), imageURL));
        }

        return movies;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
