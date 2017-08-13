package com.example.android.newsapp.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.adapters.News;
import com.example.android.newsapp.adapters.NewsAdapter;
import com.example.android.newsapp.utility.NewsLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>> {

    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * URL for news data from Guardian API
     */
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?";

    /**
     * API key for Guardian API
     */
    private static final String API_KEY = "test";

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /** Adapter for the list of news articles */
    private NewsAdapter adapter;

    /**
     * List of news articles
     */
    private ListView newsListView;

    /** TextView that is displayed when the list is empty */
    private TextView emptyStateTextView;

    /**
     * ProgressBar that is displayed when the data is loaded
     */
    private ProgressBar loadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Find a reference to the {@link ListView} in the layout
        newsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of news articles as input
        adapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(adapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with the selected article.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Find the current book that was clicked on
                News currentNews = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the earthquake URI
                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(webIntent);
            }
        });

        // Find the reference to the progress bar in a layout
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        // Find the reference to the empty text view in a layout and set empty view
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyStateTextView);

        if (isConnected()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String chooseCategory = sharedPreferences.getString(
                getString(R.string.settings_choose_category_key),
                getString(R.string.settings_choose_category_default));

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder builder = baseUri.buildUpon();

        // Section query added only when we want to filter articles
        if (!chooseCategory.contains("all")) {
            builder.appendQueryParameter("section", chooseCategory);
        }
        builder.appendQueryParameter("order-by", orderBy);
        builder.appendQueryParameter("api-key", API_KEY);

        Log.e(LOG_TAG, builder.toString());

        return new NewsLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        if (isConnected()) {

            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Set empty state text to display "No articles found."
            emptyStateTextView.setText(R.string.no_news);

            // Clear the adapter of previous earthquake data
            adapter.clear();

            // If there is a valid list of {@link News}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (news != null && !news.isEmpty()) {
                adapter.addAll(news);
            }

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Helper method to check network connection
    public boolean isConnected() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // Return network connection info as boolean value
        return (networkInfo != null && networkInfo.isConnected());
    }
}
