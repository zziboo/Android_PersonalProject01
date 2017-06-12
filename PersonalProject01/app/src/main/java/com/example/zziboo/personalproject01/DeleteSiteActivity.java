package com.example.zziboo.personalproject01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

public class DeleteSiteActivity extends AppCompatActivity {
    ListView listView;
    dbhelper mydb;
    Vector<Dataset> datasetVector;
    String selectItem;
    Button delbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_site);
        selectItem = "";

        mydb = new dbhelper(this);
        Cursor res = mydb.getData();
        datasetVector = new Vector<>();

        while(res.moveToNext()) {
            datasetVector.add(new Dataset(res.getString(0), res.getString(1), res.getString(2)));
        }

        listView = (ListView) findViewById(R.id.delList);
        final CustomList adapter = new CustomList(this);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.titleList);
                selectItem = txt.getText().toString();
            }
        });

        delbtn = (Button)findViewById(R.id.delbtn);
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectItem == "") {
                    Toast.makeText(getApplicationContext(), "목록을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), selectItem + "을 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                mydb.deleteUrl(selectItem);

                datasetVector.clear();
                Cursor tmp = mydb.getData();

                while(tmp.moveToNext()) {
                    datasetVector.add(new Dataset(tmp.getString(0), tmp.getString(1), tmp.getString(2)));
                }

                listView.setAdapter(adapter);
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
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    }
    public void close(View view){
        finish();
    }
}
