package com.example.anbmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BandMembersProfile extends AppCompatActivity implements  RecyclerViewAdapterBandNoticeBoard.OnItemClickListener{
    TextView _txtbandidbmembers,_txtBandNamebmembrs, _txtbanddurlbmembers;
    RecyclerView _recyclerviewBmembers;
    ImageView _bandsprofilepicbmembers;
    RecyclerView.LayoutManager layoutManagerNotice;
    RecyclerViewAdapterBandNoticeBoard adapterNotice;
    List<BandsNoticeBoardDetails> klistviews;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_members_profile);

        _txtbandidbmembers = (TextView)findViewById(R.id.txtbandidbmembers);
        _txtBandNamebmembrs = (TextView)findViewById(R.id.txtBandNamebmembrs);
        _txtbanddurlbmembers = (TextView)findViewById(R.id.txtbanddurlbmembers);
        _bandsprofilepicbmembers = (ImageView) findViewById(R.id.bandsprofilepicbmembers);
        _recyclerviewBmembers = (RecyclerView) findViewById(R.id.recyclerviewBmembers);
        Toolbar toolbar = findViewById(R.id.toolbarbmembers);
        setSupportActionBar(toolbar);




        layoutManagerNotice = new LinearLayoutManager(getApplicationContext());
        _recyclerviewBmembers.setHasFixedSize(true);
        _recyclerviewBmembers.setLayoutManager(layoutManagerNotice);
        klistviews = new ArrayList<>();

        //getting band id
        Intent intent = getIntent();
        String newBandIdNotice = intent.getStringExtra("Band_Id");
        String newNameNotice = intent.getStringExtra("Band_Name");
        _txtbandidbmembers.setText(newBandIdNotice);
        _txtBandNamebmembrs.setText(newNameNotice);


        getDataurlpfpic();
        loadRecyclerViewDataNotice();
    }

    private void loadRecyclerViewDataNotice() {
        String url="https://njdrums.000webhostapp.com/fetchingrecyclerViewBandNoticeMembers.php?Band_Id="+_txtbandidbmembers.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        BandsNoticeBoardDetails details = new BandsNoticeBoardDetails(
                                o.getString("Date"),
                                o.getString("Notice_Title"),
                                o.getString("Start_Time"),
                                o.getString("End_Time"),
                                o.getString("Venue"),
                                o.getString("Description")



                        );
                        klistviews.add(details);
                        adapterNotice = new RecyclerViewAdapterBandNoticeBoard(getApplicationContext(), klistviews);
                        _recyclerviewBmembers.setAdapter(adapterNotice);
                        adapterNotice.SetOnItemClickListener(BandMembersProfile.this);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    //gettingurl of the band

    private void getDataurlpfpic() {
        String url = config77ProfilePic.DATA_URL + _txtbandidbmembers.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BandMembersProfile.this, error.getMessage(), Toast.LENGTH_LONG).show();
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




                _txtbanddurlbmembers.setText(url_band_pf_pic);

                String pfpicediturl = _txtbanddurlbmembers.getText().toString();
                Picasso.get().load(pfpicediturl).into(_bandsprofilepicbmembers);





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



    @Override
    public void onDeleteClick(int i) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(BandMembersProfile.this);
                builder.setMessage("Are you sure to logout?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
                                        editor = sharedPreferences.edit();
                                        editor.remove("Band_Name");
                                        editor.remove("Bandcode");
                                        editor.putBoolean("state", false);
                                        editor.commit();
                                        Intent intent = new Intent(BandMembersProfile.this, LoginBandMembers.class);
                                        BandMembersProfile.this.startActivity(intent);
                                        finish();

                                        Toast.makeText(BandMembersProfile.this, "Loged Out", Toast.LENGTH_SHORT).show();

                                    }


                        })
                        .setNegativeButton("Cancel",null);
                AlertDialog alert = builder.create();
                alert.show();


                return true;
            default:
                 return super.onOptionsItemSelected(item);
        }

    }
}





