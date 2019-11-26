package com.example.demo.myapplication.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.demo.myapplication.Adapter.NoteAdapter;
import com.example.demo.myapplication.Class.NoteLearn;
import com.example.demo.myapplication.Class.Work;
import com.example.demo.myapplication.Database.NoteDatabase;
import com.example.demo.myapplication.R;
import com.example.demo.myapplication.Utils.KeyBoard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteKnowlegde extends Fragment {


    public NoteKnowlegde() {
        // Required empty public constructor
    }
    NoteDatabase db;
    RecyclerView recyclerNote;
    FloatingActionButton fabNote;
    NoteAdapter adapter;
    int idAuto;
    ImageView  addNote;
    ArrayList<NoteLearn> listNote = new ArrayList<>();
    RelativeLayout layoutAddNote;
    EditText editTitle,editContent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_note_knowlegde, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setListeners();
    }

    public void init(View view){
        db = new NoteDatabase(view.getContext());
        listNote = db.getAllNote();
        idAuto = listNote.size();
        recyclerNote = view.findViewById(R.id.recyclerNote);
        adapter = new NoteAdapter(listNote,getActivity().getApplicationContext(),db);
        recyclerNote.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerNote.setItemAnimator(new DefaultItemAnimator());
        recyclerNote.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        fabNote = view.findViewById(R.id.fabNote);
        addNote = view.findViewById(R.id.addNote);
        layoutAddNote = view.findViewById(R.id.layoutNote);
        editContent = view.findViewById(R.id.editContent);
        editTitle = view.findViewById(R.id.editTitle);
    }
    public void setListeners(){
        fabNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                fabNote.setVisibility(View.INVISIBLE);
                layoutAddNote.setVisibility(View.VISIBLE);
                KeyBoard.showKeyboard(view.getContext());
                editTitle.requestFocus();
            }
        });
        addNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                addItem(editTitle.getText().toString(),editContent.getText().toString());
                fabNote.setVisibility(View.VISIBLE);
                layoutAddNote.setVisibility(View.INVISIBLE);
                KeyBoard.closeKeyboard(view.getContext());
                editTitle.setText("");
                editContent.setText("");
            }
        });

    }
    public void addItem(String title,String content) {
        NoteLearn note = new NoteLearn(++idAuto,title,content);
        db.addNote(note);
        listNote.add(note);
        adapter.notifyDataSetChanged();
    }
}
