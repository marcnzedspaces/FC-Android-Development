package com.ja.demo; // Package name for your app. Helps to organise

// Import statements
import androidx.appcompat.app.AppCompatActivity; // Provides foundation for most activities of most modern android apps

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle; // Collection of data that can be passed between activities
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login, signup;
    private FirebaseAuth auth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.lemail);
        password = findViewById(R.id.lpassword);
        login = findViewById(R.id.lbtn);
        signup = findViewById(R.id.sbtn);
        auth = FirebaseAuth.getInstance();
        pd =  new ProgressDialog(this);
        pd.setTitle("Logging In");
        pd.setCanceledOnTouchOutside(false);

        login.setOnClickListener(view -> {
            pd.show();
            auth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString()).addOnCompleteListener(task ->{
                pd.dismiss();
                if(task.isSuccessful()){
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        signup.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SignUpActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null){
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}