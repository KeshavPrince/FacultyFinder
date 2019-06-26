package com.example.facultyfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
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
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void toastfun(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    public Integer totimee(String s)
    {
        Integer res;
        String ns="";
        for(int i=0;i<s.length();i++)
            if(s.charAt(i)!=':')
                ns+=s.charAt(i);
        res=Integer.parseInt(ns);
        return res;

    }
    public void done(View view)
    {
        String username = editTextusername.getText().toString().trim();
        if(TextUtils.isEmpty(username))
        {
            toastfun("Username can't be empty");
            return;
        }
        String organisationname = editTextnameoforganisation.getText().toString().trim();
        if(TextUtils.isEmpty(organisationname))
        {
            toastfun("Name of Organisation can't be empty");
            return;
        }
        String phonenostr = editTextphonenumber.getText().toString().trim();
        if(TextUtils.isEmpty(phonenostr))
        {
            toastfun("Username can't be empty");
            return;
        }
        if(phonenostr.length()!=10) {
            toastfun("Incorrect Phone Number");
            return;
        }
        String nooflecturesstr = editTextnooflectures.getText().toString().trim();
        if(TextUtils.isEmpty(nooflecturesstr))
        {
            toastfun("Number of lectures can't be empty");
            return;
        }
        if(nooflecturesstr.equals("0") || nooflecturesstr.length()>=3)
        {
            toastfun("Number of lectures is invaild");
            return;
        }
        Integer nooflectures=Integer.parseInt(nooflecturesstr);	;
        String durationstr = editTextduration.getText().toString().trim();
        if(durationstr.length()!=5)
        {
            toastfun("Invaild Duration");
            return;
        }
        Integer duration = totimee(durationstr);
        String startingtimestr=editTextstartingtime.getText().toString().trim();
        if(startingtimestr.length()!=5)
        {
            toastfun("Invaild Starting time");
            return;
        }
        Integer startingtime = totimee(startingtimestr);
        ProfileInfo profileInfo = new ProfileInfo(username,phonenostr,organisationname,nooflectures,duration,startingtime);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(profileInfo);
        toastfun("Data Register Successfully");
        Intent QueryIntent= new Intent(ProfileFormActivity.this,Query0Activity.class);
        startActivity(QueryIntent);
        finish();
    }
}
