package com.example.anbmapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button uploadBtn, chooseBtn;
    private EditText caption;
    private ImageView uploadImageview;
    private final int IMG_REQUEST =1;
    private Bitmap bitmap;
    private String uploadUrl = "http://njdrums.000webhostapp.com/userpicuploads.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        uploadBtn = (Button) findViewById(R.id.uploadBtn2);
        chooseBtn = (Button) findViewById(R.id.chooseBtn2);
        caption = (EditText) findViewById(R.id.caption2);
        uploadImageview = (ImageView) findViewById(R.id.uploadImageView2);
        uploadBtn.setOnClickListener(this);
        chooseBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.chooseBtn2:
                selectImage();
            break;

            case R.id.uploadBtn2:
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(UserProfileActivity.this,Response,Toast.LENGTH_LONG).show();
                            uploadImageview.setImageResource(0);
                            uploadImageview.setVisibility(View.GONE);
                            caption.setText("");
                            caption.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
                params.put("caption", caption.getText().toString().trim());
                params.put("image", imageToString(bitmap));

                return params;

            }
        };
        MySingleton.getInstance(UserProfileActivity.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }
}
