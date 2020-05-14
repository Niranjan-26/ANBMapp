package com.example.anbmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BandNoticeBoard extends AppCompatActivity  {
    CalendarView _calender1;
    GridLayout _grid1;
    ImageView _imgvStarttime,_imgvEndtime,_ivpreviousbtn,_showMyNoticeuplods;
    EditText _noticetitle,_starttime,_endtime,_venue,_descriptionband;
    TextView _myDate,_createnoticeforband,_ChooseDatemsg,_tvbandidNoticeBoard,_DateLabel,_currentdateNotice;
    Button _nextbtn1,_submitnoticebtn;
    TimePickerDialog timePickerDialog;
    Calendar cal;
    int hour ;
    int minute ;
    String ampm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_notice_board);
        _calender1 = (CalendarView) findViewById(R.id.calender1);
        _myDate = (TextView) findViewById(R.id.myDate);
        _ChooseDatemsg = (TextView) findViewById(R.id.ChooseDatemsg);
        _createnoticeforband = (TextView) findViewById(R.id.createnoticeforband);
        _tvbandidNoticeBoard = (TextView) findViewById(R.id.tvbandidNoticeBoard);
        _DateLabel = (TextView) findViewById(R.id.DateLabel);
        _currentdateNotice = (TextView) findViewById(R.id.currentdateNotice);

        //current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-YYYY");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        _currentdateNotice.setText(currentDate);

        _grid1 = (GridLayout) findViewById(R.id.grid1);
        _imgvStarttime = (ImageView) findViewById(R.id.imgvStarttime);
        _imgvEndtime = (ImageView) findViewById(R.id.imgvEndtime);
        _ivpreviousbtn = (ImageView) findViewById(R.id.ivpreviousbtn);
        _showMyNoticeuplods = (ImageView) findViewById(R.id.showMyNoticeuplods);

        _noticetitle = (EditText) findViewById(R.id.noticetitle);
        _starttime = (EditText) findViewById(R.id.starttime);
        _endtime = (EditText) findViewById(R.id.endtime);
        _venue = (EditText) findViewById(R.id.venue);
        _descriptionband = (EditText) findViewById(R.id.descriptionband);

        _nextbtn1 = (Button) findViewById(R.id.nextbtn1);
        _submitnoticebtn = (Button) findViewById(R.id.submitnoticebtn);


        //getting band id
        Intent intent = getIntent();
        String newBandIdNotice = intent.getStringExtra("Band_Id");
        _tvbandidNoticeBoard.setText(newBandIdNotice);





        _calender1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                _myDate.setText(date);
            }
        });

        _imgvStarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                 hour = cal.get(Calendar.HOUR);
                 minute = cal.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(BandNoticeBoard.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay >= 12){
                            ampm = "PM";

                        }else{
                            ampm= "AM";
                        }
                        _starttime.setText(hourOfDay + ":" + minute + "" + ampm);
                    }
                }, hour,minute, false);
                timePickerDialog.show();
            }
        });
        _imgvEndtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                hour = cal.get(Calendar.HOUR);
                minute = cal.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(BandNoticeBoard.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay >= 12){
                            ampm = "PM";

                        }else{
                            ampm= "AM";
                        }
                        _endtime.setText(hourOfDay + ":" + minute + "" + ampm);
                    }
                }, hour,minute, false);
                timePickerDialog.show();




            }
        });

        _showMyNoticeuplods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        _submitnoticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(BandNoticeBoard.this);
                progressDialog.setMessage("Loading.....");
                progressDialog.show();
                final String Band_Id = _tvbandidNoticeBoard.getText().toString();
                final String Date = _myDate.getText().toString();
                final String Notice_Title = _noticetitle.getText().toString();
                final String Start_Time = _starttime.getText().toString();
                final String End_Time = _endtime.getText().toString();
                final String Venue = _venue.getText().toString();
                final String Description = _descriptionband.getText().toString();
                final String currentDate = _currentdateNotice.getText().toString();



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(BandNoticeBoard.this, "Notice Posted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BandNoticeBoard.this, DashBoardBandProfile.class);
                                BandNoticeBoard.this.startActivity(intent);
                                finish();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(BandNoticeBoard.this);
                                builder.setMessage("Notice Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                CreateNoticeBoardRegisterRequest createNoticeBoardRegisterRequest = new CreateNoticeBoardRegisterRequest(Band_Id, Date, Notice_Title, Start_Time, End_Time, Venue, Description, currentDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BandNoticeBoard.this);
                queue.add(createNoticeBoardRegisterRequest);



            }
        });


    }

    public void gototimepicker(View view) {
        _calender1.setVisibility(View.GONE);
        _nextbtn1.setVisibility(View.GONE);
        _ChooseDatemsg.setVisibility(View.GONE);


        _myDate.setVisibility(View.VISIBLE);
        _grid1.setVisibility(View.VISIBLE);
        _createnoticeforband.setVisibility(View.VISIBLE);
        _submitnoticebtn.setVisibility(View.VISIBLE);
        _descriptionband.setVisibility(View.VISIBLE);
        _ivpreviousbtn.setVisibility(View.VISIBLE);
        _DateLabel.setVisibility(View.VISIBLE);



    }

    public void backtoDate(View view) {
        _myDate.setVisibility(View.GONE);
        _grid1.setVisibility(View.GONE);
        _createnoticeforband.setVisibility(View.GONE);
        _submitnoticebtn.setVisibility(View.GONE);
        _descriptionband.setVisibility(View.GONE);
        _ivpreviousbtn.setVisibility(View.GONE);
        _DateLabel.setVisibility(View.GONE);

        _calender1.setVisibility(View.VISIBLE);
        _nextbtn1.setVisibility(View.VISIBLE);
        _ChooseDatemsg.setVisibility(View.VISIBLE);

    }


//    @Override
//    public void onTimeSet(TimePicker view, int hour, int minute) {
//        String AM_PM = " AM";
//        String mm_precede = "";
//        if (hour >= 12) {
//            AM_PM = " PM";
//            if (hour >=13 && hour < 24) {
//                hour -= 12;
//            }
//            else {
//                hour = 12;
//            }
//        } else if (hour == 0) {
//            hour = 12;
//        }
//        if (minute < 10) {
//            mm_precede = "0";
//        }
//        _starttime.setText( hour + ":" + mm_precede + minute + AM_PM);
//
//        _endtime.setText( hour + ":" + mm_precede + minute + AM_PM);
//
//    }

}
