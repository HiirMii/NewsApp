package com.example.android.newsapp.utility;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.example.android.newsapp.adapters.News;

import java.util.List;

/**
 * Created by HiirMii on 2017-06-29.
 */

/**
 * Loads a list of news articles by using an AsyncTask to perform the
 * network request to the Guardian API.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String url;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param currentUrl to load data from
     */
    public NewsLoader(Context context, String currentUrl) {
        super(context);
        url = currentUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (url == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news articles.
        List<News> news = Utils.fetchNewsData(url);
        return news;
    }
}
