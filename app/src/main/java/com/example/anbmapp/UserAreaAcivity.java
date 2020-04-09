package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
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
import io.paperdb.Paper;

import static com.example.anbmapp.R.drawable.fav_unchecked;
import static com.example.anbmapp.config88ViewBandPf.JSON_ARRAY;

public class UserAreaAcivity extends AppCompatActivity {
    TextView bandname,bandid,bandgenre,bandbasedcity,bandemail,_tv4phn,bandurl,_usridinuserareaforband, _is_Fav_fetched_From_server, _is_Fav_band1_id, _band_phone;
    CircleImageView bandpic;
    bandsdetails details;
    CheckBox _Favourtiesbtn;
    ArrayList<String>favBandDetailsList;
//    ImageView _Favourtiesbtn;
    String isFavTrue = "false";
    DatabaseHelper myDb;
    String uid,bid2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area_acivity);

        //calling the constructor of databasehelper
        myDb = new DatabaseHelper(this);

//        Bundle bundle = getIntent().getBundleExtra("bundle");
//
//        details = (bandsdetails) bundle.getSerializable("banddetails");

        // inting paper db
//        Paper.init(getApplicationContext());

//        favBandDetailsList = Paper.book().read("favBandDetail", new ArrayList<String>());

        bandname = (TextView)findViewById(R.id.bands_name);
        bandid = (TextView)findViewById(R.id.bands_new_id);
        bandgenre = (TextView)findViewById(R.id.bands_genre);
        bandbasedcity = (TextView)findViewById(R.id.bands_based_city);
        bandemail = (TextView)findViewById(R.id.bands_email);
        bandurl = (TextView)findViewById(R.id.bands_new_url);
        _tv4phn = (TextView)findViewById(R.id.tv4phn);
//        _band_phone = (TextView)findViewById(R.id.tv4phn);
        _Favourtiesbtn = (CheckBox) findViewById(R.id.Favourtiesbtn);
        _usridinuserareaforband = (TextView)findViewById(R.id.usridinuserareaforband);
        _is_Fav_fetched_From_server = (TextView)findViewById(R.id.is_Fav_fetched_From_server);
        _is_Fav_band1_id = (TextView)findViewById(R.id.is_Fav_band1_id);
        bandpic = (CircleImageView)findViewById(R.id.civ2);

        Intent intent = getIntent();
        String bname = intent.getStringExtra("fav_Band_Name");
        final String bid = intent.getStringExtra("fav_band_id");
        String bgenre = intent.getStringExtra("fav_Genre");
        String bbasedcity = intent.getStringExtra("fav_Based_City");
        String bemail = intent.getStringExtra("fav_Email");
        String user_id = intent.getStringExtra("user_id");
        String bphone = intent.getStringExtra("fav_band_phone");



        bandname.setText(bname);
        bandid.setText(bid);
        bandgenre.setText(bgenre);
        bandbasedcity.setText(bbasedcity);
        bandemail.setText(bemail);
        _usridinuserareaforband.setText(user_id);
        _tv4phn.setText(bphone);
//        _band_phone.setText(bphone);

        _Favourtiesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllFavStatus(uid, bid2,"true");

                if(_Favourtiesbtn.isChecked() || res.equals("true")){


                    //delete the fav
//                    DeleteUserFav();
                    Integer deletedRows = myDb.deleteData(bandid.getText().toString());
                    if(deletedRows > 0){
                        Toast.makeText(UserAreaAcivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UserAreaAcivity.this, "press again", Toast.LENGTH_SHORT).show();

                    }
                    _Favourtiesbtn.setButtonDrawable(fav_unchecked);














                }else if (!_Favourtiesbtn.isChecked() || !res.equals("true"))
                    {

                        //add to fav

                        String is_fav = "true";

                        boolean isInserted = myDb.insertData(bandid.getText().toString() ,
                                _usridinuserareaforband.getText().toString() ,
                                is_fav ,
                                bandname.getText().toString() ,
                                _tv4phn.getText().toString());
                        if (isInserted == true){
                            Toast.makeText(UserAreaAcivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                        }else{

                            Toast.makeText(UserAreaAcivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        _Favourtiesbtn.setButtonDrawable(R.drawable.fav_checked);








                    }
            }
        });



        getDatapfpicurl();
        gettingFavStatus();


    }



public void gettingFavStatus(){

    //passing the filter
    uid = _usridinuserareaforband.getText().toString();
    bid2 = bandid.getText().toString();
    Cursor res = myDb.getAllFavStatus(uid,bid2,"true");
    if (res.getCount() == 0) {
//        Toast.makeText(this, "No Favourites", Toast.LENGTH_SHORT).show();

    }else{
        while (res.moveToNext()){
            isFavTrue = res.getString(3);

                _is_Fav_fetched_From_server.setText(isFavTrue);


                String isfavsts_new = _is_Fav_fetched_From_server.getText().toString().trim();
                if (isfavsts_new.equals("true")) {
                    _Favourtiesbtn.setButtonDrawable(R.drawable.fav_checked);
                }

        }
    }
}










//getting pfpicurl
    private void getDatapfpicurl() {
        String url = config88ViewBandPf.DATA_URL + bandid.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserAreaAcivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(config88ViewBandPf.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String url_band_pf_pic = jo.getString(config88ViewBandPf.KEY_URL_BAND_PF_PIC);



                bandurl.setText(url_band_pf_pic);

                String pfpicediturl = bandurl.getText().toString();
                Picasso.get().load(pfpicediturl).into(bandpic);





            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }






    //getting favstatus


//    private void getDatafavstatus() {
//        String url2 = config88ViewFavStatus.DATA_URL + bandid.getText().toString().trim() + "&user_id=" + _usridinuserareaforband.getText().toString().trim();
//
//
//
//        StringRequest stringRequest = new StringRequest(url2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                showJSON2(response);
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(UserAreaAcivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
//
//    private void showJSON2(String response) {
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray(config88ViewFavStatus.JSON_ARRAY);
//
//            for (int i = 0; i < result.length(); i++) {
//                JSONObject jo = result.getJSONObject(i);
////                String Band_Id = jo.getString(config88ViewFavStatus.KEY_BAND_ID);
//                String is_Fav = jo.getString(config88ViewFavStatus.KEY_IS_FAV);
//
//
//
////                _is_Fav_band1_id.setText(Band_Id);
//                _is_Fav_fetched_From_server.setText(is_Fav);
//
//
//                String isfavsts_new = _is_Fav_fetched_From_server.getText().toString().trim();
//
//                if(isfavsts_new.equals("true")){
//                    _Favourtiesbtn.setImageResource(R.drawable.fav_checked);
//                }
//
//
//
//
//
//
//
//
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
    //removing from favourties
//    private static final String DELETE_URL = "http://njdrums.000webhostapp.com/deleteUserFav.php?Band_ID=&user_id";
//    private void DeleteUserFav() {
//
//        final String Band_Id = bandid.getText().toString();
//        final String user_id = _usridinuserareaforband.getText().toString();
//
//
//
//        register(Band_Id,user_id);
//    }
//
//
//    private void register(String Band_Id, String user_id) {
//        class RegisterUsers extends AsyncTask<String, Void, String> {
//
//            UpdateUserClass ruc = new UpdateUserClass();
//
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                Toast.makeText(UserAreaAcivity.this, "removed from favourites", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                HashMap<String, String> data = new HashMap<String, String>();
//
//
//                data.put("Band_Id", params[0]);
//                data.put("user_id", params[1]);
//
//                String result = ruc.sendPostRequest(DELETE_URL, data);
//
//                return result;
//            }
//        }
//
//        RegisterUsers ru = new RegisterUsers();
//        ru.execute(Band_Id, user_id);
//    }



//    public void gotofavoritesdetails(View view) {
//        startActivity(new Intent(getApplicationContext(),showFavbandlist.class));
//    }



}