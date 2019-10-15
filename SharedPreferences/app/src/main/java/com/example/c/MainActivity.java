package com.example.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.c",
                Context.MODE_PRIVATE);

        ArrayList<String> friends = new ArrayList<>();
        friends.add("monica");
        friends.add("chandler");
        try {
            sharedPreferences.edit().putString("friends", ObjectSerializer.serialize(friends)).apply();
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<String> newFriends = new ArrayList<>();
        try{
        newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e){
            e.printStackTrace();
        }

        Log.i("newfriends", newFriends.toString());
        // sharedPreferences.edit().putString("username", "rob").apply();

        // String username = sharedPreferences.getString("username","");

        // Log.i("username", username);
    }
}
