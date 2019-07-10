package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText emailAddress;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.editTextUsename);
        emailAddress = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

    }

    public void taketoLoginActivity(View view) {
        Intent l = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(l);
    }

    public void SaveUserDetails(View view) {
        Log.i("debug",username.getText().toString() + "," + emailAddress.getText().toString());
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.healthapp",
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username.getText().toString()).apply();
        sharedPreferences.edit().putString("email", emailAddress.getText().toString()).apply();

        TaketoMainActivity();
    }

    private void TaketoMainActivity() {
        Intent l = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(l);
    }
}
