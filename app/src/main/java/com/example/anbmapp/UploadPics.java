package com.example.anbmapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class UploadPics extends AppCompatActivity implements View.OnClickListener {

    private TextView _Id;
    private EditText NAME;
    private ImageView imgView,  ChooseBn;
    private  ImageView UploadBn;
    private final int IMG_REQUEST =1;
    private Bitmap bitmap;
    ProgressDialog pd;
    LinearLayout _linearchossebtn;
    private String uploadUrl = "http://njdrums.000webhostapp.com/userpicuploads.php";
//    int check = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplodsspic);

        UploadBn = (ImageView) findViewById(R.id.uploadbn);
        ChooseBn = (ImageView) findViewById(R.id.choosebn);
        _Id = (TextView) findViewById(R.id.id);
        NAME = (EditText) findViewById(R.id.name);
        imgView = (ImageView) findViewById(R.id.imageViews);
        _linearchossebtn = (LinearLayout) findViewById(R.id.linearchossebtn);


        Intent intent = getIntent();
        String _band_id = intent.getStringExtra("Band_Id");
//        int band_id = intent.getIntExtra("Band_Id",0);


        _Id.setText(_band_id);

        ChooseBn.setOnClickListener(this);
        UploadBn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
//        check++;
        switch (v.getId())
        {
            case R.id.choosebn:
                selectImage();
                ChooseBn.setVisibility(View.GONE);
                _linearchossebtn.setVisibility(View.GONE);
                imgView.setVisibility(View.VISIBLE);
                break;

            case R.id.uploadbn:
                uploadImage();
                break;

//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(check == 1){
//                            Toast.makeText(UploadPics.this, "clicked once", Toast.LENGTH_SHORT).show();
//
//                        }
//                        check = 0;
//                    }
//                },500);


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
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
                NAME.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void uploadImage(){
        pd = new ProgressDialog(UploadPics.this);
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
                            Toast.makeText(UploadPics.this,Response,Toast.LENGTH_LONG).show();
                            imgView.setImageResource(0);
                            imgView.setVisibility(View.GONE);
                            NAME.setText("");
                            NAME.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }pd.dismiss();


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
                params.put("id", _Id.getText().toString().trim());
                params.put("name", NAME.getText().toString().trim());
                params.put("image", imageToString(bitmap));

                return params;

            }
        };
        MySingleton.getInstance(UploadPics.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }


}
