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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {


   private EditText editTextcpassword;
   private EditText editTextpassword;
   private EditText editTextmail;
   private TextInputLayout mail;
   private TextInputLayout password;
   private TextInputLayout confirmpassword;
   private ProgressDialog progressDialog;
   private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         firebaseAuth = FirebaseAuth.getInstance();
         progressDialog =new ProgressDialog(this);
         editTextcpassword =(EditText)findViewById(R.id.confirmpassword);
         editTextpassword =(EditText)findViewById(R.id.password);
         editTextmail =(EditText)findViewById(R.id.mail);
         confirmpassword=(TextInputLayout)findViewById(R.id.Inputsignup_confirmpassword);
         password=(TextInputLayout)findViewById(R.id.Inputsignup_password);
         mail=(TextInputLayout)findViewById(R.id.Inputsignup_mail);
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
    public void signup(View view)
    {
        String scpassword = editTextcpassword.getText().toString().trim();
        String smail = editTextmail.getText().toString().trim();
        String spassword = editTextpassword.getText().toString().trim();
        if(TextUtils.isEmpty(smail))
        {
            mail.setError("Field can't be empty");
            return;
        }
        else
        {
            mail.setError(null);
        }
        if(TextUtils.isEmpty(scpassword))
        {
            confirmpassword.setError("Field can't be empty");
            return;
        }
        else
        {
            confirmpassword.setError(null);
        }
        if(TextUtils.isEmpty(spassword))
        {
            password.setError("Field can't be empty");
            return;
        }
        else
        {
            password.setError(null);

        }
        if(!spassword.equals(scpassword))
        {
            confirmpassword.setError("Password doesn't match");
            return;
        }
        else
        {
            confirmpassword.setError(null);
        }
        boolean chkinternt=chkinternet();
        if(!chkinternt)
        {
            Toast.makeText(this,"No internet Connection..",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(smail,spassword).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignupActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
                            Intent profileIntent= new Intent(SignupActivity.this,ProfileFormActivity.class);
                            startActivity(profileIntent);
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
