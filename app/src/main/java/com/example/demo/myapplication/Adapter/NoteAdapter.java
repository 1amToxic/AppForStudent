package com.example.demo.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.myapplication.Class.NoteLearn;
import com.example.demo.myapplication.Database.NoteDatabase;
import com.example.demo.myapplication.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    ArrayList<NoteLearn> list = new ArrayList<>();
    Context context;
    NoteDatabase db;
    public NoteAdapter(ArrayList<NoteLearn> list, Context context,NoteDatabase db) {
        this.list = list;
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_note,parent,false);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_recyclerview);
        view.setAnimation(animation);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final NoteLearn note = list.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(note);
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,content;
        ImageView delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
