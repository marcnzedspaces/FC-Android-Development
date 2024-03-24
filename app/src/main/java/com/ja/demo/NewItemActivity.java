package com.ja.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewItemActivity extends AppCompatActivity {

    private DatabaseReference ref;
    private EditText title, desc;
    private Button add;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        ref = FirebaseDatabase.getInstance().getReference(getIntent().getStringExtra("uid"));

        title = findViewById(R.id.iname);
        desc = findViewById(R.id.idesc);
        add = findViewById(R.id.abtn);
        pd = new ProgressDialog(this);
        pd.setTitle("Adding new item");
        pd.setCanceledOnTouchOutside(false);

        add.setOnClickListener(view -> {
            pd.show();
            Item item = new Item(title.getText().toString().trim(), desc.getText().toString().trim());
            ref.child(ref.push().getKey()).setValue(item).addOnCompleteListener(task -> {
                pd.dismiss();
                if(task.isSuccessful())
                    Toast.makeText(NewItemActivity.this, "New item was added to list successfully.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(NewItemActivity.this, "Unable to add item to list.", Toast.LENGTH_SHORT).show();
            });
        });
    }
}