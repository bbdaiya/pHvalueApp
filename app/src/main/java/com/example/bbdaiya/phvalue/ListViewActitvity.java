package com.example.bbdaiya.phvalue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ListViewActitvity extends AppCompatActivity {
    final String LOG = ListViewActitvity.class.getSimpleName();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_actitvity);
        ArrayList<Data> values = getIntent().getParcelableArrayListExtra("list");
        Log.v(LOG, values.get(0).getValue());
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        Adapter adapter = new Adapter(getApplicationContext(), values);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }
}
