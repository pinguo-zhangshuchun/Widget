package com.kari.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kari.widget.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mAdapter = new ContentAdapter(this, loadData(0, 15));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private List<String> loadData(int start, int count) {
        List<String> data = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            data.add(start + i + "");
        }
        return data;
    }

}
