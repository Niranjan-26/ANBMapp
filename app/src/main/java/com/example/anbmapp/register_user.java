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


public class register_user extends AppCompatActivity {
    Button buttonSignup;
    EditText _etxtfname, _etxtuname, _etxtadd, _etxtphone, _etxtemail, _etxtpassword;
    TextView _etxtGender;
    RadioGroup radioGroup;
    RadioButton rb;
    int radioId;


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
        setContentView(R.layout.activity_register_user);
        _etxtfname = (EditText) findViewById(R.id.etxtfname);
        _etxtuname = (EditText) findViewById(R.id.etxtuname);
        _etxtGender = (TextView) findViewById(R.id.etxtgender);
        _etxtadd = (EditText) findViewById(R.id.etxtadd);
        _etxtphone = (EditText) findViewById(R.id.etxtphone);
        _etxtemail = (EditText) findViewById(R.id.etxtemail);
        _etxtpassword = (EditText) findViewById(R.id.etxtpassword);
        buttonSignup = (Button) findViewById(R.id.btnsignup);
        radioGroup = (RadioGroup) findViewById(R.id.rgGenre);









        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //radiobuttons
                radioId = radioGroup.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(radioId);
                _etxtGender.setText(rb.getText().toString());


                boolean error = false;

                final String Full_Name = _etxtfname.getText().toString();
                final String Username = _etxtuname.getText().toString();
                final String Gender = _etxtGender.getText().toString();
                final String Address = _etxtadd.getText().toString();
                final String Phone = _etxtphone.getText().toString();
                final String Email = _etxtemail.getText().toString();
                final String Password = _etxtpassword.getText().toString();

                //validating the fields

                if (Full_Name.isEmpty()) {
                    _etxtfname.setError("Please fill this field");
                    error = true;
                }
                if (!Full_Name.matches("[a-zA-Z\\s]+")) {
                    _etxtfname.setError("Please enter your name properly");
                    error = true;
                }
                if (Username.isEmpty()) {
                    _etxtuname.setError("Please fill this field");
                    error = true;
                }
                if (Gender.isEmpty()) {
                    _etxtGender.setError("Please fill this field");
                    error = true;
                }
                if (Address.isEmpty()) {
                    _etxtadd.setError("Please fill this field");
                    error = true;
                }
                if (Phone.isEmpty()) {
                    _etxtphone.setError("Please fill this field");
                    error = true;
                }
                if (!PHONE_PATTERN.matcher(Phone).matches()) {
                    _etxtphone.setError("Please enter valid phone number");
                    error = true;
                }
                if (Email.isEmpty()) {
                    _etxtemail.setError("Please fill this field");
                    error = true;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    _etxtemail.setError("Please enter a valid email address");
                    error = true;
                }
                if (Password.isEmpty()) {
                    _etxtpassword.setError("Please fill this field");
                    error = true;
                }
                if (!PASSWORD_PATTERN.matcher(Password).matches()) {
                    _etxtpassword.setError("Password too weak. Must be 6 character long");
                    error = true;
                }


                if (error == false) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(register_user.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(register_user.this, login_user.class);
                                    register_user.this.startActivity(intent);
                                    finish();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(register_user.this);
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

                    RegisterRequest registerRequest = new RegisterRequest(Full_Name, Username, Gender, Address, Phone, Email, Password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(register_user.this);
                    queue.add(registerRequest);

                }
            }
        });

    }

    public void checkButton(View v){
        //radiobuttons
        radioId = radioGroup.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radioId);
    }

}