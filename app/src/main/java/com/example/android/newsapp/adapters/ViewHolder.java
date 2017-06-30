package com.example.android.newsapp.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.android.newsapp.R;

/**
 * Created by HiirMii on 2017-06-28.
 */

public class ViewHolder {

    TextView sectionTextView;
    TextView titleTextView;
    TextView dateTextView;
    TextView timeTextView;

    public ViewHolder(@NonNull View view) {
        this.sectionTextView = (TextView) view.findViewById(R.id.article_section);
        this.titleTextView = (TextView) view.findViewById(R.id.article_title);
        this.dateTextView = (TextView) view.findViewById(R.id.article_date);
        this.timeTextView = (TextView) view.findViewById(R.id.article_time);
    }
}
