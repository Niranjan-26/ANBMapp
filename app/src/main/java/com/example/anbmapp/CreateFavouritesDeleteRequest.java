package com.example.anbmapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateFavouritesDeleteRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://njdrums.000webhostapp.com/deleteUserFav.php";
    private Map<String, String> params;


    public CreateFavouritesDeleteRequest(String user_id, String Band_Id, String is_fav, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("Band_Id", Band_Id);
        params.put("is_fav", is_fav);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
