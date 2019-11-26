package com.example.demo.myapplication.Fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.demo.myapplication.Adapter.WorkAdapter;
import com.example.demo.myapplication.Class.Work;
import com.example.demo.myapplication.Database.TodoDatabase;
import com.example.demo.myapplication.R;
import com.example.demo.myapplication.Utils.KeyBoard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends Fragment implements View.OnClickListener {


    private static final String CHANNEL_ID = "ID";

    public TodoFragment() {
        // Required empty public constructor
    }

    int mYear, mMonth, mDay, mHour, mMinute;
    RecyclerView recyclerToDo;
    TodoDatabase db;
    ArrayList<Work> listWork = new ArrayList<>();
    WorkAdapter adapter;
    ImageView datePicker, timePicker;
    TextView textDate, textTime;
    FloatingActionButton btnAdd;
    ImageView btnAddToRe;
    int idAuto;
    RelativeLayout addTaskLayout;
    EditText task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        return view;
    }

    private void setNotification() {
        for (Work work : listWork) {
            Calendar c = Calendar.getInstance(), calendar = Calendar.getInstance();
            StringTokenizer stk = new StringTokenizer(work.getTimeDL(), ":");
            String s[] = new String[2];
            int i = 0;
            while (stk.hasMoreTokens()) {
                s[i++] = stk.nextToken();
            }
            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(s[1]));
            if (c.getTimeInMillis() - calendar.getTimeInMillis() < 10 * 60 * 1000 &&
                    c.getTimeInMillis() - calendar.getTimeInMillis()>0) {
                addService(work.getWork(),c);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setListener();
        createNotificationChannel();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(2 * 60 * 1000);
                        setNotification();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }



    private void setListener() {
        datePicker.setOnClickListener(this);
        timePicker.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnAddToRe.setOnClickListener(this);
    }

    public void init(View view) {

        db = new TodoDatabase(getContext());

        listWork = db.getAllWork();
        idAuto = listWork.size();
        recyclerToDo = view.findViewById(R.id.recyclerMydate);
        adapter = new WorkAdapter(listWork, getActivity().getApplicationContext(), db);
        recyclerToDo.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerToDo.setItemAnimator(new DefaultItemAnimator());
        recyclerToDo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        textDate = view.findViewById(R.id.textDate);
        textTime = view.findViewById(R.id.textTime);
        btnAdd = view.findViewById(R.id.fab);
        btnAddToRe = view.findViewById(R.id.addToRecy);
        addTaskLayout = view.findViewById(R.id.addTaskLayout);
        task = view.findViewById(R.id.task);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.datePicker: {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //  String dateString = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                                textDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            }
            case R.id.timePicker: {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                //Intent intent = new Intent(getContext(), AlarmReceiver.class);
                                textTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            }
            case R.id.fab: {
                btnAdd.setVisibility(View.INVISIBLE);
                addTaskLayout.setVisibility(View.VISIBLE);
                KeyBoard.showKeyboard(getContext());
                task.requestFocus();
                break;
            }
            case R.id.addToRecy: {
                addItem(task.getText().toString(), textDate.getText().toString(), textTime.getText().toString());
                textTime.setText("");
                textDate.setText("");
                task.setText("");
                addTaskLayout.setVisibility(View.INVISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                KeyBoard.closeKeyboard(getContext());
                break;
            }
        }
    }

    public void addItem(String task, String date, String time) {
        Work work = new Work(++idAuto, task, date, time);
        db.addWork(work);
        listWork.add(work);
        adapter.notifyDataSetChanged();
    }
    public void addService(String content,Calendar c) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                .setContentTitle("Notification")
                .setContentText(content)
                .setWhen(c.getTimeInMillis())
                .setShowWhen(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(1, mBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
