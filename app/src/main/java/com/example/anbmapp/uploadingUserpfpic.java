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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class uploadingUserpfpic extends AppCompatActivity implements View.OnClickListener {

    private ImageView _uploadUserProfile, _gallerygoUserProfile;
    CircleImageView  _edituserprofile;
    private TextView _useridpfpicupload;

    private final int IMG_REQUEST =2;
    private Bitmap bitmap;
    ProgressDialog pd;
    private String uploadUrls = "http://njdrums.000webhostapp.com/uploaduserspfpic.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_userpfpic);

        _uploadUserProfile = (ImageView) findViewById(R.id.uploadUserProfile);
        _gallerygoUserProfile = (ImageView) findViewById(R.id.gallerygoUserProfile);
        _edituserprofile = (CircleImageView) findViewById(R.id.edituserprofile);

        _useridpfpicupload = (TextView) findViewById(R.id.useridpfpicupload);

        _uploadUserProfile.setOnClickListener(this);
        _gallerygoUserProfile.setOnClickListener(this);

        Intent intent2 = getIntent();
        String _user_id2 = intent2.getStringExtra("user_id2");
//        int band_id = intent.getIntExtra("Band_Id",0);


        _useridpfpicupload.setText(_user_id2);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.gallerygoUserProfile:
                selectImage();
                break;

            case R.id.uploadUserProfile:

                uploadImage();

                break;
        }

    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
        _gallerygoUserProfile.setVisibility(View.GONE);
        _edituserprofile.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                _edituserprofile.setImageBitmap(bitmap);
                _edituserprofile.setVisibility(View.VISIBLE);
                _useridpfpicupload.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void uploadImage(){
//        pd = new ProgressDialog(uploadingUserpfpic.this);
//        pd.setMessage("loading");
//        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        pd.setIndeterminate(true);
//        pd.setProgress(0);
//        pd.show();
        final ProgressDialog progressDialog = new ProgressDialog(uploadingUserpfpic.this);
        progressDialog.setMessage("Uploading.....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(uploadingUserpfpic.this,Response,Toast.LENGTH_LONG).show();
                            _edituserprofile.setImageResource(0);
                            _edituserprofile.setVisibility(View.GONE);
                            _useridpfpicupload.setText("");
                            _useridpfpicupload.setVisibility(View.GONE);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        pd.dismiss();


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
                params.put("_useridpfpicupload", _useridpfpicupload.getText().toString().trim());
                params.put("image", imageToString(bitmap));

                return params;

            }
        };
        MySingleton.getInstance(uploadingUserpfpic.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }
}