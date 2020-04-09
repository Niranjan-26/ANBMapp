package com.example.anbmapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoardBandProfile extends AppCompatActivity {

    TextView _txtBandiddash, _txtbanddid, _txtbanddurl;
    ImageView _bandsprofilepic;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;



//    private String uploadUrl = "http://njdrums.000webhostapp.com/fetchBandprofilePic.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_band_profile);

        final TextView _txtBandNameDash = (TextView) findViewById(R.id.txtBandNameDash);
        final TextView _txtEmailDash = (TextView) findViewById(R.id.txtEmailDash);

        _txtBandiddash = (TextView) findViewById(R.id.txtBandiddash);

        _txtbanddid = (TextView) findViewById(R.id.txtbanddid);
        _txtbanddurl = (TextView) findViewById(R.id.txtbanddurl);
        _bandsprofilepic = (ImageView) findViewById(R.id.bandsprofilepic);

        final ImageView _txtvieweditprofilepic = (ImageView) findViewById(R.id.txtvieweditprofilepic);


        Intent intent = getIntent();
        String band_id = intent.getStringExtra("Band_Id");

        String band_name = intent.getStringExtra("Band_Name");
        String email = intent.getStringExtra("Email");

        _txtBandiddash.setText(band_id);
        _txtBandNameDash.setText(band_name);
        _txtEmailDash.setText(email);



        _txtvieweditprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String band_id = _txtBandiddash.getText().toString();
                Intent intent = new Intent(DashBoardBandProfile.this, UploadBandProfilePic.class);

                intent.putExtra("Band_Id", band_id);
                DashBoardBandProfile.this.startActivity(intent);

            }
        });
        getData();


    }



    public void goTOImageUpload(View view){

        String band_id = _txtBandiddash.getText().toString();
        Intent intent = new Intent(DashBoardBandProfile.this, UploadPics.class);
//        startActivity(new Intent(getApplicationContext(),UploadPics.class));
        intent.putExtra("Band_Id", band_id);
        DashBoardBandProfile.this.startActivity(intent);


    }

    public void goToStatus(View view) {
        String band_id2 = _txtBandiddash.getText().toString();
        Intent intent = new Intent(DashBoardBandProfile.this, uploadBandStatus.class);
        intent.putExtra("Band_Id2", band_id2);
        DashBoardBandProfile.this.startActivity(intent);

    }






    private void getData() {
        String url = config77ProfilePic.DATA_URL + _txtBandiddash.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DashBoardBandProfile.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(config77ProfilePic.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String Band_Id = jo.getString(config77ProfilePic.KEY_BAND_ID);
                String url_band_pf_pic = jo.getString(config77ProfilePic.KEY_URL_BAND_PF_PIC);



                _txtbanddid.setText(Band_Id);
                _txtbanddurl.setText(url_band_pf_pic);

                String pfpicediturl = _txtbanddurl.getText().toString();
                Picasso.get().load(pfpicediturl).into(_bandsprofilepic);





            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
//        ListAdapter adapter = new SimpleAdapter(
//                this, list, R.layout.activity_mylist,
//                new String[]{config77ProfilePic.KEY_BAND_NAME, config77ProfilePic.KEY_GENRE, config77ProfilePic.KEY_BASED_CITY, config77ProfilePic.KEY_EMAIL},
//                new int[]{R.id.tvBandName, R.id.tvBandGenre, R.id.tvBandBasedCity, R.id.tvBandEmail});
//
//        ivListView.setAdapter(adapter);

    }


    public void gotonoticeboard(View view) {
        String band_id = _txtBandiddash.getText().toString();
        Intent intent = new Intent(DashBoardBandProfile.this, BandNoticeBoard.class);
        intent.putExtra("Band_Id", band_id);
        DashBoardBandProfile.this.startActivity(intent);
    }

    public void gotoBandManagerGallery(View view) {
        String band_id = _txtBandiddash.getText().toString();
        Intent intent = new Intent(DashBoardBandProfile.this, BandManagerGallery.class);
        intent.putExtra("Band_Id", band_id);
        DashBoardBandProfile.this.startActivity(intent);

    }

    public void gotoBandProfileEdit(View view) {
        String band_id = _txtBandiddash.getText().toString();
        Intent intent = new Intent(DashBoardBandProfile.this, BandProfileEditDash.class);
        intent.putExtra("Band_Id", band_id);
        DashBoardBandProfile.this.startActivity(intent);

    }

    public void logoutbManager(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardBandProfile.this);

        builder.setMessage("Are you sure to logout?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences2 = getSharedPreferences("loginref2", MODE_PRIVATE);
                        editor2 = sharedPreferences2.edit();
                        editor2.remove("Band_Name");
                        editor2.remove("Password");
                        editor2.remove("Bandcode");
                        editor2.putBoolean("state", false);
                        editor2.commit();
                        Intent intent = new Intent(DashBoardBandProfile.this, login_bandmember.class);
                        DashBoardBandProfile.this.startActivity(intent);
                        Toast.makeText(DashBoardBandProfile.this, "Loged Out", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                })
                .setNegativeButton("Cancel",null);
        AlertDialog alert = builder.create();
        alert.show();





    }
}
