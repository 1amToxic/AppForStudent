package com.example.demo.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.myapplication.Fragment.InfoFragment;
import com.example.demo.myapplication.Fragment.LoveFragment;
import com.example.demo.myapplication.Fragment.NoteKnowlegde;
import com.example.demo.myapplication.Fragment.SpendFragment;
import com.example.demo.myapplication.Fragment.TodoFragment;
import com.example.demo.myapplication.Utils.KeyBoard;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TextView textDate,tvMoney,calendar,notesj,heart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_main);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navi_drawer_open,R.string.navi_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        init();
        setTime();
        setListener();
    }
    public void setTime(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);
                    textDate.setText(mHour + " : " + mMinute);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

    }
    public void setListener(){
        heart.setOnClickListener(this);
        calendar.setOnClickListener(this);
        notesj.setOnClickListener(this);
        tvMoney.setOnClickListener(this);
    }
    public void init(){
        heart = findViewById(R.id.tvHeart);
        calendar = findViewById(R.id.calendar);
        notesj = findViewById(R.id.notesubject);
        textDate = findViewById(R.id.textDate);
        tvMoney = findViewById(R.id.tvMoney);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(item.getItemId()==R.id.itemInfo){
            getSupportActionBar().setTitle("Giới thiệu");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(drawerLayout.isDrawerOpen(Gravity.START))
                drawerLayout.closeDrawer(Gravity.START);
            transaction.replace(R.id.container_view,new InfoFragment());
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){

            case R.id.calendar:{
                getSupportActionBar().setTitle("Ghi chú công việc");
                drawerLayout.closeDrawer(Gravity.START);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_view,new TodoFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case R.id.tvMoney:{
                getSupportActionBar().setTitle("Chi tiêu");
                drawerLayout.closeDrawer(Gravity.START);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_view,new SpendFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case R.id.notesubject:{
                getSupportActionBar().setTitle("Ghi chú học tập");
                drawerLayout.closeDrawer(Gravity.START);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_view,new NoteKnowlegde());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case R.id.tvHeart:{
                getSupportActionBar().setTitle("Tình yêu");
                drawerLayout.closeDrawer(Gravity.START);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_view,new LoveFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        KeyBoard.closeKeyboard(this);
        super.onPause();
    }
}
