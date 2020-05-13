package com.example.popularmovie;

public class Movie {
    private String mTitle;
    private String mImgUrl;
    private String mPlot;
    private float mVote_rating = 0;
    private String mReleaseDate;
    public Movie(String title, String img_url, String plot, float avg_vote, String releaseDate){
        this.mTitle = title;
        this.mImgUrl = img_url.replace("/","");
        this.mPlot = plot;
        this.mVote_rating = avg_vote;
        this.mReleaseDate = releaseDate;
    }
    public String getTitle(){
        if (mTitle == null){
            return "Not Available";
        }
        return mTitle;
    }
    public String getmImgUrl(){
        if (mImgUrl == null){
            return "Image not Available";
        }
        return mImgUrl;
    }
    public String getmPlot(){
        if (mPlot == null){
            return "Plot not Available";
        }
        return mPlot;
    }
    public float getmVote_rating(){
        return mVote_rating;
    }

    public void setmTitle(String mTitle) {
        if (mTitle != null) {
            this.mTitle = mTitle;
        }
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public void setmPlot(String mPlot) {
        this.mPlot = mPlot;
    }

    public void setmVote_rating(float mVote_rating) {
        this.mVote_rating = mVote_rating;
    }
}
