package com.example.listcradview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageView mcardImage;
    private TextView mcardTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcardImage = (ImageView) findViewById(R.id.image);
        mcardTitle = (TextView) findViewById(R.id.cardTitle);

        mcardImage.setImageResource(R.drawable.wallpaper);

        mcardTitle.setText("Mountain pic");

    }
}
