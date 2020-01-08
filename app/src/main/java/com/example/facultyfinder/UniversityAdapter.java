package com.example.facultyfinder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.MyViewHolder> {

    Context context;
    ArrayList<university_name> names;

    public UniversityAdapter(Context c, ArrayList<university_name> name) {
        this.context = c;
        this.names = name;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_university,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.Uname.setText(names.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Query0Activity.class);
                intent.putExtra("name",names.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder {

        TextView Uname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Uname = (TextView) itemView.findViewById(R.id.university_name);
        }
    }
}
