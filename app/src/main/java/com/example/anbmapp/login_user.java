package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class login_user extends AppCompatActivity {

    CheckBox _chkboxloginUser;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;
    private String Username,Password,Password2,Email;
    Boolean check3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        final EditText etUsername = (EditText) findViewById(R.id.etxtuserName);
        final EditText etPassword = (EditText) findViewById(R.id.etxtpass);

        final Button bLogin = (Button) findViewById(R.id.btnloginUser);
        _chkboxloginUser = (CheckBox) findViewById(R.id.chkboxloginUser);

        //using shredpreferences
        sharedPreferences3 = getSharedPreferences("loginref3", MODE_PRIVATE);
        editor3 = sharedPreferences3.edit();

        etUsername.setText(sharedPreferences3.getString("Username",null));
        etPassword.setText(sharedPreferences3.getString("Password",null));


        check3 = sharedPreferences3.getBoolean("state",false);
        if(check3){
            _chkboxloginUser.setChecked(true);
        }


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(login_user.this);
                progressDialog.setMessage("Loading.....");
                progressDialog.show();

                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();


                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String Id = jsonResponse.getString("Id");
                                String Full_Name = jsonResponse.getString("Full_Name");
                                String Gender = jsonResponse.getString("Gender");
                                String Address = jsonResponse.getString("Address");
                                String Phone = jsonResponse.getString("Phone");
                                Email = jsonResponse.getString("Email");
                                Password2 = jsonResponse.getString("Password");

                                //condition for checkbox to use shredpreferences

                                if(_chkboxloginUser.isChecked()) {
                                    editor3.putBoolean("state", true);
                                    editor3.putString("Username", Username);
                                    editor3.putString("Password", Password2);
                                    editor3.commit();
                                }else {
                                    editor3.remove("Username");
                                    editor3.remove("Password");
                                    editor3.putBoolean("state", false);
                                    editor3.commit();
                                }


                                Intent intent = new Intent(login_user.this, MainActivity.class);

                                intent.putExtra("Id", Id);
                                intent.putExtra("Full_Name", Full_Name);
                                intent.putExtra("Gender", Gender);
                                intent.putExtra("Address", Address);
                                intent.putExtra("Phone", Phone);
                                intent.putExtra("Username", Username);
                                intent.putExtra("Email", Email);
                                login_user.this.startActivity(intent);
                                finish();



                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(login_user.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(Username, Password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(login_user.this);
                queue.add(loginRequest);
            }
        });
    }
    public void tv_User_Register(View view) {
        startActivity(new Intent(getApplicationContext(), register_user.class));
        //method, while clicking create new account
    }

    public void gotoforgotuserpwactivity(View view) {
       Intent intent = new Intent(login_user.this,FogotPasswordUser.class);
        intent.putExtra("Email", Email);
        login_user.this.startActivity(intent);

    }
}