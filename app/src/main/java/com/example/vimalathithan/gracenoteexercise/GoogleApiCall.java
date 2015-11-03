package com.example.vimalathithan.gracenoteexercise;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by vimalathithan on 10/19/2015.
 */
public class GoogleApiCall {

    private static GoogleApiCall sApiCall;

    public static GoogleApiCall getInstance() {
        if (sApiCall == null) {
            sApiCall = new GoogleApiCall();
        }

        return sApiCall;
    }

    public String getImageUrl(String hashTag) throws IOException, JSONException {
        HttpGet requestGoogle = new HttpGet("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + hashTag);
        HttpClient clientGoogle = new DefaultHttpClient();
        HttpResponse responseGoogle = null;
        responseGoogle = clientGoogle.execute(requestGoogle);

        HttpEntity eg = responseGoogle.getEntity();

        String dataGoogle = null;
        dataGoogle = EntityUtils.toString(eg);

        JSONObject timeLineGoogle = new JSONObject(dataGoogle);
        JSONObject responseData = timeLineGoogle.getJSONObject("responseData");
        JSONArray results = responseData.getJSONArray("results");
        return results.getJSONObject(1).getString("url");
    }
}
