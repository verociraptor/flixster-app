package com.vparra.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    String posterPath;
    String title;
    String overview;

    // whoever calls this constructor will be the one to handle
    // the exception thrown by any of the jsonobject methods
    public Movie(JSONObject jsonObject, String imageURL) throws JSONException {
        //poster path is initially a url path. need to use a image loading library
        posterPath = imageURL + "/" + jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray, String imageURL) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i), imageURL));
        }

        return movies;
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