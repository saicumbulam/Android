package com.example.codeledge_v2.ui.ProgramDisplay;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.codeledge_v2.DatabaseOps.DbHelper;
import com.example.codeledge_v2.R;

import java.io.Console;

public class ProgramDisplayFragment extends Fragment {
    private View root;
    private DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbHelper = DbHelper.getInstance();
        root = inflater.inflate(R.layout.fragment_program_display, container, false);
        loadWebViewer();
        return root;
    }


    private void loadWebViewer() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html> <html> <head> </head> <body>");
        String program_name = getArguments().getString("PROGRAM_NAME");
        String tableType = getArguments().getString("TABLETYPE");
        String HtmlString =  (dbHelper.getProgramDescr(program_name, tableType)).replace("\n", "<br/>");
        sb.append(HtmlString);
        sb.append("</body></html>");
        WebView webView = root.findViewById(R.id.ProgramDisplayFragmentWebview);
        String encodedHtml  = Base64.encodeToString(sb.toString().getBytes(), Base64.NO_PADDING);
        webView.loadData(encodedHtml, "text/html", "base64");

    }

}
