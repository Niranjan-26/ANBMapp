package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserProfile extends AppCompatActivity {
    TextView _useridineditpf,_urltextpfpic;
    CircleImageView _civuserpfupdate;
   EditText _etxtusernameedit,_etxtfullnameedit,_etxtgenderedit,_etxtaddressedit,_etxtphoneedit,_etxtemailedit;
    ImageView _useredit;
    Button _btnupdate;
    // Progress Dialog

    private static final String UPDATE_URL = "https://njdrums.000webhostapp.com/updateUserDetails.php?Id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        _useridineditpf = (TextView)findViewById(R.id.useridineditpf);
        _etxtusernameedit = (EditText)findViewById(R.id.etxtusernameedit);
        _etxtfullnameedit = (EditText)findViewById(R.id.etxtfullnameedit);
        _etxtgenderedit = (EditText)findViewById(R.id.etxtgenderedit);
        _etxtaddressedit = (EditText)findViewById(R.id.etxtaddressedit);
        _etxtphoneedit = (EditText)findViewById(R.id.etxtphoneedit);
        _etxtemailedit = (EditText)findViewById(R.id.etxtemailedit);
        _useredit = (ImageView) findViewById(R.id.useredit);
        _urltextpfpic = (TextView) findViewById(R.id.urltextpfpic);
        _civuserpfupdate = (CircleImageView)findViewById(R.id.civuserpfupdate);
        _btnupdate = (Button) findViewById(R.id.btnupdate);
        _btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();



            }
        });
        Intent intent = getIntent();
        String newuseridineditpf = intent.getStringExtra("new_useridinprofile");

        _useridineditpf.setText(newuseridineditpf);
        _useredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id2 = _useridineditpf.getText().toString();
                Intent intent = new Intent(EditUserProfile.this, uploadingUserpfpic.class);
                intent.putExtra("user_id2", user_id2);
                EditUserProfile.this.startActivity(intent);
            }
        });
        getDataa();
        getuserpfpicurl();
    }
    //updatinguser details
    private void UpdateUser() {

        String Full_Name = _etxtfullnameedit.getText().toString();
        String Username = _etxtusernameedit.getText().toString();
        String Gender = _etxtgenderedit.getText().toString();
        String Address = _etxtaddressedit.getText().toString();
        String Phone = _etxtphoneedit.getText().toString();
        String Email = _etxtemailedit.getText().toString();
        String Id = _useridineditpf.getText().toString();




        registers(Id,Full_Name,Username,Gender,Address,Phone,Email);
    }


    private void registers(String Id, String Full_Name, String Username, String Gender, String Address, String Phone, String Email) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            UpdateUserClass uuc = new UpdateUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditUserProfile.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast t = Toast.makeText(EditUserProfile.this, "Updated",
                        Toast.LENGTH_SHORT);
                t.show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(EditUserProfile.this);
//                                builder.setMessage("Updated")
//                                        .setPositiveButton("ok",null)
//                                        .create()
//                                        .show();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("Id", params[0]);
                data.put("Full_Name", params[1]);
                data.put("Username", params[2]);
                data.put("Gender", params[3]);
                data.put("Address", params[4]);
                data.put("Phone", params[5]);
                data.put("Email", params[6]);

                String result = uuc.sendPostRequest(UPDATE_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(Id,Full_Name,Username,Gender,Address,Phone,Email);
    }




//getiing data of user

        private void getDataa () {
            String url = config99ProfiledetailsUser.DATA_URL + _useridineditpf.getText().toString().trim();


            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditUserProfile.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        private void showJSON (String response){
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config99ProfiledetailsUser.JSON_ARRAY);

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String Id = jo.getString(config99ProfiledetailsUser.KEY_USER_ID);
                    String Full_Name = jo.getString(config99ProfiledetailsUser.KEY_Full_NAME);
                    String Username = jo.getString(config99ProfiledetailsUser.KEY_USER_NAME);
                    String Gender = jo.getString(config99ProfiledetailsUser.KEY_GENDER);
                    String Address = jo.getString(config99ProfiledetailsUser.KEY_ADDRESS);
                    String Phone = jo.getString(config99ProfiledetailsUser.KEY_PHONE);
                    String Email = jo.getString(config99ProfiledetailsUser.KEY_EMAIL);
//                String url_user_pf_pic = jo.getString(config99ProfiledetailsUser.KEY_URL_USER_PF_PIC);


                    _etxtusernameedit.setText(Username);
                    _etxtfullnameedit.setText(Full_Name);
                    _etxtgenderedit.setText(Gender);
                    _etxtaddressedit.setText(Address);
                    _etxtphoneedit.setText(Phone);
                    _etxtemailedit.setText(Email);
//                _urltextpfpic.setText(url_user_pf_pic);

//                _txtbanddid.setText(Band_Id);
//                _txtbanddurl.setText(url_band_pf_pic);
//
//                String pfpicuserurl = _urltextpfpic.getText().toString();
//                Picasso.get().load(pfpicuserurl).into(_civuserpfupdate);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

//getting user pf pic url
        private void getuserpfpicurl () {
            String url = config88ViewUserPf.DATA_URL + _useridineditpf.getText().toString().trim();


            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    showJSONpfurl(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditUserProfile.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        private void showJSONpfurl (String response){
    //        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config88ViewUserPf.JSON_ARRAY);

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);

                    String url_user_pf_pic = jo.getString(config88ViewUserPf.KEY_URL_USER_PF_PIC);



                    _urltextpfpic.setText(url_user_pf_pic);



                    String pfpicuserurl = _urltextpfpic.getText().toString();
                    Picasso.get().load(pfpicuserurl).into(_civuserpfupdate);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }






}
