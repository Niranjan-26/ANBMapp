package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginBandMembers extends AppCompatActivity {

    EditText _bandnamemember,_bandcodemember;
    Button _btnmemberlogin;
    CheckBox _chkboxloginbmembers;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String Band_Name,Bandcode,Bandcode2;
    Boolean check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_band_members);

        _bandnamemember = (EditText)findViewById(R.id.bandnamemember);
        _bandcodemember = (EditText)findViewById(R.id.bandcodemember);
        _btnmemberlogin = (Button) findViewById(R.id.btnmemberlogin);

        _chkboxloginbmembers = (CheckBox) findViewById(R.id.chkboxloginbmembers);

        //using shredpreferences
        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        _bandnamemember.setText(sharedPreferences.getString("Band_Name",null));
        _bandcodemember.setText(sharedPreferences.getString("Bandcode",null));

        check = sharedPreferences.getBoolean("state",false);
        if(check){
            _chkboxloginbmembers.setChecked(true);
        }




        _btnmemberlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(LoginBandMembers.this);
                pd.setMessage("loading wait");
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setIndeterminate(true);
                pd.setProgress(0);
                pd.show();
                Band_Name = _bandnamemember.getText().toString();
                Bandcode = _bandcodemember.getText().toString();


                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    String Band_Id = jsonResponse.getString("Band_Id");
                                    String Band_Name = jsonResponse.getString("Band_Name");
                                    String Email = jsonResponse.getString("Email");
                                    Bandcode2 = jsonResponse.getString("Bandcode");

                                    //condition for checkbox to use shredpreferences

                                    if(_chkboxloginbmembers.isChecked()) {
                                        editor.putBoolean("state", true);
                                        editor.putString("Band_Name", Band_Name);
                                        editor.putString("Bandcode", Bandcode2);
                                        editor.commit();
                                    }else {
                                        editor.remove("Band_Name");
                                        editor.remove("Bandcode");
                                        editor.putBoolean("state", false);
                                        editor.commit();
                                    }
                                        Intent intent = new Intent(LoginBandMembers.this, BandMembersProfile.class);
                                        intent.putExtra("Band_Id", Band_Id);
                                        intent.putExtra("Band_Name", Band_Name);
                                        intent.putExtra("Email", Email);
                                        LoginBandMembers.this.startActivity(intent);
                                        finish();



                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginBandMembers.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pd.dismiss();


                    }
                };

                BandMemberLoginRequest bandMemberLoginRequest = new BandMemberLoginRequest(Band_Name, Bandcode, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginBandMembers.this);
                queue.add(bandMemberLoginRequest);
            }
        });


    }
}
