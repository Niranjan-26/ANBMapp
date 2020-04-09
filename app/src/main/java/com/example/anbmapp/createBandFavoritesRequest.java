package com.example.anbmapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class createBandFavoritesRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://njdrums.000webhostapp.com/uploadFavourites.php";
    private Map<String, String> params;


    public createBandFavoritesRequest(String Band_Name, String Genre, String Based_City, String Email,String favourites, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Band_Name", Band_Name);
        params.put("Genre", Genre);
        params.put("Based_City", Based_City);
        params.put("Email", Email);
        params.put("favourites", favourites);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}
