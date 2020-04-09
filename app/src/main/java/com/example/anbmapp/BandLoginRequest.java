package com.example.anbmapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BandLoginRequest extends StringRequest {
    private static final String BAND_LOGIN_REQUEST_URL = "http://njdrums.000webhostapp.com/BandLogin.php";
    private Map<String, String> params;

    public BandLoginRequest(String Band_Name, String Password, String Bandcode, Response.Listener<String> listener) {
        super(Request.Method.POST, BAND_LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Band_Name", Band_Name);
        params.put("Password", Password);
        params.put("Bandcode", Bandcode);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


