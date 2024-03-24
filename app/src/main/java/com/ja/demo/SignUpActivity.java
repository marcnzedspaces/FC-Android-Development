package com.ja.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText email, pwd, cpwd;
    private Button su;
    private FirebaseAuth auth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.semail);
        pwd = findViewById(R.id.spassword);
        cpwd = findViewById(R.id.scpassword);
        su = findViewById(R.id.subtn);
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        pd.setTitle("Creating Account");
        pd.setCanceledOnTouchOutside(false);

        su.setOnClickListener(view -> {
            pd.show();
            String pw = pwd.getText().toString(), cpw = cpwd.getText().toString();
            if(pw.equals(cpw)){
                auth.createUserWithEmailAndPassword(email.getText().toString().trim(), pw).addOnCompleteListener(task ->{
                    pd.dismiss();
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else
                        Toast.makeText(SignUpActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else{
                pd.dismiss();
                Toast.makeText(SignUpActivity.this,"Passwords do not match", Toast.LENGTH_SHORT).show();

            }
        });
    }
}