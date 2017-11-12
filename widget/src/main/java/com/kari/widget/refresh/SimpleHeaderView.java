package com.kari.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * File:   SimpleHeaderView.java
 * Author: kari
 * Date:   17-11-13 on 00:05
 */
public class SimpleHeaderView extends LinearLayout implements RefreshLayout.Header {

    private TextView txt;

    public SimpleHeaderView(Context context) {
        this(context, null);
    }

    public SimpleHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_simple_header, this, true);
        txt = (TextView) findViewById(R.id.txt);
    }

    @Override
    public void onPullStart(RefreshLayout layout) {
        txt.setText("begin");
    }

    @Override
    public void onPulling(RefreshLayout layout, float progress) {
        txt.setText(progress+"");
    }

    @Override
    public void onPullFinish(RefreshLayout layout) {
        txt.setText("finish");
    }
}
