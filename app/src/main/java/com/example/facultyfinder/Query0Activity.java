package com.example.facultyfinder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class Query0Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView navEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null) {
            Intent LoginIntent = new Intent(Query0Activity.this, LoginActivity.class);
            startActivity(LoginIntent);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    public void signout()
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(Query0Activity.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        navEmail =(TextView)findViewById(R.id.navEmail);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        navEmail.setText(user.getEmail());
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent ProfileIntent = new Intent(Query0Activity.this, ProfileActivity.class);
            startActivity(ProfileIntent);
            finish();
            // Handle the camera action
        }else if (id == R.id.nav_home) {

        }
        else if (id == R.id.nav_facultylist) {

        } else if (id == R.id.nav_signout) {
            signout();
        } else if (id == R.id.nav_markabsentees) {


        } else if (id == R.id.nav_addfaculty) {
            Intent AddfacultyIntent = new Intent(Query0Activity.this, AddFacultyActivity.class);
            startActivity(AddfacultyIntent);
            finish();
        } else if (id == R.id.nav_removefaculty) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
