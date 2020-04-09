package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FogotPasswordUser extends AppCompatActivity {
    EditText _etxtforgotuserpwemail;
//    TextView  _tv_EmailofUser;
    Button _btnuserforgotpw;
    ProgressBar _progressdialogueuserfpw;
//    SharedPreferences sharedPreferences3;
//    SharedPreferences.Editor editor3;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password_user);

        _etxtforgotuserpwemail = (EditText) findViewById(R.id.etxtforgotuserpwemail);
//        _tv_EmailofUser = (TextView) findViewById(R.id.tv_EmailofUser);
        _btnuserforgotpw = (Button)findViewById(R.id.btnuserforgotpw);
        _progressdialogueuserfpw = (ProgressBar) findViewById(R.id.progressdialogueuserfpw);

        _etxtforgotuserpwemail.addTextChangedListener(emailTextWatcher);
        _btnuserforgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();

            }
        });

//        Intent intent = getIntent();
//        String userfpwEmail = intent.getStringExtra("Email");
//        _tv_EmailofUser.setText(userfpwEmail);


    }

    private TextWatcher emailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = _etxtforgotuserpwemail.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _etxtforgotuserpwemail.setError("Please enter a valid email address");
                _btnuserforgotpw.setEnabled(false);
            } else {
                _etxtforgotuserpwemail.setError(null);
                _btnuserforgotpw.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    public void sendCode() {
        _progressdialogueuserfpw.setVisibility(View.VISIBLE);
        final String email;
        boolean error = false;
        String url = "https://njdrums.000webhostapp.com/SendUserCode.php";
        email = _etxtforgotuserpwemail.getText().toString().trim();

        if (email.isEmpty()) {
            _etxtforgotuserpwemail.setError("Please fill this field");
            error = true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _etxtforgotuserpwemail.setError("Please enter a valid email address");
            error = true;
        }
        if (error == false){
            _etxtforgotuserpwemail.setVisibility(View.VISIBLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(FogotPasswordUser.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("success")){
                        _progressdialogueuserfpw.setVisibility(View.GONE);
//                        editorPreferences.putString("email", email);
//                        editorPreferences.apply();
                        Toast.makeText(getApplicationContext(), "Code Successfully Sent. Please Check Your Email.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FogotPasswordUser.this, ForgotPwUserEnterVCode.class);
                        intent.putExtra("Email", email);
                        FogotPasswordUser.this.startActivity(intent);
                        finish();
                    }
                    else if (response.trim().equals("error")) {
                        _progressdialogueuserfpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Sorry! Email not found", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.trim().equals("dbError")){
                        _progressdialogueuserfpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        _progressdialogueuserfpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        _progressdialogueuserfpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"The server could not be found. Please try again after some time!!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        _progressdialogueuserfpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        _progressdialogueuserfpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Parsing error! Please try again after some time!!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NoConnectionError) {
                        _progressdialogueuserfpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        _progressdialogueuserfpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Connection TimeOut! Please check your internet connection.",Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }


}
