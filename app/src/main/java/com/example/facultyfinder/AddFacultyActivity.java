package com.example.facultyfinder;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddFacultyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbaraddfaculty);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layoutaddfaculty);
        NavigationView navigationView = findViewById(R.id.nav_viewaddfaculty);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void signout()
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(AddFacultyActivity.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layoutaddfaculty);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent ProfileIntent = new Intent(AddFacultyActivity.this, ProfileActivity.class);
            startActivity(ProfileIntent);
            finish();
            // Handle the camera action
        } else if (id == R.id.nav_facultylist) {

        }else if (id == R.id.nav_home) {
            Intent QueryIntent= new Intent(AddFacultyActivity.this,Query0Activity.class);
            startActivity(QueryIntent);
            finish();
        }
        else if (id == R.id.nav_signout) {
            signout();
        } else if (id == R.id.nav_markabsentees) {

        } else if (id == R.id.nav_addfaculty) {

        } else if (id == R.id.nav_removefaculty) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutaddfaculty);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
