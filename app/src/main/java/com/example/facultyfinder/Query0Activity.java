package com.example.facultyfinder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Query0Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView navEmail;
    FirebaseAuth firebaseAuth;
    EditText editTextqueryfacultyname;
    EditText editTextspecifytime;
    Integer click;
    RadioGroup radioGroup;
    RadioButton radioButtoncur,radioButtonspec;
    FacultyInfo facultyInfo;
    FirebaseUser user;
    ProfileInfo profileInfo;
    Integer dow ,querytime;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Profile");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileInfo=dataSnapshot.getValue(ProfileInfo.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setSupportActionBar(toolbar);
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty");
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
        click=-1;
        navigationView.setNavigationItemSelectedListener(this);
        editTextqueryfacultyname=(EditText)findViewById(R.id.queryfacultyname);
        editTextspecifytime=(EditText)findViewById(R.id.queryspecfictime);
        radioGroup=(RadioGroup)findViewById(R.id.radiotime);
        radioButtoncur=(RadioButton)findViewById((R.id.radioButton2));
        radioButtonspec=(RadioButton)findViewById((R.id.radioButton));
        editTextspecifytime.setVisibility(View.INVISIBLE);
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


    public void currenttime(View view)
    {
        int radioid=radioGroup.getCheckedRadioButtonId();
        radioButtoncur=findViewById(radioid);
        click=1;
        editTextspecifytime.setVisibility(View.INVISIBLE);
    }

    public void specifytime(View view)
    {
        int radioid=radioGroup.getCheckedRadioButtonId();
        radioButtonspec=findViewById(radioid);
        click=2;
        editTextspecifytime.setVisibility(View.VISIBLE);
    }

    public void search(View view)
    {
        final String queryfacultyname=editTextqueryfacultyname.getText().toString().trim();
        if(TextUtils.isEmpty(queryfacultyname))
        {
            Toast.makeText(this,"Please enter Faculty name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(click==-1)
        {
            Toast.makeText(this,"Please choose from Time options",Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar c =Calendar.getInstance();
        if(click==1)
        {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String time =format.format(c.getTime());
            dow =c.get(Calendar.DAY_OF_WEEK);
            String s1=time.substring(0,time.indexOf(":"));
            String s2=time.substring(time.indexOf(":")+1);
            String s3=s2.substring(0,time.indexOf(":"));
            Integer a=Integer.valueOf(s1);
            Integer b=Integer.valueOf(s3);
            querytime=(a*100)+b;}
        else
        {
            dow =c.get(Calendar.DAY_OF_WEEK);
            String time= editTextspecifytime.getText().toString();
            String s1=time.substring(0,time.indexOf(":"));
            String s2=time.substring(time.indexOf(":")+1);
            String s3=s2.substring(0,time.indexOf(":"));
            Integer a=Integer.valueOf(s1);
            Integer b=Integer.valueOf(s3);
            querytime=(a*100)+b;
        }
        Log.w("thala as Always",Integer.toString(querytime));
        Log.w("thala as Always",Integer.toString(dow));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot x:dataSnapshot.getChildren())
                {
                    facultyInfo=x.getValue(FacultyInfo.class);
                    if(queryfacultyname.equals(facultyInfo.facultyname))
                    {
                        Log.w("thala","wolf");
                        solve();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void solve()
    {
        Integer st=profileInfo.startingtime;
        Integer nol=profileInfo.nooflectures;
        Integer dur=profileInfo.duration;
        Integer cur=1;
        Integer a=st,b=st+dur;
        while(cur<=nol)
        {
            if(querytime>=a && querytime<b)
            {
                break;
            }
            cur++;
            a+=dur;
            b+=dur;
        }
        if(cur>nol)
        {
            Toast.makeText(this,"Working work is over or not started yet",Toast.LENGTH_SHORT).show();
            return;
        }
        String ans="Hoilday";
        cur--;
        if(dow==2)
        {
            ans=facultyInfo.monday.get(cur);
        }
        if(dow==3)
        {
            ans=facultyInfo.tuesday.get(cur);
        }
        if(dow==4)
        {
            ans=facultyInfo.wednesday.get(cur);
        }
        if(dow==5)
        {
            ans=facultyInfo.thursday.get(cur);
        }
        if(dow==6)
        {
            ans=facultyInfo.friday.get(cur);
        }
        if(dow==7)
        {
            ans=facultyInfo.saturday.get(cur);
        }
        Log.w("wolfthala",ans);
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
