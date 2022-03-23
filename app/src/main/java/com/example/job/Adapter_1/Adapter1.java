package com.example.job.Adapter_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.job.Model.Add_post;
import com.example.job.R;

import java.util.ArrayList;

public class Adapter1  extends RecyclerView.Adapter<Adapter1.myViewHolder> {
    ArrayList<Add_post> arrayList;
    Context context;

    public Adapter1(ArrayList<Add_post> arrayList,Context context){
        this.arrayList=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_post_item,parent,false);
        return  new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Add_post add_post =arrayList.get(position);
        holder.textView1.setText(add_post.getJob_Title());
        holder.textView2.setText(add_post.getQualification());
        holder.textView3.setText(add_post.getExperience());
        holder.textView4.setText(add_post.getDate());
        holder.textView5.setText(add_post.getSalary());
        holder.textView6.setText(add_post.getJob());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2,textView3,textView4,textView5,textView6;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView34);
            textView2=itemView.findViewById(R.id.textView39);
            textView3=itemView.findViewById(R.id.textView50);
            textView4=itemView.findViewById(R.id.textView52);
            textView5=itemView.findViewById(R.id.textView53);
            textView6=itemView.findViewById(R.id.textView54);
        }
    }
}
