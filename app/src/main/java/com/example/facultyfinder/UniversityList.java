package com.example.facultyfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    UniversityAdapter universityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_list);

        name = (TextView)findViewById(R.id.university_name);
        universitylist = findViewById(R.id.unilist);
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
                    Log.w("thala","ing");
                    DataSnapshot profile = node.child("Profile");
                    Log.w("thala","g");
                    ProfileInfo pro = profile.getValue(ProfileInfo.class);
                    university_name u = new university_name(pro.getOrganisationname());
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
}