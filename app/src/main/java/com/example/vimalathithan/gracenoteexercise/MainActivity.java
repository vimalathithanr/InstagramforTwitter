package com.example.vimalathithan.gracenoteexercise;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    TweetAdapter mTweetAdapter;
    private static final String NAME = "vimalathithanr";

    private LinearLayout mListLayout;
    private Spinner mSortOrder;
    private ProgressBar mProgress;

    public enum SortOrder {Timestamp, Hashtag}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView tweetList = (ListView) findViewById(R.id.list_tweets);
        mListLayout = (LinearLayout) findViewById(R.id.llListLayout);
        mSortOrder = (Spinner) findViewById(R.id.spnSortOrder);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mTweetAdapter = new TweetAdapter(MainActivity.this, new ArrayList<TweetImage>());
        tweetList.setAdapter(mTweetAdapter);

        populateSpinner();
        toggleProgress(true);
        (new TweetRequest()).execute(NAME);
    }

    private class TweetRequest extends AsyncTask<String, JSONObject, List<TweetImage>> {

        private String mErrorString = "";

        @Override
        protected void onPreExecute() {
            toggleProgress(true);
        }

        @Override
        protected List<TweetImage> doInBackground(String... params) {
            try {
                final JSONArray timeLineArray = TwitterApiCall.getInstance().getLastTweet(params[0]);
                final List<TweetImage> tweetList = new ArrayList<>(timeLineArray.length());

                for (int i = 0; i < timeLineArray.length(); i++) {
                    final TweetImage tweet = populateTweet(timeLineArray, i);
                    if (tweet != null) {
                        tweetList.add(tweet);
                    }
                }

                return tweetList;
            } catch (OAuthCommunicationException | OAuthExpectationFailedException |
                    OAuthMessageSignerException | IOException | JSONException e) {
               mErrorString = e.getMessage();
               return new ArrayList<>(0);
            }
        }

        @Override
        protected void onPostExecute(List<TweetImage> tweetList) {
            toggleProgress(false);

            if (tweetList.size() > 0) {
                mTweetAdapter.addToList(tweetList);
            } else {
                    Toast.makeText(getApplicationContext(), "No Tweet!",
                            Toast.LENGTH_LONG).show();
            }
        }
    }

    private void populateSpinner() {
        final List<String> sortList = new ArrayList<>(SortOrder.values().length);

        for (SortOrder order : SortOrder.values()) {
            sortList.add(order.name());
        }

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sortList);

        mSortOrder.setAdapter(spinnerAdapter);

        mSortOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sortString = spinnerAdapter.getItem(position);
                mTweetAdapter.setSortOrder(SortOrder.valueOf(sortString));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTweetAdapter.setSortOrder(SortOrder.Timestamp);
            }
        });
    }

    /**
     * Toggles the list view visibility and shows progress while loading
     *
     * @param show
     */
    private void toggleProgress(final boolean show) {
        mListLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private TweetImage populateTweet(@Nullable JSONArray ja, int index) {
        try {
            final JSONObject last = ja.getJSONObject(index);
            final String timeStr = JsonUtils.getTimeStamp(last);

            final long timeStamp = DateUtils.getDateInMs(timeStr);
            final String hashTag = JsonUtils.getHashTag(last);
            final String tweet = ja.getJSONObject(index).getString("text");

            if (StringUtils.isStringValid(hashTag) && StringUtils.isStringValid(tweet)) {
                final String url = GoogleApiCall.getInstance().getImageUrl(hashTag);
                return new TweetImage(hashTag, url, tweet, timeStamp);
            } else {
                return null;
            }
        } catch (IOException | JSONException e) {
            return null;
        }
    }
}