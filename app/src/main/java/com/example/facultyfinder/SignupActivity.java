package com.example.facultyfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {


   private EditText editTextusername;
   private EditText editTextpassword;
   private EditText editTextmail;
   private ProgressDialog progressDialog;
   private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         firebaseAuth = FirebaseAuth.getInstance();
         progressDialog =new ProgressDialog(this);
         editTextusername =(EditText)findViewById(R.id.username);
         editTextpassword =(EditText)findViewById(R.id.password);
         editTextmail =(EditText)findViewById(R.id.mail);
    }
    public void signup(View view)
    {
        String username = editTextusername.getText().toString().trim();
        String mail = editTextmail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        if(TextUtils.isEmpty(mail))
        {
            Toast.makeText(this,"Please enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this,"Please enter Username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(mail,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignupActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
                            Intent LoginIntent= new Intent(SignupActivity.this,LoginActivity.class);
                            startActivity(LoginIntent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this,"Unable to Register.. Try Again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void gotoLogin(View view)
    {
        Intent LoginIntent= new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }
}
