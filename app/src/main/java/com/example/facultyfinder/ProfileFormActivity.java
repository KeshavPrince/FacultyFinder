package com.example.facultyfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.internal.measurement.zzm;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileFormActivity extends AppCompatActivity {

    EditText editTextusername;
    EditText editTextnameoforganisation;
    EditText editTextphonenumber;
    EditText editTextduration;
    EditText editTextstartingtime;
    EditText editTextnooflectures;
    TextInputLayout textInputLayoutusername;
    TextInputLayout textInputLayoutnameoforginsation;
    TextInputLayout textInputLayoutphhonenumber;
    TextInputLayout textInputLayoutduration;
    TextInputLayout textInputLayoutnooflectures;
    TextInputLayout textInputLayoutstartingtime;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);
        editTextusername = (EditText)findViewById(R.id.username);
        editTextnameoforganisation = (EditText)findViewById(R.id.organisationname);
        editTextphonenumber= (EditText)findViewById(R.id.phonenumber);
        editTextduration = (EditText)findViewById(R.id.duration);
        editTextstartingtime = (EditText)findViewById(R.id.startingtime);
        editTextnooflectures = (EditText)findViewById(R.id.nooflectures);
        textInputLayoutduration=(TextInputLayout)findViewById(R.id.Input_duration);
        textInputLayoutnameoforginsation=(TextInputLayout)findViewById(R.id.Input_orginationname);
        textInputLayoutnooflectures=(TextInputLayout)findViewById(R.id.Input_nooflectures);
        textInputLayoutphhonenumber=(TextInputLayout)findViewById(R.id.Input_phonenumber);
        textInputLayoutstartingtime=(TextInputLayout)findViewById(R.id.Input_startingtime);
        textInputLayoutusername=(TextInputLayout)findViewById(R.id.Input_username);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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

    public void toastfun(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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
        res = a * 60 + Integer.parseInt(nr);
        return res;
    }
    public void done(View view)
    {
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        String username = editTextusername.getText().toString().trim();
        if(TextUtils.isEmpty(username))
        {
            textInputLayoutusername.setError("Field can't be empty");
            return;
        }
        else
        {
            textInputLayoutusername.setError(null);
        }
        String organisationname = editTextnameoforganisation.getText().toString().trim();
        if(TextUtils.isEmpty(organisationname))
        {
            textInputLayoutnameoforginsation.setError("Field can't be empty");
            return;
        }
        else
        {
            textInputLayoutnameoforginsation.setError(null);
        }
        organisationname = organisationname.toLowerCase();
        String phonenostr = editTextphonenumber.getText().toString().trim();
        if(TextUtils.isEmpty(phonenostr))
        {
            textInputLayoutphhonenumber.setError("Field can't be empty");
            return;
        }
        else
        {
            textInputLayoutphhonenumber.setError(null);
        }
        if(phonenostr.length()!=10) {
            textInputLayoutphhonenumber.setError("Incorrect phone number");
            return;
        }
        else
        {
            textInputLayoutphhonenumber.setError(null);
        }
        String nooflecturesstr = editTextnooflectures.getText().toString().trim();
        if(TextUtils.isEmpty(nooflecturesstr))
        {
            textInputLayoutnooflectures.setError("Field can't be empty");
            return;
        }
        else
        {
            textInputLayoutnooflectures.setError(null);
        }
        if(nooflecturesstr.equals("0") || nooflecturesstr.length()>=3)
        {
            textInputLayoutnooflectures.setError("Invalid input");
            return;
        }
        else
        {
            textInputLayoutnooflectures.setError(null);
        }
        Integer nooflectures=Integer.parseInt(nooflecturesstr);	;
        String durationstr = editTextduration.getText().toString().trim();
        if(durationstr.length()!=5)
        {
            textInputLayoutduration.setError("Invaild input");
            return;
        }
        else
        {
            textInputLayoutduration.setError(null);
        }
        Integer duration = totimee(durationstr);
        String startingtimestr=editTextstartingtime.getText().toString().trim();
        if(startingtimestr.length()!=5)
        {
            textInputLayoutstartingtime.setError("Invaild input");
            return;
        }
        else
        {
            textInputLayoutduration.setError(null);
        }
        Log.w("thala","here we go");
        Integer startingtime = totimee(startingtimestr);
        ProfileInfo profileInfo = new ProfileInfo(username,phonenostr,organisationname,nooflectures,duration,startingtime);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("Profile").setValue(profileInfo);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        toastfun("Data Register Successfully");
        Intent QueryIntent= new Intent(ProfileFormActivity.this,Query0Activity.class);
        startActivity(QueryIntent);
        finish();
    }
}
