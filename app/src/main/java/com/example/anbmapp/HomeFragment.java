package com.example.anbmapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener {

    TextView _useridinfav;
    SearchView _searchviewBand;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter adapter;


    List<bandsdetails> listitemList;
    RecyclerView listView2;

    public HomeFragment(){
        //required empty constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        listView2=(RecyclerView) v.findViewById(R.id.lview2);
        _useridinfav=(TextView) v.findViewById(R.id.useridfor_fav);
        _searchviewBand=(SearchView) v.findViewById(R.id.searchviewBand);


        layoutManager = new LinearLayoutManager(getContext());
        listView2.setHasFixedSize(true);
        listView2.setLayoutManager(layoutManager);
        listitemList = new ArrayList<>();
        loadRecyclerViewData();
        //getting user id
        Intent intent =  (getActivity().getIntent());
        String userid = intent.getStringExtra("Id");
        _useridinfav.setText(userid);



        //removing the search icon from the keyboard
        _searchviewBand.setImeOptions(EditorInfo.IME_ACTION_DONE);

        _searchviewBand.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;

            }
        });




        return v;



    }

    private void loadRecyclerViewData() {
//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Searching.....");
//        progressDialog.show();
        String url="https://njdrums.000webhostapp.com/fetchbandsdataRecycler.php";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        bandsdetails details = new bandsdetails(
                                o.getString("Band_Name"),
                                o.getString("Genre"),
                                o.getString("Based_City"),
                                o.getString("Email"),
                                o.getString("Band_Id"),
                                o.getString("Phone")


                                );
                        listitemList.add(details);
                        adapter = new RecyclerViewAdapter(getContext(), listitemList);
                        listView2.setAdapter(adapter);
                        //getting context from recycleradapter in this fragment
                        adapter.SetOnItemClickListener(HomeFragment.this);


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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);




    }
    //onclick on the recyclerview

    @Override
    public void onItemClick(int i) {
        String user_id = _useridinfav.getText().toString();
        Intent detailIntent = new Intent(getContext(), UserAreaAcivity.class);
        bandsdetails clickedItem = listitemList.get(i);

        detailIntent.putExtra("user_id", user_id);

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("banddetails", clickedItem );
//        detailIntent.putExtra("bundle", bundle);

        detailIntent.putExtra("fav_Band_Name", clickedItem.getBand_Name());
        detailIntent.putExtra("fav_Genre", clickedItem.getGenre());
        detailIntent.putExtra("fav_Based_City", clickedItem.getBased_City());
        detailIntent.putExtra("fav_Email", clickedItem.getEmail());
        detailIntent.putExtra("fav_band_id", clickedItem.getBand_Id());
        detailIntent.putExtra("fav_band_phone", clickedItem.getPhone());
        startActivity(detailIntent);


    }

}
