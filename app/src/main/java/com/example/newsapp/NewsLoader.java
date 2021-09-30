package com.example.newsapp;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        // Perform the network request, parse the response, and extract a list of news.
        List<News> newsList = QueryUtils.fetchNewsData();
        return newsList;
    }
}