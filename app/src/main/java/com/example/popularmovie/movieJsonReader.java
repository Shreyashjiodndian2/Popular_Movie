package com.example.popularmovie;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public final class movieJsonReader {
    public static Movie getMovieDetail(Context context, String inp_String) throws JSONException {
        if (inp_String != null) {
            JSONObject jsonObject = new JSONObject(inp_String);
            String title = jsonObject.getString("original_title");
            String imgPath = jsonObject.getString("poster_path");
            String plot = jsonObject.getString("overview");
            float avg_vote = (float) jsonObject.getDouble("vote_average");
            String release_date = jsonObject.getString("release_date");
            Movie movie = new Movie(title = title, imgPath, plot, avg_vote, release_date);
            return movie;
        }
        else {
            return null;
        }
    }
}
