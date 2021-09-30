package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.loader.app.LoaderManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<News>>, SwipeRefreshLayout.OnRefreshListener {

    private NewsAdapter adapter;
    private static final int LOADER_ID = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference the SwipeRefreshLayout and set set onRefreshListener and give it color
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.teal_700));

        // Find a reference to the ListView and the emptyStateTextView in the layout and set empty view on the list
        ListView listView = findViewById(R.id.list_view);
        emptyStateTextView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyStateTextView);

        // Set the adapter on the ListView
        // so the list can be populated in the user interface
        adapter = new NewsAdapter(this);
        listView.setAdapter(adapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news item.
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            News news = adapter.getItem(i);
            String url = news.getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        //check the device connectivity status if not online just show no connection message
        if (isOnline()){
            LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
        }
        else {
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        swipeRefreshLayout.setRefreshing(true);
        Toast.makeText(this, "Loading Data...", Toast.LENGTH_SHORT).show();
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        swipeRefreshLayout.setRefreshing(false);

        // Set empty state text to display "No News found."
        emptyStateTextView.setText(R.string.no_news);

        // Clear the adapter of previous data
        adapter.clear();

        // If there is a valid list of News, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }

    @Override
    public void onRefresh() {
        if (isOnline()) {
            LoaderManager.getInstance(this).restartLoader(LOADER_ID, null, this);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}