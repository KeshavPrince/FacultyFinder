package com.example.facultyfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class AddFacultyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    ProfileInfo profileInfo;
    public String m_Text;
    TextView textView;
    Integer nooflec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        textView=(TextView)findViewById(R.id.textView8);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileInfo=dataSnapshot.getValue(ProfileInfo.class);
                Log.w("thala","dude");
                nooflec = profileInfo.nooflectures;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
    ArrayList<String> cur=new ArrayList<String>();
    public void dialogbox(final String day, final Integer curlec) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final Integer h=curlec+1;
            if(h>=nooflec+1)
                return;
            builder.setTitle(day+ ": Period " + Integer.toString(h));
            View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog, (ViewGroup) findViewById(android.R.id.content), false);
            final EditText input = (EditText) viewInflated.findViewById(R.id.editTexti);
            builder.setView(viewInflated);
            builder.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    m_Text = input.getText().toString();
                    cur.add(m_Text);
                    dialogbox(day,h);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
    }

    //days

    public void amonday(View view)
    {
        if(cur.size()>0)cur.clear();
        dialogbox("Monday", 0);
    }
    public void atuesday(View view)
    {

        if(cur.size()>0) cur.clear();
        dialogbox("Tuesday",0);
    }
    public void awednesday(View view)
    {

        if(cur.size()>0)cur.clear();
        dialogbox("Wednesday",0);
    }
    public void athursday(View view)
    {

        if(cur.size()>0)cur.clear();
        dialogbox("Thursday",0);
    }
    public void afriday(View view)
    {

        if(cur.size()>0)cur.clear();
        dialogbox("Friday",0);
    }
    public void asaturday(View view)
    {

        if(cur.size()>0) cur.clear();
        dialogbox("Saturday",0);
    }

    public void uploaddata(View view)
    {
        Log.w("thala",Integer.toString(cur.size()));
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
