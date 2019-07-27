package com.example.facultyfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextpassword;
    private EditText editTextmail;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            Intent QueryIntent= new Intent(LoginActivity.this,Query0Activity.class);
            startActivity(QueryIntent);
            finish();
        }
        progressDialog =new ProgressDialog(this);
        editTextpassword =(EditText)findViewById(R.id.password);
        editTextmail =(EditText)findViewById(R.id.mail);
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
    public void login(View view)
    {
        String mail = editTextmail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        if(TextUtils.isEmpty(mail))
        {
            editTextmail.setError("Field can't be empty");
            return;
        }
        else
        {
            editTextmail.setError(null);
        }
        if(TextUtils.isEmpty(password))
        {
            editTextmail.setError("Field can't be empty");
            return;
        }
        else
        {
            editTextmail.setError(null);
        }
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Authenticating User...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(mail,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Intent QueryIntent= new Intent(LoginActivity.this,Query0Activity.class);
                            startActivity(QueryIntent);
                            finish();
                            //Toast.makeText(LoginActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Wrong credentials.. Try Again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void gotoSignup(View view)
    {
        Intent SignupIntent= new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(SignupIntent);
        finish();
    }
}
