package com.example.zziboo.personalproject01;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewSiteActivity extends AppCompatActivity {
    TextView titletxt;
    TextView urltxt;
    TextView daytxt;
    private dbhelper mydb;
    Date date;
    SimpleDateFormat dayFormat;
    String todayDate;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_site);
        date = new Date();
        dayFormat = new SimpleDateFormat("yy-MM-dd");
        todayDate = dayFormat.format(date);

        mydb = new dbhelper(this);

        res = mydb.getData();

        titletxt = (TextView) findViewById(R.id.addTitle);
        urltxt = (TextView) findViewById(R.id.addUrl);
        daytxt = (TextView) findViewById(R.id.addDay);

        daytxt.setText(todayDate);
    }

    public void insertClick(View view){
        if(titletxt.getText().toString().getBytes().length <= 0 ||
                urltxt.getText().toString().getBytes().length <= 0){
            Toast.makeText(getApplicationContext(), "제목과 URL을 작성하여 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mydb.insertURL(titletxt.getText().toString(), urltxt.getText().toString(), daytxt.getText().toString())){
            Toast.makeText(getApplicationContext(), "추가되었습니다", Toast.LENGTH_SHORT).show();
            titletxt.setText("");
            urltxt.setText("");
        }else {
            Toast.makeText(getApplicationContext(), "제목이 같은 데이터가 있습니다. 삭제후 추가 또는 다른 제목을 작성하여 주세요.", Toast.LENGTH_SHORT).show();
        }

        titletxt.requestFocus();
    }

    public void close(View view){
        finish();
    }
}
