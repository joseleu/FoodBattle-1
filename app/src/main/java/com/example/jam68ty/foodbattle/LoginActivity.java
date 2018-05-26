package com.example.jam68ty.foodbattle;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button login;
    EditText memail, mpassword;
    TextView msignup;
    LinearLayout mlayout;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.logInBtn);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        msignup = findViewById(R.id.signUp);

        login.setOnClickListener(this);
        msignup.setOnClickListener(this);

        //init firebase auth
        auth = FirebaseAuth.getInstance();

        //check already session , if ok->Menu
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));

        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.signUp) {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        } else if (view.getId() == R.id.logInBtn) {
            loginUser(memail.getText().toString(), mpassword.getText().toString());
        }


    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {

                    if (password.length() < 6) {
                        Snackbar snackbar = Snackbar.make(mlayout , "密碼必須超過6個字元", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }


                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            }
        });

    }

}
