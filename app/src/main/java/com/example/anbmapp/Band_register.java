package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;


public class Band_register extends AppCompatActivity {
    Button buttonSignupband;
    EditText _Band_Name, _Based_City, _Email, _Password, _Bandcode, _Phone, _Genre;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9]{10,13}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_register);
        _Band_Name = (EditText) findViewById(R.id.etxtbandname);
        _Genre = (EditText) findViewById(R.id.etxtgenreband);

        _Based_City = (EditText) findViewById(R.id.etxtbasedcity);
        _Email = (EditText) findViewById(R.id.etxtemailband);
        _Password = (EditText) findViewById(R.id.etxtpasswordband);
        _Bandcode = (EditText) findViewById(R.id.etxtbandcode);
        _Phone = (EditText) findViewById(R.id.etxtphnno);

        buttonSignupband = (Button) findViewById(R.id.btnsignupband);
        buttonSignupband.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;

                final String Band_Name = _Band_Name.getText().toString();
                final String Genre = _Genre.getText().toString();
                final String Based_City = _Based_City.getText().toString();
                final String Email = _Email.getText().toString();
                final String Password = _Password.getText().toString();
                final String Bandcode = _Bandcode.getText().toString();
                final String Phone = _Phone.getText().toString();



                //validating the fields

                if (Band_Name.isEmpty()) {
                    _Band_Name.setError("Please fill this field");
                    error = true;
                }
                if (Genre.isEmpty()) {
                    _Genre.setError("Please fill this field");
                    error = true;
                }
                if (Based_City.isEmpty()) {
                    _Based_City.setError("Please fill this field");
                    error = true;
                }
                if (Phone.isEmpty()) {
                    _Phone.setError("Please fill this field");
                    error = true;
                }
                if (!PHONE_PATTERN.matcher(Phone).matches()) {
                    _Phone.setError("Please enter valid phone number");
                    error = true;
                }
                if (Email.isEmpty()) {
                    _Email.setError("Please fill this field");
                    error = true;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    _Email.setError("Please enter a valid email address");
                    error = true;
                }
                if (Password.isEmpty()) {
                    _Password.setError("Please fill this field");
                    error = true;
                }
                if (!PASSWORD_PATTERN.matcher(Password).matches()) {
                    _Password.setError("Password too weak. Must be 6 character long");
                    error = true;
                }
                if (Bandcode.isEmpty()) {
                    _Bandcode.setError("Please fill this field");
                    error = true;
                }
                if (!PASSWORD_PATTERN.matcher(Bandcode).matches()) {
                    _Bandcode.setError("Bandcode too weak. Must be 6 character long");
                    error = true;
                }

                if (error == false) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {   final ProgressDialog progressDialog = new ProgressDialog(Band_register.this);
                            progressDialog.setMessage("Loading.....");
                            progressDialog.show();

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Band_register.this, "Band Registered", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Band_register.this, login_bandmember.class);
                                    Band_register.this.startActivity(intent);

                                    finish();

                                } else {
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Band_register.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };


                    CreateBandRegisterRequest createBandRegisterRequest = new CreateBandRegisterRequest(Band_Name, Genre, Based_City, Email, Password, Bandcode, Phone, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Band_register.this);
                    queue.add(createBandRegisterRequest);
                }

        }
        });

    }


}