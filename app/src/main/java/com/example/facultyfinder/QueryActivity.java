package com.example.facultyfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QueryActivity extends AppCompatActivity {

    private TextView textwelcome;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            Intent LoginIntent= new Intent(QueryActivity.this,LoginActivity.class);
            startActivity(LoginIntent);
            finish();
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();
        textwelcome = (TextView)findViewById(R.id.textViewwelcome);
        textwelcome.setText("Welcome !" + user.getEmail());
    }

    public void signout(View view)
    {
        firebaseAuth.signOut();
        Intent LoginIntent= new Intent(QueryActivity.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }

}
