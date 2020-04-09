package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import io.paperdb.Paper;

public class showFavbandlist extends AppCompatActivity {
    ArrayList<bandsdetails> bandsdetailsArrayList;
    FavBandDetailsAdapter favBandDetailsAdapter;
    RecyclerView rvFavBand;
    ArrayList<String> favbandDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favbandlist);

        rvFavBand = findViewById(R.id.recyclerviewFav);
        try {
            fetchBandDetails();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "first go to band profile", Toast.LENGTH_SHORT).show();
        }




    }

    private void fetchBandDetails() {
        bandsdetailsArrayList = new ArrayList<>();
        favbandDetailList = Paper.book().read("favBandDetail", new ArrayList<String>());

        for(String Band_Name: favbandDetailList){
            if(Paper.book().contains(Band_Name)){
                bandsdetailsArrayList.add((bandsdetails)Paper.book().read(Band_Name));
            }
        }

        favBandDetailsAdapter = new FavBandDetailsAdapter(bandsdetailsArrayList, false);
        setupRV();

    }

    private void setupRV() {
        rvFavBand.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvFavBand.setAdapter(favBandDetailsAdapter);
        favBandDetailsAdapter.notifyDataSetChanged();
    }


}
