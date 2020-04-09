package com.example.anbmapp;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateBandUploadStatusRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://njdrums.000webhostapp.com/uploadBandStatus.php";
    private Map<String, String> params;


    public CreateBandUploadStatusRequest(String caption, String url_upload, String Band_Id, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("caption", caption);
        params.put("url_upload", url_upload);
        params.put("Band_Id", Band_Id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
