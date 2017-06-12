package com.example.zziboo.personalproject01;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class SiteViewActivity extends AppCompatActivity {
    ListView listView;
    dbhelper mydb;
    Vector<Dataset> datasetVector;
    String selectURL;
    int resultCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_view);
        mydb = new dbhelper(this);

        Cursor res = mydb.getData();
        datasetVector = new Vector<>();

        while(res.moveToNext()) {
            datasetVector.add(new Dataset(res.getString(0), res.getString(1), res.getString(2)));
        }

        listView = (ListView) findViewById(R.id.urlList);
        CustomList adapter = new CustomList(SiteViewActivity.this);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.urlList);
                selectURL = txt.getText().toString();
            }
        });
    }

    public class CustomList extends BaseAdapter {
        private final Activity context;
        public CustomList(Activity context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return datasetVector.size();
        }

        @Override
        public Object getItem(int position) {
            return datasetVector.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listitem, null, true);
            TextView titleitem = (TextView) rowView.findViewById(R.id.titleList);
            TextView urlitem = (TextView) rowView.findViewById(R.id.urlList);
            TextView dayitem= (TextView) rowView.findViewById(R.id.dayList);
            titleitem.setText(datasetVector.get(position).getTitle());
            urlitem.setText(datasetVector.get(position).getURL());
            dayitem.setText(datasetVector.get(position).getDay());
            return rowView;
        }
    }

    public void onClick(View view){
        if(selectURL == ""){
            Toast.makeText(getApplicationContext(), "목록을 선택해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("INPUT_TEXT", selectURL);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void close(View view){
        finish();
    }
}
