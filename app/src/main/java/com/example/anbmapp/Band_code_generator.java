package com.example.anbmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Band_code_generator extends AppCompatActivity {
    Button generateBotton;
    TextView generatedTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_code_generator);
        generateBotton = (Button) findViewById(R.id.btnGenerate);
        generatedTextView = (TextView) findViewById(R.id.txtvBandcode);
        generateBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatedTextView.setText(generateBandCode(10));
                Toast toast=Toast.makeText(getApplicationContext(),"Generated Code will be saved as your BandCode",Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    private String generateBandCode(int length){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i<length;i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
