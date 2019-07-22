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
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class mark_absentees extends AppCompatActivity
                    implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    List<FacultyInfo> faculty_list;
    EditText facultyname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_absentees);
        firebaseAuth = FirebaseAuth.getInstance();
        faculty_list=new ArrayList<>();
        user = firebaseAuth.getCurrentUser();
        facultyname=(EditText)findViewById(R.id.facultyname);
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty");
        Toolbar toolbar = findViewById(R.id.toolbarmark_absentees);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layoutmark_absentees);
        NavigationView navigationView = findViewById(R.id.nav_viewmark_absentees);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Log.w("thala","viswasam");
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layoutmark_absentees);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void signout()
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(mark_absentees.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent ProIntent= new Intent(mark_absentees.this,ProfileActivity.class);
            startActivity(ProIntent);
            finish();

        } else if (id == R.id.nav_facultylist) {
            Intent QueryIntent= new Intent(mark_absentees.this,Faculty_list.class);
            startActivity(QueryIntent);
            finish();
        }else if (id == R.id.nav_home) {
            Intent QueryIntent= new Intent(mark_absentees.this,Query0Activity.class);
            startActivity(QueryIntent);
            finish();
        }
        else if (id == R.id.nav_signout) {
            signout();
        } else if (id == R.id.nav_markabsentees) {

        } else if (id == R.id.nav_addfaculty) {
            Intent addfacIntent= new Intent(mark_absentees.this,AddFacultyActivity.class);
            startActivity(addfacIntent);
            finish();

        } else if (id == R.id.nav_removefaculty) {
            Intent removeIntent= new Intent(mark_absentees.this,Removefaculty_Activity.class);
            startActivity(removeIntent);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutmark_absentees);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
