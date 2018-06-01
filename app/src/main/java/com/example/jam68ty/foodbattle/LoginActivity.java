package com.example.jam68ty.foodbattle;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    Button login;
    EditText memail, mpassword;
    TextView msignup;
    LinearLayout mlayout;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        memail = findViewById(R.id.email);
        mpassword =findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(i);
                }
            }

        };
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthStateListener);

    }

    public void buttonUserLogin_Click(View v) {

        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "processing ", true);
        (auth.signInWithEmailAndPassword(memail.getText().toString(),
                mpassword.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "login sucessful", Toast.LENGTH_SHORT).show();//顯示
                } else {
                    Log.e("Error", task.getException().toString());
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void buttonUserRegister_Click(View v) {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
    }
}