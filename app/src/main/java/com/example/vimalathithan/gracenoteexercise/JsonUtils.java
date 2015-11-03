package com.example.vimalathithan.gracenoteexercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vimalathithan on 10/19/2015.
 */
public class JsonUtils {
    private static final String RESULT = "result";
    private static final String ENTITIES = "entities";
    private static final String HASHTAGS = "hashtags";
    private static final String TEXT = "text";
    private static final String CREATED_AT = "created_at";
    private static final String RESPONSE_DATA = "responseData";
    private static final String RESULTS = "results";
    private static final String URL = "url";


    @Nullable
    private static JSONObject getAsJsonObj(@Nullable JSONObject jo, @NonNull String name) {
        try {
            return jo != null ? jo.getJSONObject(name) : null;
        } catch (JSONException ex) {
            return null;
        }
    }

    @Nullable
    private static JSONObject getAsJsonObjFromArray(@NonNull JSONArray ja, int index) {
        try {
            return ja.length() > 0 ? ja.getJSONObject(index) : null;
        } catch (JSONException ex) {
            return null;
        }
    }

    @Nullable
    private static String getAsString(@Nullable JSONObject jo, @NonNull String name) {
        try {
            return jo != null ? jo.getString(name) : null;
        } catch (JSONException ex) {
            return null;
        }
    }

    @NonNull
    private static JSONArray getAsJsonArray(JSONObject jo, String name) {
        final JSONArray jsonArray = new JSONArray();
        try {
            return jo != null ? jo.getJSONArray(name) : jsonArray;
        } catch (JSONException ex) {
            return jsonArray;
        }
    }

    @Nullable
    public static String getTimeStamp(@Nullable JSONObject jo) {
        return jo != null ? getAsString(jo, CREATED_AT) : null;
    }

    @Nullable
    private static JSONObject getResult(@Nullable JSONObject jo) {
        return getAsJsonObj(jo, RESULT);
    }

    @Nullable
    private static JSONObject getEntities(@Nullable JSONObject jo) {
        return jo != null ? getAsJsonObj(jo, ENTITIES) : null;
    }

    @NonNull
    private static JSONArray getHashTagsArray(@Nullable JSONObject jo) {
        final JSONObject entities = getEntities(jo);
        return entities != null ? getAsJsonArray(entities, HASHTAGS) : null;
    }

    @Nullable
    public static String getHashTag(@Nullable JSONObject jo) {
        final JSONArray hashTagArray = getHashTagsArray(jo);
        final JSONObject hashTagObject = getAsJsonObjFromArray(hashTagArray, 0);
        return getAsString(hashTagObject, TEXT);
    }

}
