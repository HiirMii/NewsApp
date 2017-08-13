package com.example.android.newsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.newsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by HiirMii on 2017-06-28.
 */

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each news article
 * in the data source (a list of {@link News} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param news    is the list of news articles, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the news article at the given position
     * in the list of news.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /**
         * ViewHolder for the News layout views.
         */
        ViewHolder viewHolder;

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Find the news article at the given position in the list of news
        News currentNews = getItem(position);

        // Display the section of the current news article in that TextView using viewHolder
        viewHolder.sectionTextView.setText(currentNews.getSection());

        // Display the title of the current news article in that TextView using viewHolder
        viewHolder.titleTextView.setText(currentNews.getTitle());

        // Display the publish date of the current news article in that TextView using viewHolder
        viewHolder.dateTextView.setText(formatDate(currentNews.getDate()));

        // Display the publish time of the current news article in that TextView using viewHolder
        viewHolder.timeTextView.setText(formatTime(currentNews.getDate()));

        return convertView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date string.
     */
    private String formatDate(String dateObject) {

        String date = null;

        try {
            Date object = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateObject);
            date = new SimpleDateFormat("LLL dd, yyyy").format(object);

        } catch (ParseException e) {
            Log.e(LOG_TAG, "Problem with parsing dateObject to date occurred.");
        }

        return date;
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date string.
     */

    private String formatTime(String dateObject) {

        String date = null;

        try {
            Date object = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateObject);
            date = new SimpleDateFormat("h:mm a").format(object);

        } catch (ParseException e) {
            Log.e(LOG_TAG, "Problem with parsing dateObject to time occurred.");
        }

        return date;
    }
}
