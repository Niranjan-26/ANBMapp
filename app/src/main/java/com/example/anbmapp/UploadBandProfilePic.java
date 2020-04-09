package com.example.anbmapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class UploadBandProfilePic extends AppCompatActivity implements View.OnClickListener {
    private ImageView _uploadBandProfile, _gallerygoBandProfile, _editBandProfile;
    private TextView _Band_Id;

    private final int IMG_REQUEST =1;
    private Bitmap bitmap;
    ProgressDialog pd;
    private String uploadUrls = "http://njdrums.000webhostapp.com/uploadbandspfpic.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_band_profile_pic);

        _uploadBandProfile = (ImageView) findViewById(R.id.uploadBandProfile);
        _gallerygoBandProfile = (ImageView) findViewById(R.id.gallerygoBandProfile);
        _Band_Id = (TextView) findViewById(R.id.bandIdProfileImage);
        _editBandProfile = (ImageView) findViewById(R.id.editBandProfile);
        _uploadBandProfile.setOnClickListener(this);
        _gallerygoBandProfile.setOnClickListener(this);

        Intent intent = getIntent();
        String _band_id = intent.getStringExtra("Band_Id");
//        int band_id = intent.getIntExtra("Band_Id",0);


        _Band_Id.setText(_band_id);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.gallerygoBandProfile:
                selectImage();
                _gallerygoBandProfile.setVisibility(View.GONE);
                _editBandProfile.setVisibility(View.VISIBLE);
            break;

            case R.id.uploadBandProfile:

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
                _editBandProfile.setImageBitmap(bitmap);
                _editBandProfile.setVisibility(View.VISIBLE);
                _Band_Id.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void uploadImage(){
        pd = new ProgressDialog(UploadBandProfilePic.this);
        pd.setMessage("Uploading...");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(true);
        pd.setProgress(0);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(UploadBandProfilePic.this,Response,Toast.LENGTH_LONG).show();
                            _editBandProfile.setImageResource(0);
                            _editBandProfile.setVisibility(View.GONE);
                            _Band_Id.setText("");
                            _Band_Id.setVisibility(View.GONE);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("_Band_Id", _Band_Id.getText().toString().trim());
                params.put("image", imageToString(bitmap));

                return params;

            }
        };
        MySingleton.getInstance(UploadBandProfilePic.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }
}
