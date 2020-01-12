package com.example.facultyfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

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
    String UniversityName;
    DataSnapshot current;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Log.w("thala","vailmai");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        progressDialog =new ProgressDialog(this);
        Intent intent = getIntent();
        UniversityName = intent.getStringExtra("name");
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        if(UniversityName == NULL) {
            databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Profile");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    profileInfo = dataSnapshot.getValue(ProfileInfo.class);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            databaseReference = FirebaseDatabase.getInstance().getReference();
            Log.w("thala","kings");
            databaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.w("thala","ing");
                    for(DataSnapshot node: dataSnapshot.getChildren())
                    {
                        DataSnapshot profile = node.child("Profile");
                        Log.w("thala","g");
                        profileInfo = profile.getValue(ProfileInfo.class);
                        if(profileInfo.getOrganisationname().equals(UniversityName)) {
                            current = node.child("Faculty");
                            break;
                        }

                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // set code to show an error
                    Log.w("thala","ing");
                }
            });
        }
        setSupportActionBar(toolbar);
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
        editTextspecifytime.setVisibility(View.VISIBLE);
    }

    public void signout()
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(Query0Activity.this,UniversityList.class);
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
    public void tos()
    {
        Toast.makeText(this,"No such Faculty exist",Toast.LENGTH_SHORT).show();
    }

    public Integer totimee(String s)
    {
        Integer res,a,b;
        String ns="", nr = "";
        Integer f = 0;
        for(int i=0;i<s.length();i++) {
            if (s.charAt(i) == ':') {
                f = 1;
                continue;
            }
            if (f == 0)
                ns += s.charAt(i);
            else
                nr += s.charAt(i);
        }
        a = Integer.parseInt(ns);
        Log.w("Thala is ",Integer.toString(a));
        res = a * 60 + Integer.parseInt(nr);
        return res;

    }

    public void search(View view)
    {
        String queryfacultynam=editTextqueryfacultyname.getText().toString().trim();
        if(TextUtils.isEmpty(queryfacultynam))
        {
            Toast.makeText(this,"Please enter Faculty name",Toast.LENGTH_SHORT).show();
            return;
        }
        final String queryfacultyname = queryfacultynam.toLowerCase();
        if(click==-1)
        {
            Toast.makeText(this,"Please choose from Time options",Toast.LENGTH_SHORT).show();
            return;
        }
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar c =Calendar.getInstance();
        if(click==1)
        {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String time =format.format(c.getTime());
            dow =c.get(Calendar.DAY_OF_WEEK);
            querytime = totimee(time);
        }
        else
        {
            dow =c.get(Calendar.DAY_OF_WEEK);
            String time= editTextspecifytime.getText().toString();
            querytime = totimee(time);
        }

        Log.w("thala as Always",Integer.toString(querytime));
        Log.w("thala as Always",Integer.toString(dow));

        if(UniversityName == NULL) {
            databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot x : dataSnapshot.getChildren()) {
                        facultyInfo = x.getValue(FacultyInfo.class);
                        if (queryfacultyname.equals(facultyInfo.facultyname)) {
                            Log.w("thala", "wolf");
                            solve();
                            return;
                        }
                    }
                    tos();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {

            for (DataSnapshot x : current.getChildren()) {
                facultyInfo = x.getValue(FacultyInfo.class);
                if (queryfacultyname.equals(facultyInfo.facultyname)) {
                    Log.w("thala", "wolf");
                    solve();
                    return;
                }
            }
            tos();

        }

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
            Toast.makeText(this,"Working hour is over or not started yet",Toast.LENGTH_SHORT).show();
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
        boolean chk=facultyInfo.present;
        if(chk) {
            showMessage("Phone Number", facultyInfo.getFacultyphoneno());
            showMessage("Faculty is Expected to be in", ans);
        }
        else
        {
            showMessage("Phone Number", facultyInfo.getFacultyphoneno());
            showMessage("Faculty is ", "Absent");
        }
    }
    public  void showMessage(String title,String message)
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        navEmail =(TextView)findViewById(R.id.navEmail);
        if(user != null) {
            navEmail.setText(user.getEmail());
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                Intent ProfileIntent = new Intent(Query0Activity.this, ProfileActivity.class);
                ProfileIntent.putExtra("name", UniversityName);
                startActivity(ProfileIntent);
                finish();
            } else if (id == R.id.nav_home) {

            } else if (id == R.id.nav_facultylist) {
                Intent ProfileIntent = new Intent(Query0Activity.this, Faculty_list.class);
                ProfileIntent.putExtra("name", UniversityName);
                startActivity(ProfileIntent);
                finish();
            } else if (id == R.id.nav_signout) {
                signout();
            } else if (id == R.id.nav_markabsentees) {
                Intent markabsentIntent = new Intent(Query0Activity.this, mark_absentees.class);
                markabsentIntent.putExtra("name", UniversityName);
                startActivity(markabsentIntent);
                finish();

            } else if (id == R.id.nav_addfaculty) {
                Intent AddfacultyIntent = new Intent(Query0Activity.this, AddFacultyActivity.class);
                AddfacultyIntent.putExtra("name", UniversityName);
                startActivity(AddfacultyIntent);
                finish();
            } else if (id == R.id.nav_removefaculty) {
                Intent RemoveIntent = new Intent(Query0Activity.this, Removefaculty_Activity.class);
                RemoveIntent.putExtra("name", UniversityName);
                startActivity(RemoveIntent);
                finish();
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            Toast.makeText(this,"You are not Authorised.",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
