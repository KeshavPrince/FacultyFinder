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
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
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
    EditText editTextfacultyname;
    EditText editTextfacultyphoneno;
    FacultyInfo facultyInfo;
    TextInputLayout textInputLayoutname;
    TextInputLayout textInputLayoutphoneno;
    ProfileInfo profileInfo;
    public String m_Text;
    ArrayList<String> cur=new ArrayList<String>();
    ArrayList<String> mon=new ArrayList<>();
    ArrayList<String> tues=new ArrayList<>();
    ArrayList<String> wed=new ArrayList<>();
    ArrayList<String> thur=new ArrayList<>();
    ArrayList<String> fri=new ArrayList<>();
    ArrayList<String> sat=new ArrayList<>();

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
        textInputLayoutname=(TextInputLayout)findViewById(R.id.Input_addname);
        textInputLayoutphoneno=(TextInputLayout)findViewById(R.id.Input_addphoneno);
        editTextfacultyname=(EditText)findViewById(R.id.addname);
        editTextfacultyphoneno=(EditText)findViewById(R.id.addphoneno);
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Profile");
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

    public void updateday(String day)
    {
        if(day.equals("Monday"))
        {
            for(String s:cur)
            {
                mon.add(s);
            }
        }
        else if(day.equals("Tuesday"))
        {
            for(String s:cur)
            {
                tues.add(s);
            }
        }
        else if(day.equals("Wednesday"))
        {
            for(String s:cur)
            {
                wed.add(s);
            }
        }
        else if(day.equals("Thursday"))
        {
            for(String s:cur)
            {
                thur.add(s);
            }
        }
        else if(day.equals("Friday"))
        {
            for(String s:cur)
            {
                fri.add(s);
            }
        }
        else if(day.equals("Saturday"))
        {
            for(String s:cur)
            {
                sat.add(s);
            }
        }
    }

    public void dialogbox(final String day, final Integer curlec) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final Integer h=curlec+1;
            if(h>=nooflec+1) {
                updateday(day);
                cur.clear();
                return;
            }
            builder.setTitle(day+ ": Lecture " + Integer.toString(h));
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
    public void toastfun(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void amonday(View view)
    {
        dialogbox("Monday",0);
    }
    public void atuesday(View view)
    {
        dialogbox("Tuesday",0);
    }
    public void awednesday(View view)
    {
        dialogbox("Wednesday",0);
    }
    public void athursday(View view)
    {
        dialogbox("Thursday",0);
    }
    public void afriday(View view)
    {
        dialogbox("Friday",0);
    }
    public void asaturday(View view)
    {
        dialogbox("Saturday",0);
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

    public void uploaddata(View view)
    {
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        String Facultyname=editTextfacultyname.getText().toString().trim();
        if(TextUtils.isEmpty(Facultyname))
        {
            textInputLayoutname.setError("Field can't be empty");
            return;
        }
        else
        {
            textInputLayoutname.setError(null);
        }
        Facultyname = Facultyname.toLowerCase();
        String FacultyPhoneno=editTextfacultyphoneno.getText().toString().trim();
        if(TextUtils.isEmpty(FacultyPhoneno))
        {
            textInputLayoutphoneno.setError("Field can't be empty");
            return;
        }
        else
        {
            textInputLayoutphoneno.setError(null);
        }
        if(FacultyPhoneno.length()!=10) {
            textInputLayoutphoneno.setError("Incorrect phone number");
            return;
        }
        else
        {
            textInputLayoutphoneno.setError(null);
        }
        if(mon.size()==0)
        {
            toastfun("Enter faculty's schedule for Monday");
            return;
        }
        if(tues.size()==0)
        {
            toastfun("Enter faculty's schedule for Tuesday");
            return;
        }
        if(wed.size()==0)
        {
            toastfun("Enter faculty's schedule for Wednesday");
            return;
        }
        if(thur.size()==0)
        {
            toastfun("Enter faculty's schedule for Thurday");
            return;
        }
        if(fri.size()==0)
        {
            toastfun("Enter faculty's schedule for Friday");
            return;
        }
        if(sat.size()==0)
        {
            toastfun("Enter faculty's schedule for Saturday");
            return;
        }
        facultyInfo=new FacultyInfo(Facultyname,FacultyPhoneno,true,mon,tues,wed,thur,fri,sat);
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Faculty").child(Facultyname);
        databaseReference.setValue(facultyInfo);
        toastfun("Faculty Created!");

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
            Intent profileIntent = new Intent(AddFacultyActivity.this, ProfileActivity.class);
            startActivity(profileIntent);
            finish();
        } else if (id == R.id.nav_facultylist) {
            Intent LoginIntent= new Intent(AddFacultyActivity.this,Faculty_list.class);
            startActivity(LoginIntent);
            finish();
        }else if (id == R.id.nav_home) {
            Intent QueryIntent= new Intent(AddFacultyActivity.this,Query0Activity.class);
            startActivity(QueryIntent);
            finish();
        }
        else if (id == R.id.nav_signout) {
            signout();
        } else if (id == R.id.nav_markabsentees) {
            Intent markabsentIntent= new Intent(AddFacultyActivity.this,mark_absentees.class);
            startActivity(markabsentIntent);
            finish();

        } else if (id == R.id.nav_addfaculty) {

        } else if (id == R.id.nav_removefaculty) {
            Intent RemoveIntent = new Intent(AddFacultyActivity.this, Removefaculty_Activity.class);
            startActivity(RemoveIntent);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutaddfaculty);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
