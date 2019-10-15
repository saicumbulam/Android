package com.example.codeledge;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.example.codeledge.DatabaseOps.DbHelper;

public class ReaderActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitializeClasses();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        String program_name = getIntent().getStringExtra("PROGRAM_NAME");
        String HtmlString = requestSqliteData(program_name);

        mWebView.loadData(HtmlString.replace("\n", "<br/>"), "text/html", "UTF-8");

    }

    private void InitializeClasses() {
        dbHelper = DbHelper.getInstance();
    }

    private String requestSqliteData(String programName) {
        return  dbHelper.getProgramDescr(programName);
    }

}
