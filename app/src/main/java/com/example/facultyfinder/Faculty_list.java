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
                for(DataSnapshot node:dataSnapshot.getChildren())
                {
                        FacultyInfo f = node.getValue(FacultyInfo.class);
                        String s = f.getFacultyname().toLowerCase();
                        String res = Camelcaseit(s);
                        f.setFacultyname(res);
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
        Intent LoginIntent= new Intent(Faculty_list.this,UniversityList.class);
        startActivity(LoginIntent);
        finish();
    }
    public String Camelcaseit(String s)
    {
        char ch[] = new char[s.length()];
        ch[0] = (char)(s.charAt(0) - 32);
        for(int i = 1; i < s.length(); ++i)
        {
            if(s.charAt(i) != ' ' && s.charAt(i - 1) == ' ')
            {
                ch[i] = (char)(s.charAt(i) - 32);
            }
            else {
                ch[i] = s.charAt(i);
            }
        }
        String res = new String(ch);
        return res;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent ProIntent= new Intent(Faculty_list.this,ProfileActivity.class);
            startActivity(ProIntent);
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
            Intent markabsentIntent= new Intent(Faculty_list.this,mark_absentees.class);
            startActivity(markabsentIntent);
            finish();

        } else if (id == R.id.nav_addfaculty) {
            Intent addfacIntent= new Intent(Faculty_list.this,AddFacultyActivity.class);
            startActivity(addfacIntent);
            finish();

        } else if (id == R.id.nav_removefaculty) {
            Intent RemoveIntent = new Intent(Faculty_list.this, Removefaculty_Activity.class);
            startActivity(RemoveIntent);
            finish();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutfacultylist);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}