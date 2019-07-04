package com.example.facultyfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Faculty_list extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    ListView flistView;
    List<FacultyInfo> faculty_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);
        flistView=(ListView)findViewById(R.id.facultylistview);
        faculty_list=new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty");
        Toolbar toolbar = findViewById(R.id.toolbarfacultylist);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layoutfacultylist);
        NavigationView navigationView = findViewById(R.id.nav_viewfacultylist);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Log.w("thala","viswasam");
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                faculty_list.clear();
                for(DataSnapshot faculty:dataSnapshot.getChildren())
                {
                    FacultyInfo f=faculty.getValue(FacultyInfo.class);
                    faculty_list.add(f);
                }
                facultylistview adapter= new facultylistview(Faculty_list.this,faculty_list);
                flistView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layoutfacultylist);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void signout()
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(Faculty_list.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent QueryIntent= new Intent(Faculty_list.this,ProfileActivity.class);
            startActivity(QueryIntent);
            finish();

        } else if (id == R.id.nav_facultylist) {

        }else if (id == R.id.nav_home) {
            Intent QueryIntent= new Intent(Faculty_list.this,Query0Activity.class);
            startActivity(QueryIntent);
            finish();
        }
        else if (id == R.id.nav_signout) {
            signout();
        } else if (id == R.id.nav_markabsentees) {

        } else if (id == R.id.nav_addfaculty) {
            Intent QueryIntent= new Intent(Faculty_list.this,AddFacultyActivity.class);
            startActivity(QueryIntent);
            finish();

        } else if (id == R.id.nav_removefaculty) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutprofile);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}