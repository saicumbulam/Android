package com.example.healthapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView searchView;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;

    private TextView email;
    private TextView username;

    private String usernameProvided;
    private String emailProvided;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setElevation(0);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        searchView = findViewById(R.id.doctorSearch);
        navigationView.setNavigationItemSelectedListener(this);
        NavBarModify();
    }

    private void NavBarModify() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.healthapp",
                Context.MODE_PRIVATE);

        this.usernameProvided = sharedPreferences.getString("username","");
        this.emailProvided = sharedPreferences.getString("email","");

        if (emailProvided.equals("") && usernameProvided.equals(""))  {
            CreateAlert();
        } else {
            View headerView =  navigationView.getHeaderView(0);
            this.username = headerView.findViewById(R.id.nav_header_title);
            this.email = headerView.findViewById(R.id.nav_header_subtitle);

            this.username.setText(usernameProvided);
            this.email.setText(emailProvided);
        }

    }

    private void CreateAlert() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account is not created");
        builder.setMessage("Would you like to create your account now ?");
        // add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent l = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(l);
            }
        });
        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent s = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(s);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                Intent h = new Intent(MainActivity.this, MainActivity.class);
                startActivity(h);
                break;


            case R.id.nav_doctor:
                Intent d = new Intent(MainActivity.this, SearchResultsActivity.class);
                d.putExtra("message", "Doctor");
                startActivity(d);
                break;

            case R.id.nav_appointment:
                Intent a = new Intent(MainActivity.this, AppointmentActivity.class);
                startActivity(a);
                break;


            case R.id.nav_pharmacy:
                Intent p = new Intent(MainActivity.this, SearchResultsActivity.class);
                p.putExtra("message", "Pharmacy");
                startActivity(p);
                break;

            case R.id.nav_deals:
                Intent m = new Intent(MainActivity.this, DealActivity.class);
                startActivity(m);
                break;

            case R.id.nav_login:
                if (!usernameProvided.equals("") && !emailProvided.equals("")) {
                    Intent l = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(l);

                } else {
                    Intent l = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(l);
                }
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void launchLoginActivity(MenuItem item) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void takeToDoctorSearchResults(View view) {

        Log.d(LOG_TAG, "Button clicked!");
        Intent s = new Intent(MainActivity.this, SearchResultsActivity.class);
        s.putExtra("message", "Doctor");
        startActivity(s);
    }

    public void takeToPharmacySearchResults(View view) {

        Log.d(LOG_TAG, "Button clicked!");
        Intent s = new Intent(MainActivity.this, SearchResultsActivity.class);
        s.putExtra("message", "Pharmacy");
        startActivity(s);
    }

    public void takeToFindResultsActivity(View view) {
        Log.d(LOG_TAG, "Find button clicked!");
        Intent s = new Intent(MainActivity.this, FindResultsActivity.class);
        startActivity(s);
    }
}
