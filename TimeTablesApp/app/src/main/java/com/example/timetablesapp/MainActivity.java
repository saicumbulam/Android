package com.example.timetablesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar timeTableseekbar = findViewById(R.id.seekBar);
        ListView listView = findViewById(R.id.listView);

        timeTableseekbar.setMax(20);
        timeTableseekbar.setProgress(10);

        timeTableseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 1;
                int timesTable;

                if (progress < 1) {
                   timesTable = min;
                } else {
                    timesTable = progress;
                }
                Log.i("Seekbar value", Integer.toString(timesTable));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int timesTable = 10;

        ArrayList<String> timesTableContent = new ArrayList<String>();
        for (int i = 1; i <= 10; i++) {
            timesTableContent.add(Integer.toString(i * timesTable));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, timesTableContent);

        listView.setAdapter(arrayAdapter);
    }
}
