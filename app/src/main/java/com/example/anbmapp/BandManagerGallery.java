package com.example.anbmapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class BandManagerGallery extends AppCompatActivity implements  RecyclerViewAdapterBandManagerGallery.OnItemClickListener{
    TextView _bandidformanagergallery;
    RecyclerView _lviewBandGallery;
    RecyclerView.LayoutManager layoutManagerGallery;
    RecyclerViewAdapterBandManagerGallery adapterGallery;
    List<Bands_uploads_details> mlistviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_manager_gallery);

        _bandidformanagergallery = (TextView) findViewById(R.id.bandidformanagergallery);
        _lviewBandGallery = (RecyclerView) findViewById(R.id.lviewBandGallery);


        layoutManagerGallery = new LinearLayoutManager(getApplicationContext());
        _lviewBandGallery.setHasFixedSize(true);
        _lviewBandGallery.setLayoutManager(layoutManagerGallery);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(_lviewBandGallery);
        mlistviews = new ArrayList<>();

        //getting band id
        Intent intent = getIntent();
        String newBandIdNotice = intent.getStringExtra("Band_Id");
        _bandidformanagergallery.setText(newBandIdNotice);

        loadRecyclerViewDataGallery();
    }

    private void loadRecyclerViewDataGallery() {

        String url="https://njdrums.000webhostapp.com/fetchingrecyclerViewBandGallery.php?Band_Id="+_bandidformanagergallery.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        Bands_uploads_details details = new Bands_uploads_details(
                                o.getString("caption"),
                                o.getString("url_upload")



                        );
                        mlistviews.add(details);
                        adapterGallery = new RecyclerViewAdapterBandManagerGallery(getApplicationContext(), mlistviews);
                        _lviewBandGallery.setAdapter(adapterGallery);
                        adapterGallery.SetOnItemClickListener(BandManagerGallery.this);


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

    //removing from the gallery
    private static final String DELETE_URL = "http://njdrums.000webhostapp.com/deleteBandsPost.php?Band_ID=&caption";

    private void register(String Band_Id,String url_upload) {
        class RegisterUsers extends AsyncTask<String, Void, String> {

            UpdateUserClass ruc = new UpdateUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("Band_Id", params[0]);
                data.put("url_upload", params[1]);

                String result = ruc.sendPostRequest(DELETE_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(Band_Id, url_upload);
    }

//sharing button handled
    @Override
    public void onDeleteClick(int i) {
        Bands_uploads_details clickedSharePost = mlistviews.get(i);
        final String caption = clickedSharePost.getCaption();
        final String url_upload = clickedSharePost.getUrl_upload();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //using ifelse for caption and image
        if(url_upload.equals("http://.jpg")){
            intent.putExtra(Intent.EXTRA_TEXT, caption);
            startActivity(intent.createChooser(intent, "Share via:"));
        }else{
            intent.putExtra(Intent.EXTRA_TEXT, url_upload);
            startActivity(intent.createChooser(intent, "Share via:"));
        }






    }
    //handling delete swipe button
    public void onSwipeDelete(int i){
        Bands_uploads_details clickedPost = mlistviews.get(i);

        final String Band_Id = _bandidformanagergallery.getText().toString();
//        final String caption = clickedPost.getCaption();
        final String url_upload = clickedPost.getUrl_upload();

        register(Band_Id,url_upload);

    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            onSwipeDelete(viewHolder.getAdapterPosition());
            Toast.makeText(BandManagerGallery.this, "Deleted", Toast.LENGTH_SHORT).show();
            mlistviews.remove(viewHolder.getAdapterPosition());
            adapterGallery.notifyDataSetChanged();

        }
    };



}
