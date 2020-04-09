package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class uploadBandStatus extends AppCompatActivity {
    TextView _txtvBandidstatus, _txtvBandurlstatus;
    ImageView _btnUploadStatus;
    EditText _edittxtStatus;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_band_status);


        _btnUploadStatus = (ImageView) findViewById(R.id.btnUploadStatus);
        _edittxtStatus = (EditText) findViewById(R.id.edittxtStatus);
        _txtvBandidstatus = (TextView) findViewById(R.id.txtvBandidstatus);
        _txtvBandurlstatus = (TextView) findViewById(R.id.txtvBandurlstatus);


        _btnUploadStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(uploadBandStatus.this);
                pd.setMessage("Uploading");
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setIndeterminate(true);
                pd.setProgress(0);
                pd.show();
                Intent intent = getIntent();
                String newBand_id2 = intent.getStringExtra("Band_Id2");
                _txtvBandidstatus.setText(newBand_id2);

                final String caption = _edittxtStatus.getText().toString();
                final String url_upload = _txtvBandurlstatus.getText().toString();
                final String Band_Id = _txtvBandidstatus.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
//                                AlertDialog.Builder builder = new AlertDialog.Builder(uploadBandStatus.this);
//                                builder.setMessage("Status posted")
//                                        .setNegativeButton("ok", null)
//                                        .create()
//                                        .show();

//                                Intent intent = new Intent(uploadBandStatus.this, DashBoardBandProfile.class);
//                                uploadBandStatus.this.startActivity(intent);
                                Toast.makeText(uploadBandStatus.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(uploadBandStatus.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }pd.dismiss();

                    }
                };

                CreateBandUploadStatusRequest createBandUploadStatusRequest = new CreateBandUploadStatusRequest(caption, url_upload, Band_Id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(uploadBandStatus.this);
                queue.add(createBandUploadStatusRequest);


            }
        });
    }
}
