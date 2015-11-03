package com.example.vimalathithan.gracenoteexercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vimalathithan on 10/19/2015.
 */
public class TweetAdapter extends ArrayAdapter<TweetImage> {

    private Context mContext;
    private ImageLoader mImageLoader;
    private List<TweetImage> mTweetList;
    private MainActivity.SortOrder mSortOrder = MainActivity.SortOrder.Timestamp;

    public TweetAdapter(Context context, List<TweetImage> tweetList) {
        super(context, -1, tweetList);
        mContext = context;
        mTweetList = tweetList;

        final Context appContext = context.getApplicationContext();
        mImageLoader = VolleySingleton.getInstance(appContext).getImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.simplerow, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.ivTweetImage = (NetworkImageView) view.findViewById(R.id.ivTweetImage);
            holder.tvHashTag = (TextView) view.findViewById(R.id.tvHashTag);
            holder.tvTweet = (TextView) view.findViewById(R.id.tvTweet);
            holder.tvTimeStamp = (TextView) view.findViewById(R.id.tvTimeStamp);
            view.setTag(holder);
            convertView = view;
        }
        viewHolder = (ViewHolder) convertView.getTag();
        TweetImage tweetImage = getItem(position);

        viewHolder.ivTweetImage.setImageUrl(tweetImage.getUrl(), mImageLoader);
        viewHolder.tvTimeStamp.setText(DateUtils.getDateString(tweetImage.getTimeStamp()));
        viewHolder.tvHashTag.setText(tweetImage.getHashTag());
        viewHolder.tvTweet.setText(tweetImage.getTweet());

        return convertView;
    }

    @Override
    public int getCount() {
        return mTweetList.size();
    }

    /**
     * {@inheritDoc}
     */
    public TweetImage getItem(int position) {
        return mTweetList.get(position);
    }

    /**
     * Add tweet to adapter. ALWAYS call on main thread
     *
     * @param tweet
     */
    public void addToList(List<TweetImage> tweet) {
        mTweetList.addAll(tweet);
        sortBy(mSortOrder);
        notifyDataSetChanged();
    }

    /**
     * Set the list for this adapter. ALWAYS call on main thread
     *
     * @param tweetList
     */
    public void setList(List<TweetImage> tweetList) {
        mTweetList = tweetList;
        notifyDataSetChanged();
    }

    public void setSortOrder(MainActivity.SortOrder order) {
        mSortOrder = order;
        sortBy(order);
    }

    /**
     * Sort method which does the sorting
     *
     * @param order
     */
    private void sortBy(MainActivity.SortOrder order) {
        final Comparator<TweetImage> comparator;
        switch (order) {
            case Hashtag:
                comparator = new HashTagComparator();
                break;
            case Timestamp:
            default:
                comparator = new TimeStampComparator();
                break;
        }

        Collections.sort(mTweetList, comparator);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView tvHashTag;
        NetworkImageView ivTweetImage;
        TextView tvTweet;
        TextView tvTimeStamp;
    }

    private static class TimeStampComparator implements Comparator<TweetImage> {
        public int compare(TweetImage c1, TweetImage c2) {
            return c1.getTimeStamp() > c2.getTimeStamp() ? -1 :
                    (c1.getTimeStamp() < c2.getTimeStamp() ? 1 : 0);
        }
    }

    private static class HashTagComparator implements Comparator<TweetImage> {
        public int compare(TweetImage c1, TweetImage c2) {
            return c1.getHashTag().compareTo(c2.getHashTag());
        }
    }
}
