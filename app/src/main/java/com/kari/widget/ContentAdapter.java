package com.kari.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * File:   ContentAdapter.java
 * Author: kari
 * Date:   17-11-12 on 22:08
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private Context mContext;
    private List<String> mData;

    public ContentAdapter(Context context, List<String> data) {
        this.mContext = context;
        mData = data;
    }

    public void addData(List<String> data) {
        if (data != null) {
            mData.addAll(data);
        }
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_content, null);
        return new ContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        holder.bindData(position, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        public ContentViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txt = (TextView) itemView.findViewById(R.id.txt);
        }

        public void bindData(int position, String data) {
            img.setImageResource(R.mipmap.ic_launcher);
            txt.setText(data);
        }
    }
}