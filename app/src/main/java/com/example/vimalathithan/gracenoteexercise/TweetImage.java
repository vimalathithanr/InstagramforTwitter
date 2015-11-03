package com.example.vimalathithan.gracenoteexercise;

import android.support.annotation.NonNull;

/**
 * Created by vimalathithan on 10/19/2015.
 */
public class TweetImage {
    private String hashTag;
    private String url;
    private String tweet;
    private long timeStamp;

    TweetImage(@NonNull String hashTag, String url, String tweet, long timeStamp) {
        this.hashTag = hashTag;
        this.url = url;
        this.tweet = tweet;
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
}
