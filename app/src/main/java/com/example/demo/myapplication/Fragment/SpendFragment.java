package com.example.demo.myapplication.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.demo.myapplication.Adapter.SpendAdapter;
import com.example.demo.myapplication.Class.Spend;
import com.example.demo.myapplication.Database.SpendDatabase;
import com.example.demo.myapplication.R;
import com.example.demo.myapplication.Utils.KeyBoard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpendFragment extends Fragment implements View.OnClickListener {


    public SpendFragment() {
        // Required empty public constructor
    }

    int idAuto = 0;
    int sumFirst = 0;
    Spinner spinner;
    EditText moneySum, editSpend, editMoney;
    RecyclerView recyclerSpend;
    FloatingActionButton addFab;
    RelativeLayout addSpendLayout;
    ImageView imageAdd;
    SpendDatabase spendDb;
    SpendAdapter adapter;
    TextView summary, warning;
    ArrayList<Spend> listSpend = new ArrayList<>();
    ArrayList<String> listMonth = new ArrayList<>();
    Animation animation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setListeners();
        addToSpinners(view);
        loadSharePrefer();
        processsingSum();
    }

    public void addToSpinners(View view) {
        for (int i = 1; i <= 12; i++) {
            listMonth.add("Thang " + i);
        }
        ArrayAdapter<String> adapterSp = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listMonth);
        adapterSp.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapterSp);
    }

    public void init(View view) {
        spinner = view.findViewById(R.id.spinner);
        moneySum = view.findViewById(R.id.editSum);
        editSpend = view.findViewById(R.id.editSpend);
        editMoney = view.findViewById(R.id.editMoney);
        recyclerSpend = view.findViewById(R.id.recyclerSpend);
        addFab = view.findViewById(R.id.addSpendFab);
        addSpendLayout = view.findViewById(R.id.addSpendLayout);
        imageAdd = view.findViewById(R.id.imageAdd);
        summary = view.findViewById(R.id.summary);
        spendDb = new SpendDatabase(getContext());
        listSpend = spendDb.getAllSpend();
        idAuto = listSpend.size();
        adapter = new SpendAdapter(listSpend, getActivity().getApplicationContext());
        recyclerSpend.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerSpend.setItemAnimator(new DefaultItemAnimator());
        recyclerSpend.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        warning = view.findViewById(R.id.warning);

        if(listSpend.size()>0) {
            loadSharePrefer();
            processsingSum();
        }
        else{
            moneySum.setText("");
            summary.setText("");
            warning.setText("");
        }
    }

    public void setListeners() {
        addFab.setOnClickListener(this);
        imageAdd.setOnClickListener(this);
        moneySum.setOnClickListener(this);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.addSpendFab: {

                addFab.setVisibility(View.INVISIBLE);
                addSpendLayout.setVisibility(View.VISIBLE);
                editSpend.requestFocus();
                KeyBoard.showKeyboard(getContext());
                break;
            }
            case R.id.imageAdd: {
                String s = moneySum.getText().toString();
                int size = s.length()-1;
                while (size>=0){
                    if(s.charAt(size)=='.'){
                        s = s.substring(0,size)+s.substring(size+1,s.length());
                    }
                    size--;
                }
                sumFirst = Integer.parseInt(s);
                addItem(editSpend.getText().toString(), editMoney.getText().toString());
                addSpendLayout.setVisibility(View.INVISIBLE);
                addFab.setVisibility(View.VISIBLE);
                editMoney.setText("");
                editSpend.setText("");
                KeyBoard.closeKeyboard(getContext());
                processsingSum();
                saveSharePrefer();
                break;
            }
        }
    }

    private void processsingSum() {
        int sumUsed = 0;
        for (Spend s : listSpend) {
            sumUsed += Integer.parseInt(s.getMoney());
        }

        summary.setText(changeToBeauty(sumUsed+""));

        if (sumUsed > sumFirst) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("Bạn đã tiêu quá " + changeToBeauty((sumUsed - sumFirst)+""));
        }
        else warning.setText("");
    }
    public String changeToBeauty(String t){
        int size = t.length();
        while(size-3>=0){
            size-=3;
            t = t.substring(0,size)+"."+t.substring(size,t.length());
        }
        if(t.charAt(0)=='.'){
            StringBuilder strb = new StringBuilder(t);
            strb.delete(0,1);
            t = strb.toString();
        }
        return t;
    }
    public void addItem(String spend, String money) {
        Spend spent = new Spend(++idAuto, spend, money);
        spendDb.addSpend(spent);
        listSpend.add(spent);
        adapter.notifyDataSetChanged();
    }

    public void loadSharePrefer() {
        Log.d("AppLog", "Load");
        SharedPreferences shared = getContext().getSharedPreferences("Sum", Context.MODE_PRIVATE);
        sumFirst = shared.getInt("SumFirs",0);
        moneySum.setText(changeToBeauty(sumFirst+""));

        Log.d("AppLog",changeToBeauty(sumFirst+""));
        summary.setText(shared.getString("Summary", ""));
        spinner.setSelected(true);
        spinner.setSelection(shared.getInt("Spinner",0));
      //  Log.d("AppLog",spinner.getSelectedItemPosition()+"-1");
        String t = shared.getString("Owe", "");
        processsingSum();
        if (t.equals("")) warning.setVisibility(View.INVISIBLE);
        else {
            warning.setText(t);
            warning.setVisibility(View.VISIBLE);
        }
        //  }
    }

    public void saveSharePrefer() {
        SharedPreferences shared = getContext().getSharedPreferences("Sum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("SumFirs", sumFirst);
        editor.putString("Summary", summary.getText().toString());
        editor.putString("Owe", warning.getText().toString());
        editor.putInt("Spinner",spinner.getSelectedItemPosition());
    //    Log.d("AppLog",spinner.getSelectedItemPosition()+"");
      //  Log.d("AppLog", "Save");
        editor.apply();
    }
}
