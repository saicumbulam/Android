package com.example.healthapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView email;
    private TextView username;
    NavigationView navigationView;
    private String usernameProvided;
    private String emailProvided;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigation();
        setCustomizeListView();
        NavBarModify();
    }

    private void NavBarModify() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.healthapp",
                Context.MODE_PRIVATE);

        usernameProvided = sharedPreferences.getString("username","");
        emailProvided = sharedPreferences.getString("email","");

        if (!emailProvided.equals("") && !usernameProvided.equals(""))  {
            View headerView =  navigationView.getHeaderView(0);
            this.username = headerView.findViewById(R.id.nav_header_title);
            this.email = headerView.findViewById(R.id.nav_header_subtitle);

            this.username.setText(usernameProvided);
            this.email.setText(emailProvided);
        }

    }

    private void setCustomizeListView() {
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        Log.d(LOG_TAG, message);

        ListView recentListview = findViewById(R.id.recentlistview);
        final ArrayList<String> myFamily = new ArrayList<String>(){{add("Rob");
            add("Rob1");add("Rob2");}};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myFamily);
        recentListview.setAdapter(arrayAdapter);

        recentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Hello " + myFamily.get(position), Toast.LENGTH_LONG).show();
            }
        });

        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        final ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(Integer.toString(i));
        }

        ListView specialListView = findViewById(R.id.listview1);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayList);
        specialListView.setAdapter(arrayAdapter1);

        specialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Hello " + arrayList.get(position), Toast.LENGTH_LONG).show();
            }
        });

        TextView nearYou = findViewById(R.id.nearYou);
        TextView specialities = findViewById(R.id.specialities);
        if (message.equals("Pharmacy")) {
            nearYou.setText("Pharmacies Near You");
            specialities.setText("Types of Pharmacies");
        } else if(message.equals("Doctor"))
        {
            nearYou.setText("Doctors Near You");
            specialities.setText("All Specialities");
        }

    }

    private void setNavigation() {
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                Intent h = new Intent(SearchResultsActivity.this, MainActivity.class);
                startActivity(h);
                break;


            case R.id.nav_doctor:
                Intent d = new Intent(SearchResultsActivity.this, SearchResultsActivity.class);
                d.putExtra("message", "Doctor");
                startActivity(d);
                break;

            case R.id.nav_appointment:
                Intent a = new Intent(SearchResultsActivity.this, AppointmentActivity.class);
                startActivity(a);
                break;


            case R.id.nav_pharmacy:
                Intent p = new Intent(SearchResultsActivity.this, SearchResultsActivity.class);
                p.putExtra("message", "Pharmacy");
                startActivity(p);
                break;

            case R.id.nav_deals:
                Intent m = new Intent(SearchResultsActivity.this, DealActivity.class);
                startActivity(m);
                break;

            case R.id.nav_login:
                if (!usernameProvided.equals("") && !emailProvided.equals("")) {
                    Intent l = new Intent(SearchResultsActivity.this, AccountActivity.class);
                    startActivity(l);

                } else {
                    Intent l = new Intent(SearchResultsActivity.this, RegisterActivity.class);
                    startActivity(l);
                }
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void taketoNearMeActivity(View view) {
        Log.d(LOG_TAG, "Near me clicked!");
        Intent n = new Intent(SearchResultsActivity.this, NearMeActivity.class);
        n.putExtra("message", "Doctor");
        startActivity(n);
    }
}
