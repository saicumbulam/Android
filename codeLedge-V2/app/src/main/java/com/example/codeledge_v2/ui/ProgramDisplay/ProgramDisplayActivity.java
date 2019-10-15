package com.example.codeledge_v2.ui.ProgramDisplay;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.codeledge_v2.DatabaseOps.DbHelper;
import com.example.codeledge_v2.R;

import static android.text.Html.fromHtml;

public class ProgramDisplayActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_display);

        dbHelper = DbHelper.getInstance();


        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html> <html> <head> </head> <body>");
        String program_name = getIntent().getStringExtra("PROGRAM_NAME");
        String tableType = getIntent().getStringExtra("TABLETYPE");
        String HtmlString =  (dbHelper.getProgramDescr(program_name, tableType)).replace("\n", "<br/>");
        sb.append(HtmlString);
        sb.append("</body></html>");
        WebView webView = findViewById(R.id.ProgramDisplayActivityWebview);
        String encodedHtml  = Base64.encodeToString(sb.toString().getBytes(), Base64.NO_PADDING);
        webView.loadData(encodedHtml, "text/html", "base64");
        //textView.setText(HtmlCompat.fromHtml(HtmlString,HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}

