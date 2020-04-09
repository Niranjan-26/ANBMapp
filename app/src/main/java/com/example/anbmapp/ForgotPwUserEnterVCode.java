package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class ForgotPwUserEnterVCode extends AppCompatActivity {
    EditText _etxtVerifyuserpwemailcode;
    TextView _tv_EmailofUser;
    Button _btnuserVerifypw;
    ProgressBar _progressdialogueuserVpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pw_user_enter_vcode);
        _etxtVerifyuserpwemailcode = (EditText) findViewById(R.id.etxtVerifyuserpwemail);
        _tv_EmailofUser = (TextView) findViewById(R.id.tv_EmailofUser);
        _btnuserVerifypw = (Button) findViewById(R.id.btnuserVerifypw);
        _progressdialogueuserVpw = (ProgressBar) findViewById(R.id.progressdialogueuserVpw);

        Intent intent = getIntent();
        String userfpwEmail = intent.getStringExtra("Email");
        _tv_EmailofUser.setText(userfpwEmail);

        _btnuserVerifypw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    public void verifyUser() {
        boolean error = false;
        String url = "https://njdrums.000webhostapp.com/VerifyUserCode.php";
        final String code = _etxtVerifyuserpwemailcode.getText().toString().trim();
//        String email = sharedPreferences.getString("email", null);
        if (code.isEmpty()) {
            _etxtVerifyuserpwemailcode.setError("Please enter the code");
            error = true;
        }
        if (error == false) {
            _progressdialogueuserVpw.setVisibility(View.VISIBLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(ForgotPwUserEnterVCode.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("success")) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Successfully Verified.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPwUserEnterVCode.this, ChangeUserPassword.class);
                        intent.putExtra("Email", _tv_EmailofUser.getText().toString());
                        ForgotPwUserEnterVCode.this.startActivity(intent);
                        finish();
                    } else if (response.trim().equals("error")) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        _etxtVerifyuserpwemailcode.setError("Incorrect Code");
                    } else if (response.trim().equals("dbError")) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NoConnectionError) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        _progressdialogueuserVpw.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("code", code);
                    params.put("email", _tv_EmailofUser.getText().toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}
