package com.example.bie_daalt_02v;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        User user = userArrayList.get(position);

        holder.description.setText(user.description);
        holder.title.setText(user.title);
//        holder.Age.setText(user.age);
        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to DetailActivity
                Intent intent = new Intent(context, DetailActivity.class);
                // Pass data to DetailActivity using intent.putExtra if needed
                intent.putExtra("Description", user.description);
                intent.putExtra("Title", user.title);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView description, title;
        Button btnViewDetails;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tefirstName);
            title = itemView.findViewById(R.id.telastName);

            checkBox = itemView.findViewById(R.id.checkBox);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails); // Issue here


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    // Handle checkbox state change here
                    if (isChecked) {
                    } else {
                        // Checkbox is unchecked
                        // Change text color to the default color when unchecked
                }
            }
        });
    }
}
}