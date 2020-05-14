package com.example.anbmapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateNoticeBoardRegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://njdrums.000webhostapp.com/NoticeBoardPosting.php";
    private Map<String, String> params;


    public CreateNoticeBoardRegisterRequest(String Band_Id, String Date, String Notice_Title, String Start_Time, String End_Time, String Venue, String Description, String currentDate, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Band_Id", Band_Id);
        params.put("Date", Date);
        params.put("Notice_Title", Notice_Title);
        params.put("Start_Time", Start_Time);
        params.put("End_Time", End_Time);
        params.put("Venue", Venue);
        params.put("Description", Description);
        params.put("currentDate", currentDate);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
