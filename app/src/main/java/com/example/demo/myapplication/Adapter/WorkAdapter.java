package com.example.demo.myapplication.Adapter;

import android.content.Context;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.myapplication.Class.Work;
import com.example.demo.myapplication.Database.TodoDatabase;
import com.example.demo.myapplication.R;

import java.util.ArrayList;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder> {
    ArrayList<Work> listWork = new ArrayList<>();
    Context context;
    TodoDatabase db;
    public WorkAdapter(ArrayList<Work> listWork, Context context,TodoDatabase db) {
        this.listWork = listWork;
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_recyclerview);
        view.startAnimation(animation);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Work work = listWork.get(position);
        holder.work.setText(work.getWork());
        holder.date.setText(work.getDateDL());
        holder.time.setText(work.getTimeDL());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listWork.remove(position);
                notifyDataSetChanged();
                db.deleteWork(work);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listWork.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView work,date,time;
        ImageView delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            work = itemView.findViewById(R.id.work);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
