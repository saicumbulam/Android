package com.example.healthapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.healthapp.DealsActivityContents.DealsListAdapter;
import com.example.healthapp.DealsActivityContents.DealsListData;
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DealActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String usernameProvided;
    private String emailProvided;
    private NavigationView navigationView;

    private String[]titles =new String[]{"Crocin","Ibu-prufin","Vicks"};
    private String[]subtitle=new String[]{"General Pharmacy",
            "General Pharmacy","General Pharmacy"};
    private Integer[] images = { R.drawable.one,
            R.drawable.one, R.drawable.one };
    private double[] prices =new double[] {50.00,100.00,120.00};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setElevation(0);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        NavBarModify();

        ListView recentListview = findViewById(R.id.listview);
        List<DealsListData> rowData =new ArrayList<DealsListData>();
        for(int i=0;i<titles.length;i++){
            DealsListData data =new DealsListData();
            data.setImageId(images[i]);
            data.setSubtitle(subtitle[i]);
            data.setTitle(titles[i]);
            data.setPrice(prices[i]);
            rowData.add(data);
        }
        DealsListAdapter adapter = new DealsListAdapter(this, rowData);
        recentListview.setAdapter(adapter);
    }

    private void NavBarModify() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.healthapp",
                Context.MODE_PRIVATE);

        usernameProvided = sharedPreferences.getString("username","");
        emailProvided = sharedPreferences.getString("email","");

        if (!emailProvided.equals("") && !usernameProvided.equals(""))  {
            View headerView =  navigationView.getHeaderView(0);
            TextView NavBarUsername = headerView.findViewById(R.id.nav_header_title);
            TextView NavBarEmail = headerView.findViewById(R.id.nav_header_subtitle);

            NavBarUsername.setText(usernameProvided);
            NavBarEmail.setText(emailProvided);
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
                Intent h = new Intent(DealActivity.this, MainActivity.class);
                startActivity(h);
                break;


            case R.id.nav_doctor:
                Intent d = new Intent(DealActivity.this, SearchResultsActivity.class);
                d.putExtra("message", "Doctor");
                startActivity(d);
                break;

            case R.id.nav_appointment:
                Intent a = new Intent(DealActivity.this, AppointmentActivity.class);
                startActivity(a);
                break;


            case R.id.nav_pharmacy:
                Intent p = new Intent(DealActivity.this, SearchResultsActivity.class);
                p.putExtra("message", "Pharmacy");
                startActivity(p);
                break;

            case R.id.nav_deals:
                Intent m = new Intent(DealActivity.this, DealActivity.class);
                startActivity(m);
                break;

            case R.id.nav_login:
                if (!usernameProvided.equals("") && !emailProvided.equals("")) {
                    Intent l = new Intent(DealActivity.this, AccountActivity.class);
                    startActivity(l);

                } else {
                    Intent l = new Intent(DealActivity.this, RegisterActivity.class);
                    startActivity(l);
                }
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
