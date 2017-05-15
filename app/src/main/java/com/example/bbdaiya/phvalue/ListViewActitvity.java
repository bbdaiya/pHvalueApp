package com.example.bbdaiya.phvalue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListViewActitvity extends AppCompatActivity {
    final String LOG = ListViewActitvity.class.getSimpleName();
    RecyclerView recyclerView;
    ArrayList<Data> values = new ArrayList<>();
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_actitvity);
        values = getIntent().getParcelableArrayListExtra("list");
        Log.v(LOG, "value "+values.get(0).getValue());
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        adapter = new Adapter(getApplicationContext(), values);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_by_value_asc) {

            Collections.sort(values, new Comparator<Data>() {
                @Override
                public int compare(Data o1, Data o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });
            recyclerView.setAdapter(adapter);
            return true;
        }
        if (id == R.id.sort_by_value_desc) {
            Collections.sort(values, new Comparator<Data>() {
                @Override
                public int compare(Data o1, Data o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });
            Collections.reverse(values);
            recyclerView.setAdapter(adapter);
            return true;
        }
        if (id == R.id.show_latest) {

            recyclerView.setAdapter(adapter);
            return true;
        }
        if (id == R.id.show_oldest) {
            Collections.reverse(values);

            recyclerView.setAdapter(adapter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
