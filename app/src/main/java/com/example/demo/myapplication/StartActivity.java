package com.example.demo.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        imageView =  findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        btnStart = findViewById(R.id.btnStart);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_shape);
        imageView.setAnimation(animation);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.anim_text);
        textView.setAnimation(animation1);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
