package com.example.anbmapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserDetailsRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://njdrums.000webhostapp.com/updateUserDetails.php";
    private Map<String, String> params;


    public UpdateUserDetailsRequest( String Id, String Full_Name, String Username, String Gender, String Address, String Phone, String Email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Id", Id);
        params.put("Full_Name", Full_Name);
        params.put("Username", Username);
        params.put("Gender", Gender);
        params.put("Address", Address);
        params.put("Phone", Phone);
        params.put("Email", Email);
//        params.put("Password", Password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
