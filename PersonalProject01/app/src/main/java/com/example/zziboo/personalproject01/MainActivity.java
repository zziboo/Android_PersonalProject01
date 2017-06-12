package com.example.zziboo.personalproject01;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    WebView webView;
    EditText editText;
    static final int GET_STRING = 1;
    String urltxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editSearch);
        editText.setText("http://");
        editText.setSelection(editText.length());
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new OpenBrowser());
    }

    public void open(View view){
        urltxt = editText.getText().toString();
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(urltxt);
    }

    public class OpenBrowser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.viewMenu:
                intent = new Intent(MainActivity.this, SiteViewActivity.class);
                startActivityForResult(intent, GET_STRING);
                return true;
            case R.id.addMenu:
                intent = new Intent(getApplicationContext(), NewSiteActivity.class);
                startActivity(intent);
                return true;
            case R.id.delMenu:
                intent = new Intent(getApplicationContext(), DeleteSiteActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == GET_STRING){
            if(resultCode == RESULT_OK) {
                String key = data.getStringExtra("INPUT_TEXT");
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                urltxt = "http://" + key;
                webView.loadUrl(urltxt);
                editText.setText(urltxt);
            }
        }
    }
}
