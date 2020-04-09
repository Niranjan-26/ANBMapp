package com.example.anbmapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BandMemberLoginRequest extends StringRequest {
    private static final String BAND_LOGIN_REQUEST_URL = "http://njdrums.000webhostapp.com/BandMemberLogin.php";
    private Map<String, String> params;

    public BandMemberLoginRequest(String Band_Name, String Bandcode, Response.Listener<String> listener) {
        super(Method.POST, BAND_LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Band_Name", Band_Name);
        params.put("Bandcode", Bandcode);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


