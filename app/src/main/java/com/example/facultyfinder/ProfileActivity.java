package com.example.facultyfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    TextView textViewusername;
    TextView textVieworganisation;
    TextView textViewemail;
    TextView textViewphonenumber;
    TextView textViewnooffaculty;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.w("Thala","kp");
        progressDialog =new ProgressDialog(this);
        setfront();
        Toolbar toolbar = findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layoutprofile);
        NavigationView navigationView = findViewById(R.id.nav_viewprofile);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
    public void setfront()
    {
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Fetching User Data..");
        progressDialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Profile");
        textViewusername = (TextView)findViewById(R.id.username);
        textVieworganisation=(TextView)findViewById(R.id.organisation);
        textViewemail=(TextView)findViewById(R.id.email);
        textViewphonenumber=(TextView)findViewById(R.id.faculty_phoneno);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileInfo profileInfo=dataSnapshot.getValue(ProfileInfo.class);
                dochange(profileInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void dochange(ProfileInfo profileInfo)
    {
       Log.w("thala1","cool");
        textViewemail.setText(user.getEmail());
        Log.w("thala2",profileInfo.getPhonenumber());
        textViewusername.setText(profileInfo.getUsername());
        textVieworganisation.setText(profileInfo.getOrganisationname());
        textViewphonenumber.setText(profileInfo.getPhonenumber());
        progressDialog.dismiss();
    }
    public void signout()
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(ProfileActivity.this,UniversityList.class);
        startActivity(LoginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layoutprofile);
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

            // Handle the camera action
        } else if (id == R.id.nav_facultylist) {
            Intent LoginIntent= new Intent(ProfileActivity.this,Faculty_list.class);
            startActivity(LoginIntent);
            finish();

        }else if (id == R.id.nav_home) {
            Intent QueryIntent= new Intent(ProfileActivity.this,Query0Activity.class);
            startActivity(QueryIntent);
            finish();
        }
        else if (id == R.id.nav_signout) {
            signout();
        } else if (id == R.id.nav_markabsentees) {
            Intent markabsentIntent= new Intent(ProfileActivity.this,mark_absentees.class);
            startActivity(markabsentIntent);
            finish();
        } else if (id == R.id.nav_addfaculty) {
            Intent QueryIntent= new Intent(ProfileActivity.this,AddFacultyActivity.class);
            startActivity(QueryIntent);
            finish();

        } else if (id == R.id.nav_removefaculty) {
            Intent RemoveIntent = new Intent(ProfileActivity.this, Removefaculty_Activity.class);
            startActivity(RemoveIntent);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutprofile);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
