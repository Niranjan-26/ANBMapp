package com.example.anbmapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment  {
    ImageView usereditbutton;
    CircleImageView _civuserpf;
    SwipeRefreshLayout _swiperefreshprofileuser;
    TextView _useridinprofile,_txtviewurlinprofilefrag,_FullNametx,_tv2add,_tv4phn,fragmentprofemail,fragmentprofusername;
    public ProfileFragment(){
        //default constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

         fragmentprofusername = (TextView) v.findViewById(R.id.fragment_profile_username);
         fragmentprofemail = (TextView) v.findViewById(R.id.fragment_profile_Email);
        _useridinprofile = (TextView)v.findViewById(R.id.useridinprofile);
        _txtviewurlinprofilefrag = (TextView)v.findViewById(R.id.txtviewurlinprofilefrag);
        _FullNametx = (TextView)v.findViewById(R.id.FullNametx);
        _tv2add = (TextView)v.findViewById(R.id.tv2add);
        _tv4phn = (TextView)v.findViewById(R.id.tv4phn);
         usereditbutton = (ImageView)v.findViewById(R.id.useredit);
        _civuserpf = (CircleImageView)v.findViewById(R.id.civuserpf);
        _swiperefreshprofileuser = (SwipeRefreshLayout) v.findViewById(R.id.swiperefreshprofileuser);
        //swipe refresh
        _swiperefreshprofileuser.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getuserpfpicurl();
                getIntentData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        _swiperefreshprofileuser.setRefreshing(false);
                    }
                }, 4000);




            }
        });
         usereditbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String new_useridinprofile = _useridinprofile.getText().toString();
                 Intent intent = new Intent(getContext(), EditUserProfile.class);
                 intent.putExtra("new_useridinprofile", new_useridinprofile);
                 getContext().startActivity(intent);
             }
         });



        getIntentData();
        getuserpfpicurl();

        return v;


    }
    //getting purextra
    public void getIntentData(){
        Intent intent =  (getActivity().getIntent());

        String userid = intent.getStringExtra("Id");
        String username = intent.getStringExtra("Username");
        String full_name = intent.getStringExtra("Full_Name");
        String gender = intent.getStringExtra("Gender");
        String address = intent.getStringExtra("Address");
        String phone = intent.getStringExtra("Phone");
        String email = intent.getStringExtra("Email");



        fragmentprofusername.setText(username);
        fragmentprofemail.setText(email);
        _useridinprofile.setText(userid);
        _FullNametx.setText(full_name);
        _tv2add.setText(address);
        _tv4phn.setText(phone);

    }

    //getting user pf pic url
    private void getuserpfpicurl () {
        String url = config88ViewUserPf.DATA_URL + _useridinprofile.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONpfurl(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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



                _txtviewurlinprofilefrag.setText(url_user_pf_pic);



                String pfpicuserurl = _txtviewurlinprofilefrag.getText().toString();
                Picasso.get().load(pfpicuserurl).into(_civuserpf);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }






}









