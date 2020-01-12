package com.example.facultyfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class mark_absentees extends AppCompatActivity
                    implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    List<FacultyInfo> faculty_listt;
    EditText facultyname;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_absentees);
        firebaseAuth = FirebaseAuth.getInstance();
        faculty_listt=new ArrayList<>();
        progressDialog =new ProgressDialog(this);
        user = firebaseAuth.getCurrentUser();
        facultyname=(EditText)findViewById(R.id.facultynameabsent);
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
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                faculty_listt.clear();
                for(DataSnapshot faculty:dataSnapshot.getChildren())
                {
                    FacultyInfo f=faculty.getValue(FacultyInfo.class);
                    faculty_listt.add(f);
                }
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        Intent LoginIntent= new Intent(mark_absentees.this,UniversityList.class);
        startActivity(LoginIntent);
        finish();
    }
    public void markabsent()
    {
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        String fname=facultyname.getText().toString().trim();
        for(int i=0;i<faculty_listt.size();i++)
        {
            String ss=faculty_listt.get(i).facultyname;
            if(ss.equals(fname))
            {
                faculty_listt.get(i).present=false;
                databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty").child(fname);
                databaseReference.setValue(faculty_listt.get(i));
                Toast.makeText(this,"Faculty marked Absent",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
    public void markabsentees(View view)
    {
        String fname=facultyname.getText().toString().trim();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to mark " + fname +" Absent?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                markabsent();
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

    public void restatt()
    {
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        String fname;
        for(int i=0;i<faculty_listt.size();i++)
        {
            fname=faculty_listt.get(i).facultyname;
            faculty_listt.get(i).present=true;
            databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty").child(fname);
            databaseReference.setValue(faculty_listt.get(i));
        }
        Toast.makeText(this,"All faculty are present now",Toast.LENGTH_SHORT).show();
    }

    public void resetattendence(View view)
    {
        String fname=facultyname.getText().toString().trim();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to reset " + "?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                restatt();
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
    public boolean chkinternet()
    {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
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
