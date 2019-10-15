package com.example.healthapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.healthapp.NearMeActivityContents.NearMeListAdapter;
import com.example.healthapp.NearMeActivityContents.NearMeListData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FindResultsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    public static final String[]titles =new String[]{"Doc1","Doc2","Doc2"};
    public static final String[]subtitle=new String[]{"General Physician",
            "General Physician","Psychiatrist"};
    public static final Integer[] images = { R.drawable.one,
            R.drawable.one, R.drawable.one };

    private String usernameProvided;
    private String emailProvided;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_results);

        toolbar = findViewById(R.id.toolbar_findResults);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setElevation(0);
        }

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        ListView recentListview = findViewById(R.id.listview);
        List<NearMeListData> rowData =new ArrayList<NearMeListData>();
        for(int i=0;i<titles.length;i++){
            NearMeListData data =new NearMeListData();
            data.setImageId(images[i]);
            data.setSubtitle(subtitle[i]);
            data.setTitle(titles[i]);
            rowData.add(data);
        }
        NearMeListAdapter adapter = new NearMeListAdapter(this, rowData);
        recentListview.setAdapter(adapter);
        recentListview.setClickable(true);
        recentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Clicked List Item", Toast.LENGTH_LONG).show();
            }
        });

        NavBarModify();
    }

    private void NavBarModify() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.healthapp",
                Context.MODE_PRIVATE);

        this.usernameProvided = sharedPreferences.getString("username","");
        this.emailProvided = sharedPreferences.getString("email","");

        if (emailProvided.equals("") && usernameProvided.equals(""))  {
        } else {
            View headerView =  navigationView.getHeaderView(0);
            TextView username = headerView.findViewById(R.id.nav_header_title);
            TextView email = headerView.findViewById(R.id.nav_header_subtitle);

            username.setText(usernameProvided);
            email.setText(emailProvided);
        }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                Intent h = new Intent(FindResultsActivity.this, MainActivity.class);
                startActivity(h);
                break;


            case R.id.nav_doctor:
                Intent d = new Intent(FindResultsActivity.this, SearchResultsActivity.class);
                d.putExtra("message", "Doctor");
                startActivity(d);
                break;

            case R.id.nav_appointment:
                Intent a = new Intent(FindResultsActivity.this, AppointmentActivity.class);
                startActivity(a);
                break;


            case R.id.nav_pharmacy:
                Intent p = new Intent(FindResultsActivity.this, SearchResultsActivity.class);
                p.putExtra("message", "Pharmacy");
                startActivity(p);
                break;

            case R.id.nav_deals:
                Intent m = new Intent(FindResultsActivity.this, DealActivity.class);
                startActivity(m);
                break;

            case R.id.nav_login:
                if (!usernameProvided.equals("") && !emailProvided.equals("")) {
                    Intent l = new Intent(FindResultsActivity.this, AccountActivity.class);
                    startActivity(l);

                } else {
                    Intent l = new Intent(FindResultsActivity.this, RegisterActivity.class);
                    startActivity(l);
                }
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
