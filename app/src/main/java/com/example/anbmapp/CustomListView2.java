package com.example.anbmapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jaiso on 13-02-2018.
 */

public class CustomListView2 extends ArrayAdapter<String> {

    private String[] band_id;
    private String[] band_name;
    private String[] genre;
    private String[] based_city;
    private String[] email;

    private Activity context;
    ViewHolder viewHolder=null;
//    Bitmap bitmap;

    public CustomListView2(Context context,String[] band_id, String[] band_name, String[] genre, String[] based_city, String[] email) {
        super(context, R.layout.mylistforhomefragment,band_name);
        this.context= (Activity) context;
        this.band_id=band_id;
        this.band_name=band_name;
        this.genre=genre;
        this.based_city=based_city;
        this.email=email;


    }

    @NonNull
    @Override

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r=convertView;
        viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.mylistforhomefragment,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)r.getTag();

        }

        viewHolder.tvw1.setText(band_name[position]);
        viewHolder.tvw2.setText(genre[position]);
        viewHolder.tvw3.setText(based_city[position]);
        viewHolder.tvw4.setText(email[position]);
        viewHolder.tvw5.setText(band_id[position]);



        r.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast t = Toast.makeText(getContext(), "Message",
//                        Toast.LENGTH_SHORT);
//                t.show();
                Intent intent = new Intent(getContext(), UserAreaAcivity.class);

                intent.putExtra("fav_Band_Name", band_name[position]);
                intent.putExtra("fav_Genre", genre[position]);
                intent.putExtra("fav_Based_City", based_city[position]);
                intent.putExtra("fav_Email", email[position]);
                intent.putExtra("fav_band_id", band_id[position]);


                getContext().startActivity(intent);

            }
        });
//        viewHolder.btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                    String fav_Band_Name = viewHolder.tvw1.getText().toString();
////                    String fav_Genre = viewHolder.tvw2.getText().toString();
////                    String fav_Based_City = viewHolder.tvw3.getText().toString();
////                    String fav_Email = viewHolder.tvw4.getText().toString();
//                    String fav_band_id = viewHolder.tvw5.getText().toString();
//
//
//
//                Intent intent = new Intent(getContext(), UserAreaAcivity.class);
////        startActivity(new Intent(getApplicationContext(),UploadPics.class));
////                intent.putExtra("fav_Band_Name", fav_Band_Name);
////                intent.putExtra("fav_Genre", fav_Genre);
////                intent.putExtra("fav_Based_City", fav_Based_City);
////                intent.putExtra("fav_Email", fav_Email);
//                intent.putExtra("fav_band_id", fav_band_id);
//
//                getContext().startActivity(intent);
//
//
//            }
//        });
//        new GetImageFromURL(viewHolder.ivw).execute(imagepath[position]);






        return r;
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        TextView tvw4;
        TextView tvw5;

        Button btn4;

        ViewHolder(View v){
            tvw1=(TextView)v.findViewById(R.id.tvBandNamerecyler);
            tvw2=(TextView)v.findViewById(R.id.tvBandGenrerecyler);
            tvw3=(TextView)v.findViewById(R.id.tvBandBasedCityrecyler);
            tvw4=(TextView)v.findViewById(R.id.tvBandEmailrecyler);
            tvw5=(TextView)v.findViewById(R.id.tvBandIdrecyler);



//            btn4 = (Button)v.findViewById(R.id.checkboxFavourties);

        }

    }

//    public class GetImageFromURL extends AsyncTask<String,Void,Bitmap>
//    {
//
//        ImageView imgView;
//        public GetImageFromURL(ImageView imgv)
//        {
//            this.imgView=imgv;
//        }
//        @Override
//        protected Bitmap doInBackground(String... url) {
//            String urldisplay=url[0];
//            bitmap=null;
//
//            try{
//
//                InputStream ist=new java.net.URL(urldisplay).openStream();
//                bitmap= BitmapFactory.decodeStream(ist);
//            }
//            catch (Exception ex)
//            {
//                ex.printStackTrace();
//            }
//
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap){
//
//            super.onPostExecute(bitmap);
//            imgView.setImageBitmap(bitmap);
//        }
//    }



}



