package com.example.demo.myapplication.Fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.myapplication.MainActivity;
import com.example.demo.myapplication.R;
import com.example.demo.myapplication.Utils.WaveHelper;
import com.gelitenight.waveview.library.WaveView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoveFragment extends Fragment {


    public LoveFragment() {
        // Required empty public constructor
    }
    RelativeLayout layoutFa,layoutYes,layoutQ;
    Button btnFa,btnIl;
    @SuppressLint("WrongViewCast")
    WaveView wave;
    WaveHelper waveHelper;
    ImageView pickDate;
    TextView date,instruct;
    EditText nameGirl,nameBoy;
    long timeDating;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_love, container, false);
        init(v);

        btnFa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make your toast here
                layoutQ.setVisibility(View.INVISIBLE);
                layoutFa.setVisibility(View.VISIBLE);
            }
        });
        btnIl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutQ.setVisibility(View.INVISIBLE);
                layoutYes.setVisibility(View.VISIBLE);
            }
        });

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });
        return v;
    }
    public void init(View v){
        btnIl = v.findViewById(R.id.btnIl);
        layoutYes = v.findViewById(R.id.layoutYes);
        layoutQ = v.findViewById(R.id.questions);
        layoutFa = v.findViewById(R.id.layoutFa);
        btnFa = (Button) v.findViewById(R.id.btnFa);
        wave = v.findViewById(R.id.wave);
        wave.setWaveColor(Color.parseColor("#F06292"), Color.parseColor("#C2185B"));
        wave.setShowWave(true);
        waveHelper = new WaveHelper(wave);
        waveHelper.start();
        pickDate = v.findViewById(R.id.pickdate);
        date = v.findViewById(R.id.date);
//        instruct = v.findViewById(R.id.instruct);
        nameBoy = v.findViewById(R.id.nameBoy);
        nameGirl = v.findViewById(R.id.nameGirl);
        loadSharePrefer();
    }
    public void datePicker(){
//        instruct.setVisibility(View.INVISIBLE);
        final Calendar c = Calendar.getInstance(),calendar =Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //  String dateString = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.MONTH,monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        long day = (calendar.getTimeInMillis()-c.getTimeInMillis())/(1000*60*60*24);
                        timeDating = c.getTimeInMillis();
                        if(day>1)
                            date.setText(day+" days");
                        else date.setText(day+" day");
                        date.requestFocus();
                        saveSharePrefer();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void loadSharePrefer() {
        Log.d("AppLog", "Load");
        SharedPreferences shared = getContext().getSharedPreferences("Sum", Context.MODE_PRIVATE);
        nameBoy.setText(shared.getString("Boy","1"));
        nameGirl.setText(shared.getString("Girl","1"));
//        if(shared.getInt("Help",2)==1){
//            instruct.setVisibility(View.INVISIBLE);
//        }else{
//            instruct.setVisibility(View.VISIBLE);
//        }
        timeDating = shared.getLong("Date",1);
        Calendar calendar = Calendar.getInstance();
        long day = (calendar.getTimeInMillis()-timeDating)/(1000*60*60*24);
        if(day>1)
            date.setText(day+" days");
        else date.setText(day+" day");

    }

    public void saveSharePrefer() {
        SharedPreferences shared = getContext().getSharedPreferences("Sum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("Boy",nameBoy.getText().toString());
        editor.putString("Girl",nameGirl.getText().toString());
        editor.putLong("Date",timeDating);
       // editor.putInt("Help",instruct.getVisibility());
        //  Log.d("AppLog", "Save");
        editor.apply();
    }
}
