package com.example.anbmapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdatingBandProfilePic extends AppCompatActivity implements View.OnClickListener {
    private ImageView _gallerygo, _uploadimages;
    private EditText caption;
    private ImageView uploadImageview;
    private final int IMG_REQUEST =1;
    private Bitmap bitmap;
    ProgressDialog pd;
    private String uploadUrl = "http://njdrums.000webhostapp.com/userpicuploads.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating_band_profile_pic);

        _uploadimages = (ImageView) findViewById(R.id.uploadImages);
        _gallerygo = (ImageView) findViewById(R.id.gallerygo);
        caption = (EditText) findViewById(R.id.caption);
        uploadImageview = (ImageView) findViewById(R.id.uploadImageView);
        _uploadimages.setOnClickListener(this);
        _gallerygo.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.gallerygo:
                selectImage();
                break;

            case R.id.uploadImages:

                uploadImage();

                break;
        }

    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                uploadImageview.setImageBitmap(bitmap);
                uploadImageview.setVisibility(View.VISIBLE);
                caption.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void uploadImage(){
        pd = new ProgressDialog(UpdatingBandProfilePic.this);
        pd.setMessage("loading");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(true);
        pd.setProgress(0);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(UpdatingBandProfilePic.this,Response,Toast.LENGTH_LONG).show();
                            uploadImageview.setImageResource(0);
                            uploadImageview.setVisibility(View.GONE);
                            caption.setText("");


                            caption.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HttpClient", "error: " + error.toString());
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("caption", caption.getText().toString().trim());
                params.put("image", imageToString(bitmap));

                return params;

            }
        };
        MySingleton.getInstance(UpdatingBandProfilePic.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }
}
