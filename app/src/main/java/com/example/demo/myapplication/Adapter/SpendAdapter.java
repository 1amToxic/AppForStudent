package com.example.demo.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.myapplication.Class.Spend;
import com.example.demo.myapplication.R;

import java.util.ArrayList;

public class SpendAdapter  extends RecyclerView.Adapter<SpendAdapter.MyViewHolder> {
    ArrayList<Spend> listSpend = new ArrayList<>();
    Context context;

    public SpendAdapter(ArrayList<Spend> listSpend, Context context) {
        this.listSpend = listSpend;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_spend,parent,false);
        //add animation
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_recyclerview);
        view.startAnimation(animation);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Spend spend = listSpend.get(position);
        String t = spend.getMoney();
        int size = t.length();
        while(size-3>=0){
            size-=3;
            t = t.substring(0,size)+"."+t.substring(size,t.length());
            // size++;
        }
        if(t.charAt(0)=='.'){
            StringBuilder strb = new StringBuilder(t);
            strb.delete(0,1);
            t = strb.toString();
        }
        holder.money.setText(t);
        holder.spend.setText(spend.getSpend());
    }

    @Override
    public int getItemCount() {
        return listSpend.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView spend,money;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            spend = itemView.findViewById(R.id.spend);
            money = itemView.findViewById(R.id.money);
        }
    }
}
