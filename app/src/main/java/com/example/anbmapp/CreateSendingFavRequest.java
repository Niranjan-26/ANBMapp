package com.example.anbmapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateSendingFavRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://njdrums.000webhostapp.com/addtofav.php";
    private Map<String, String> params;


    public CreateSendingFavRequest(String user_id, String is_fav, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("is_fav", is_fav);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
