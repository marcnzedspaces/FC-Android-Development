package com.ja.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.viewHolder> {
    private List<Item> itemList;
    private Context context;
    private String uid;

    public ItemAdapter(List<Item> itemList, Context context, String uid){
        this.itemList = itemList;
        this.context = context;
        this.uid = uid;
    }

    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getTitle());
        holder.itemDesc.setText(item.getTitle());
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public TextView itemName, itemDesc;
        public Button delBtn;
        public viewHolder(@NotNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDesc = itemView.findViewById(R.id.itemDesc);
            delBtn = itemView.findViewById(R.id.itemDelete);
            delBtn.setOnClickListener(view ->{
                int position = getAdapterPosition();
                Item i = itemList.get(position);
                FirebaseDatabase.getInstance().getReference(uid).child(i.getKey()).removeValue();
                itemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itemList.size());
            });
        }

    }
}
