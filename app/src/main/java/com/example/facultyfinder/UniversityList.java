package com.example.facultyfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UniversityList extends AppCompatActivity {

    TextView name;
    DatabaseReference reference;
    RecyclerView universitylist;
    ArrayList<university_name> list;
    private ProgressDialog progressDialog;
    UniversityAdapter universityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_list);

        name = (TextView)findViewById(R.id.university_name);
        universitylist = findViewById(R.id.unilist);
        progressDialog =new ProgressDialog(this);
        universitylist.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<university_name>();
        Log.w("thala","kingasd");
        reference = FirebaseDatabase.getInstance().getReference();
        Log.w("thala","kings");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot node: dataSnapshot.getChildren())
                {
//                    Log.w("thala","ing");
                    DataSnapshot profile = node.child("Profile");
//                    Log.w("thala","g");
                    ProfileInfo pro = profile.getValue(ProfileInfo.class);
                    String s = pro.getOrganisationname().toLowerCase();
                    String res = Camelcaseit(s);
                    Log.w("thala",res);
                    university_name u = new university_name(res);
                    list.add(u);

                }
                universityAdapter = new UniversityAdapter(UniversityList.this, list);
                universitylist.setAdapter(universityAdapter);
                universityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

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
    public void gotoLogin(View view)
    {
        Intent LoginIntent= new Intent(UniversityList.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }
}