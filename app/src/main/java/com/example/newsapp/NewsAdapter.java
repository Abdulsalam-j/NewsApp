package com.example.newsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context) {
        super(context, 0, new ArrayList<>());
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        //Get the current news item
        News currentNews = getItem(position);

        //set text for section
        TextView section = convertView.findViewById(R.id.section_tv);
        section.setText(currentNews.getSection());

        //set text for author
        TextView author = convertView.findViewById(R.id.author_tv);
        author.setText(currentNews.getAuthor());

        //set text for title
        TextView title = convertView.findViewById(R.id.title_tv);
        title.setText(currentNews.getTitle());

        // Create a new Date object from the json  response data.
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date;
        try {
            date = simpleDateFormat.parse(currentNews.getPublishDate());
        } catch (ParseException e) {
            Log.e("News Adapter", "Error parsing date!!");
            return null;
        }

        //Store formatted date in string like "Jan 7, 2018"
        String formattedDate = formatDate(date);

        //Store formatted time in a string like "7:55 AM"
        String formattedTime = formatTime(date);

        //set the date
        TextView date_tv = convertView.findViewById(R.id.date_tv);
        date_tv.setText(formattedDate);

        //set the time
        TextView time_tv = convertView.findViewById(R.id.time_tv);
        time_tv.setText(formattedTime);

        return convertView;
    }

    //Return formatted date using SimpleDateFormat object
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.US);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(dateObject);
    }

    //Return formatted time using SimpleDateFormat object
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
        timeFormat.setTimeZone(TimeZone.getDefault());
        return timeFormat.format(dateObject);
    }
}