package com.example.anbmapp;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class toolbar extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(toolbar);
        TextView imageView = (TextView)findViewById(R.id.homeLogout);

    }
}
