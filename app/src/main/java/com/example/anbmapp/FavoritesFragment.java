package com.example.anbmapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.anbmapp.SQlite.FavBand;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

import static com.example.anbmapp.R.drawable.fav_unchecked;

public class FavoritesFragment extends Fragment {
    DatabaseHelper myDb;
    SwipeMenuListView ivListView;
    TextView _useridforfavdetails,_tvbandphoneshowfav;
    public static final String KEY_BANDNAME =  "BAND_NAME";
    public static final String KEY_BANDID =  "BAND_ID";
    String bandName,bandIds,bandPhone;



    public FavoritesFragment() {

        //required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);
        myDb = new DatabaseHelper(getContext());

        ivListView = (SwipeMenuListView) v.findViewById(R.id.recyclerviewFav);
        _useridforfavdetails = (TextView) v.findViewById(R.id.useridforfavdetails);
        _tvbandphoneshowfav = (TextView) v.findViewById(R.id.tvbandphoneshowfav);

        Intent intent = (getActivity().getIntent());
        String userid = intent.getStringExtra("Id");
        _useridforfavdetails.setText(userid);



        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x01, 0x87,
                        0x86)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setIcon(R.drawable.ic_call_black_24dp);
//                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        ivListView.setMenuCreator(creator);

        ivListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        ArrayList<HashMap<String, String>> theList = new ArrayList<HashMap<String, String>>();
                        //passing the filter
                        Cursor res = myDb.getBandPhone(bandIds);
                        if (res.getCount() == 0) {
                            Toast.makeText(getContext(), "No Phone Number", Toast.LENGTH_SHORT).show();



                        }else{
                            while (res.moveToNext()){
                                bandPhone = res.getString(5);


                            }
                        }
//                        Toast.makeText(getContext(), "calling the band", Toast.LENGTH_SHORT).show();
                        String s = "tel:" + bandPhone;
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(s));
                        startActivity(intent);
//                        Toast.makeText(getContext(), "phone is" + bandPhone, Toast.LENGTH_SHORT).show();


                        break;
                    case 1:


                        Integer deletedRows = myDb.deleteData(bandIds);
                        if(deletedRows > 0){
                            Toast.makeText(getContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
                           showFav();
                        }else{
                            Toast.makeText(getContext(), "press again", Toast.LENGTH_SHORT).show();
                            showFav();
                        }


                        break;
                    default:

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        showFav();

        return v;

    }
    public void showFav(){
        ArrayList<HashMap<String, String>> theList = new ArrayList<HashMap<String, String>>();
        //passing the filter
        Cursor res = myDb.getAllData(_useridforfavdetails.getText().toString());
        if (res.getCount() == 0) {
            Toast.makeText(getContext(), "No Favourites", Toast.LENGTH_SHORT).show();



        }else{
            while (res.moveToNext()){
                bandName = res.getString(4);
                bandIds = res.getString(1);

                final HashMap<String, String> FavBand = new HashMap<>();
                FavBand.put(KEY_BANDNAME,bandName);
                FavBand.put(KEY_BANDID,bandIds);

                theList.add(FavBand);
                ListAdapter listAdapter = new SimpleAdapter(
                        getContext(), theList, R.layout.mylistforhomefragment_fav,
                        new String[]{KEY_BANDNAME, KEY_BANDID},
                        new int[]{R.id.tvBandNameshowfav,R.id.tvbandidshowfav});
                ivListView.setAdapter(listAdapter);
            }
        }

    }

//    public void showbandphone(){
//        ArrayList<HashMap<String, String>> theList = new ArrayList<HashMap<String, String>>();
//        //passing the filter
//        Cursor res = myDb.getBandPhone(bandIds);
//        if (res.getCount() == 0) {
//            Toast.makeText(getContext(), "No Phone Number", Toast.LENGTH_SHORT).show();
//
//
//
//        }else{
//            while (res.moveToNext()){
//                bandPhone = res.getString(5);
//
//
////                final HashMap<String, String> FavBand = new HashMap<>();
////                FavBand.put(KEY_BANDNAME,bandName);
////                FavBand.put(KEY_BANDID,bandIds);
////
////                theList.add(FavBand);
////                ListAdapter listAdapter = new SimpleAdapter(
////                        getContext(), theList, R.layout.mylistforhomefragment_fav,
////                        new String[]{KEY_BANDNAME, KEY_BANDID},
////                        new int[]{R.id.tvBandNameshowfav,R.id.tvbandidshowfav});
////                ivListView.setAdapter(listAdapter);
//            }
//        }
//
//    }









}