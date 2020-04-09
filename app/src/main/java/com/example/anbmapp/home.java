package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//
//        ViewPager viewPager  = findViewById(R.id.viewPager1);
//        ImageAdapter adapter = new ImageAdapter(this);
//        viewPager.setAdapter(adapter);
    }





    public void btn_login_as_guest_form(View view){
        startActivity(new Intent(getApplicationContext(),login_user.class));
    }


    public void btn_login_as_band_form(View view){
        startActivity(new Intent(getApplicationContext(),login_bandmember.class));
    }

    public void btn_login_band_member(View view) {
        startActivity(new Intent(getApplicationContext(),LoginBandMembers.class));
    }
}
