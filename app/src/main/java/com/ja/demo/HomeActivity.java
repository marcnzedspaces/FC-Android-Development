package com.ja.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton logout;
    private RecyclerView recyclerView;
    private List<Item> itemList;
    public static ItemAdapter itemAdapter;
    private FloatingActionButton newItem;
    private ProgressBar pb;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference(uid);

        pb = findViewById(R.id.pb);
        logout = findViewById(R.id.logout);
        newItem = findViewById(R.id.nibtn);
        recyclerView = findViewById(R.id.recyclerView);
        itemList = new ArrayList<Item>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(itemList, this, uid);
        recyclerView.setAdapter(itemAdapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot){
                itemList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Item item = postSnapshot.getValue(Item.class);
                    item.setKey(postSnapshot.getKey());
                    itemList.add(item);
                }
                itemAdapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error){

            }
        });

        newItem.setOnClickListener(view ->{
           Intent i = new Intent(HomeActivity.this, NewItemActivity.class);
           i.putExtra("uid", uid);
           startActivity(i);
        });

        logout.setOnClickListener(view ->{
            auth.signOut();
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
    }
}