package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login_bandmember extends AppCompatActivity {
    ProgressDialog pd;
    CheckBox _chkboxloginbmanager;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;
    private String Band_Name,Bandcode,Bandcode2,Password,Password2;
    Boolean check2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bandmember);

        final EditText _etxtBandnameLogin = (EditText) findViewById(R.id.etxtBandnameLogin);
        final EditText _etxtPasswordBandLogin = (EditText) findViewById(R.id.etxtPasswordBandLogin);
        final EditText _etxtBandcodeLogin = (EditText) findViewById(R.id.etxtBandcodeLogin);
        final Button _btnBandLogin = (Button) findViewById(R.id.btnBandLogin);
        _chkboxloginbmanager = (CheckBox) findViewById(R.id.chkboxloginbmanager);

        //using shredpreferences
        sharedPreferences2 = getSharedPreferences("loginref2", MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();

        _etxtBandnameLogin.setText(sharedPreferences2.getString("Band_Name",null));
        _etxtPasswordBandLogin.setText(sharedPreferences2.getString("Password",null));
        _etxtBandcodeLogin.setText(sharedPreferences2.getString("Bandcode",null));

        check2 = sharedPreferences2.getBoolean("state",false);
        if(check2){
            _chkboxloginbmanager.setChecked(true);
        }


        _btnBandLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(login_bandmember.this);
                pd.setMessage("loading wait");
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setIndeterminate(true);
                pd.setProgress(0);
                pd.show();
                Band_Name = _etxtBandnameLogin.getText().toString();
                Password = _etxtPasswordBandLogin.getText().toString();
                Bandcode = _etxtBandcodeLogin.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String Band_Id = jsonResponse.getString("Band_Id");
                                String Band_Name = jsonResponse.getString("Band_Name");
                                String Email = jsonResponse.getString("Email");
                                Password2 = jsonResponse.getString("Password");
                                Bandcode2 = jsonResponse.getString("Bandcode");

                                //condition for checkbox to use shredpreferences

                                if(_chkboxloginbmanager.isChecked()) {
                                    editor2.putBoolean("state", true);
                                    editor2.putString("Band_Name", Band_Name);
                                    editor2.putString("Password", Password2);
                                    editor2.putString("Bandcode", Bandcode2);
                                    editor2.commit();
                                }else {
                                    editor2.remove("Band_Name");
                                    editor2.remove("Password");
                                    editor2.remove("Bandcode");
                                    editor2.putBoolean("state", false);
                                    editor2.commit();
                                }


                                Intent intent = new Intent(login_bandmember.this, DashBoardBandProfile.class);
                                intent.putExtra("Band_Id", Band_Id);
                                intent.putExtra("Band_Name", Band_Name);
                                intent.putExtra("Email", Email);
                                login_bandmember.this.startActivity(intent);
                                finish();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(login_bandmember.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }pd.dismiss();

                    }
                };

                BandLoginRequest bandLoginRequest = new BandLoginRequest(Band_Name, Password, Bandcode, responseListener);
                RequestQueue queue = Volley.newRequestQueue(login_bandmember.this);
                queue.add(bandLoginRequest);
            }
        });
    }

    public void tv_Band_Register(View view){
        startActivity(new Intent(getApplicationContext(),Band_register.class));
    }
}