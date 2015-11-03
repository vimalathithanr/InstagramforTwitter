package com.example.vimalathithan.gracenoteexercise;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by vimalathithan on 10/19/2015.
 */
public class TwitterApiCall {

    final static String URL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
    final static String consumerKey = "KzlklF0wH75wIm04fqJvOvnER";
    final static String consumerSecret = "cT0f071krZr28waybJGD5ZvDUaawlpnVQ1di13CyHVSEw9Kyn5";
    final static String accessToken = "238082004-NypGHOmWkI8pLaVX1mI9VFbmX9M82PJeIi9YkxmK";
    final static String accessSecret = "b4NvCSKHF8L1fntMk2XeD1LFviKZR4poYj64dQNNkGeJr";

    private static TwitterApiCall sApiCall;

    public static TwitterApiCall getInstance() {
        if (sApiCall == null) {
            sApiCall = new TwitterApiCall();
        }

        return sApiCall;
    }

    public JSONArray getLastTweet(String username) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, IOException, JSONException {
        JSONArray timeLine = null;

        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, accessSecret);

        StringBuilder url = new StringBuilder(URL);
        url.append(username);

        HttpGet request = new HttpGet(url.toString());
        consumer.sign(request);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        response = client.execute(request);

        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
            HttpEntity e = response.getEntity();
            String data = null;
            data = EntityUtils.toString(e);
            timeLine = new JSONArray(data);
            return timeLine;
        } else {
            return null;
        }
    }
}
