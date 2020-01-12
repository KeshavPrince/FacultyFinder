package com.example.facultyfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class Removefaculty_Activity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    List<FacultyInfo> faculty_list;
    EditText facultyname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removefaculty_);
        firebaseAuth = FirebaseAuth.getInstance();
        faculty_list=new ArrayList<>();
        user = firebaseAuth.getCurrentUser();
        facultyname=(EditText)findViewById(R.id.facultyname);
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty");
        Toolbar toolbar = findViewById(R.id.toolbarremovefaculty);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layoutremovefaculty);
        NavigationView navigationView = findViewById(R.id.nav_viewremovefaculty);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public boolean chkinternet()
    {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public void delfac()
    {
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        Boolean chk=false;
        String name=facultyname.getText().toString().trim();
        for(FacultyInfo f:faculty_list)
        {
            if(name.equals(f.getFacultyname()))
            {
                databaseReference =FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty").child(name);
                databaseReference.removeValue();
                chk=true;
                break;
            }
        }
        if(chk) {
            Toast.makeText(this,"Faculty Removed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"No such Faculty exits",Toast.LENGTH_SHORT).show();
        }
    }
    public  void deletefaculty(View view)
    {
        String fname=facultyname.getText().toString().trim();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to remove " + fname +" ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                delfac();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layoutremovefaculty);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void signout()
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(Removefaculty_Activity.this,UniversityList.class);
        startActivity(LoginIntent);
        finish();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent ProIntent= new Intent(Removefaculty_Activity.this,ProfileActivity.class);
            startActivity(ProIntent);
            finish();

        } else if (id == R.id.nav_facultylist) {
            Intent QueryIntent= new Intent(Removefaculty_Activity.this,Faculty_list.class);
            startActivity(QueryIntent);
            finish();
        }else if (id == R.id.nav_home) {
            Intent QueryIntent= new Intent(Removefaculty_Activity.this,Query0Activity.class);
            startActivity(QueryIntent);
            finish();
        }
        else if (id == R.id.nav_signout) {
            signout();
        } else if (id == R.id.nav_markabsentees) {
            Intent markabsentIntent= new Intent(Removefaculty_Activity.this,mark_absentees.class);
            startActivity(markabsentIntent);
            finish();
        } else if (id == R.id.nav_addfaculty) {
            Intent addfacIntent= new Intent(Removefaculty_Activity.this,AddFacultyActivity.class);
            startActivity(addfacIntent);
            finish();

        } else if (id == R.id.nav_removefaculty) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutremovefaculty);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
