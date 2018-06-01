package com.example.jam68ty.foodbattle;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    private EditText textAccount;
    private EditText textEmail;
    private EditText textPassword;
    private EditText textPassword2;
    private Button register;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestoreStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//態數據以key-value的形式放入
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);//實現不同Layout的轉換功能

        firebaseAuth = FirebaseAuth.getInstance();
        textAccount = findViewById(R.id.sign_up_account);
        textEmail = findViewById(R.id.sign_up_email);
        textPassword = findViewById(R.id.sign_up_password);//找xml布局文件下的具体widget控件(如Button、TextView等)
        textPassword2 = findViewById(R.id.check_password);
        firebaseAuth = FirebaseAuth.getInstance();//使用getInstance()获取到你的数据库实例，当你打算写库的时候使用它来引用你的写入位置。
        register = findViewById(R.id.register_button);

        firebaseFirestoreStore = FirebaseFirestore.getInstance();
    }


    public void Register_Click(View v) {
        final ProgressDialog progressDialog = ProgressDialog.show(SignUpActivity.this, "Please wait", "processing ", true);
        Task<AuthResult> authResultTask = (firebaseAuth.createUserWithEmailAndPassword(textEmail.getText().toString(),
                textPassword.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "註冊成功", Toast.LENGTH_SHORT).show();//顯示


                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Map<String, String> userMap = new HashMap<>();
                    String name = textAccount.getText().toString();
                    String email = textEmail.getText().toString();
                    userMap.put("name", name);
                    userMap.put("email", email);
                    String userId = user.getUid();
                    firebaseFirestoreStore.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignUpActivity.this, "Information Saved...", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = e.getMessage();
                                    Toast.makeText(SignUpActivity.this, "Error" + error, Toast.LENGTH_LONG).show();
                                }
                            });


                    Intent i = new Intent(SignUpActivity.this, MenuActivity.class);
                    startActivity(i);

                } else {
                    Log.e("Error", task.getException().toString());
                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void login_direct(View v) {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
