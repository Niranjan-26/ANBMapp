package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BandProfileEditDash extends AppCompatActivity {
    TextView _bandIdinupdate;
    EditText _etxtbandGenre, _etxtBandBasedCity, _etxtbandphone, _etxtbandemail;
    Button _btnupdatebanddetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_profile_edit_dash);
        _etxtbandGenre = (EditText) findViewById(R.id.etxtbandGenre);
        _etxtBandBasedCity = (EditText) findViewById(R.id.etxtBandBasedCity);
        _etxtbandphone = (EditText) findViewById(R.id.etxtbandphone);
        _etxtbandemail = (EditText) findViewById(R.id.etxtbandemail);
        _bandIdinupdate = (TextView) findViewById(R.id.bandIdinupdate);
        _btnupdatebanddetails = (Button) findViewById(R.id.btnupdatebanddetails);


        Intent intent = getIntent();
        String newuseridineditpf = intent.getStringExtra("Band_Id");

        _bandIdinupdate.setText(newuseridineditpf);





        _btnupdatebanddetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();
            }
        });

        getDataa();



    }

    //updatinguser details

    private static final String UPDATE_URL = "https://njdrums.000webhostapp.com/UpdateBandDetails.php?Band_Id=";
    private void UpdateUser() {

        String Genre = _etxtbandGenre.getText().toString();
        String Based_City = _etxtBandBasedCity.getText().toString();
        String Email = _etxtbandemail.getText().toString();
        String Phone = _etxtbandphone.getText().toString();
        String Band_Id = _bandIdinupdate.getText().toString();

        registers(Genre,Based_City,Email,Phone,Band_Id);
    }


    private void registers(String Genre, String Based_City, String Email, String Phone, String Band_Id) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            UpdateUserClass uuc = new UpdateUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(BandProfileEditDash.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast t = Toast.makeText(BandProfileEditDash.this, "Updated",
                        Toast.LENGTH_SHORT);
                t.show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("Genre", params[0]);
                data.put("Based_City", params[1]);
                data.put("Email", params[2]);
                data.put("Phone", params[3]);
                data.put("Band_Id", params[4]);

                String result = uuc.sendPostRequest(UPDATE_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(Genre,Based_City,Email,Phone,Band_Id);
    }


    //getiing data of Bands

    private void getDataa() {
        String url = config99ProfiledetailsBand.DATA_URL + _bandIdinupdate.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BandProfileEditDash.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON (String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(config99ProfiledetailsBand.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String Genre = jo.getString(config99ProfiledetailsBand.KEY_BAND_GENRE);
                String Based_City = jo.getString(config99ProfiledetailsBand.KEY_BAND_BASED_CITY);
                String Email = jo.getString(config99ProfiledetailsBand.KEY_BAND_EMAIL);
                String Phone = jo.getString(config99ProfiledetailsBand.KEY_BAND_PHONE);

                _etxtbandGenre.setText(Genre);
                _etxtBandBasedCity.setText(Based_City);
                _etxtbandphone.setText(Email);
                _etxtbandemail.setText(Phone);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
