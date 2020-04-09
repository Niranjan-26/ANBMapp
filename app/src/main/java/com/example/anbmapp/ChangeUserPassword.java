package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ChangeUserPassword extends AppCompatActivity {
    EditText _etxtchangeuserpw;
    TextView _tv_EmailofUser_Changepw;
    Button _btnchangeuserpw;
    ProgressBar _progressdialoguechangeuserpw;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);

        _etxtchangeuserpw = (EditText)findViewById(R.id.etxtchangeuserpw);
        _tv_EmailofUser_Changepw = (TextView) findViewById(R.id.tv_EmailofUser_Changepw);
        _btnchangeuserpw = (Button) findViewById(R.id.btnchangeuserpw);
        _progressdialoguechangeuserpw = (ProgressBar) findViewById(R.id.progressdialoguechangeuserpw);

        _etxtchangeuserpw.addTextChangedListener(passwordTextWatcher);


        Intent intent = getIntent();
        String userfpwEmail = intent.getStringExtra("Email");
        _tv_EmailofUser_Changepw.setText(userfpwEmail);
        _btnchangeuserpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();

            }
        });

    }
    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pass = _etxtchangeuserpw.getText().toString().trim();
            if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                _etxtchangeuserpw.setError("Password too weak. Must be 6 character long");
                _btnchangeuserpw.setEnabled(false);

            } else {
                _etxtchangeuserpw.setError(null);
                _btnchangeuserpw.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void changePassword() {
        final String new_password;
        boolean error = false;
        String url = "https://njdrums.000webhostapp.com/ChangeUserPassword.php";
        new_password = _etxtchangeuserpw.getText().toString().trim();

        if (new_password.isEmpty()) {
            _etxtchangeuserpw.setError("Please fill this field");
            error = true;
        }
        if (!PASSWORD_PATTERN.matcher(new_password).matches()) {
            _etxtchangeuserpw.setError("Password too weak. Must be 6 character long");
            error = true;
        }
        if (error == false){
            _progressdialoguechangeuserpw.setVisibility(View.VISIBLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(ChangeUserPassword.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("success")){
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Password Successfully Updated. Please log in to your account", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), login_user.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (response.trim().equals("error")) {
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Sorry! Email not found", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.trim().equals("dbError")){
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"The server could not be found. Please try again after some time!!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Parsing error! Please try again after some time!!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NoConnectionError) {
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        _progressdialoguechangeuserpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Connection TimeOut! Please check your internet connection.",Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", _tv_EmailofUser_Changepw.getText().toString());
                    params.put("password", new_password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}
