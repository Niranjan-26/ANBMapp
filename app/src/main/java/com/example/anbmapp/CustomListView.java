package com.example.anbmapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

/**
 * Created by jaiso on 13-02-2018.
 */

public class CustomListView extends ArrayAdapter<String>{

    private String[] profilename;
    private String[] email;
    private String[] CaptionStatus;
    private String[] imagepath;
    private Activity context;
//    Bitmap bitmap;

    public CustomListView(Context context, String[] profilename, String[] email, String[] CaptionStatus, String[] imagepath) {
        super(context, R.layout.layout,profilename);
        this.context= (Activity) context;
        this.profilename=profilename;
        this.email=email;
        this.CaptionStatus=CaptionStatus;
        this.imagepath=imagepath;
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)r.getTag();

        }

        viewHolder.tvw1.setText(profilename[position]);
        viewHolder.tvw2.setText(email[position]);
        viewHolder.tvw3.setText(CaptionStatus[position]);


//        viewHolder.btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), UserAreaAcivity.class);
////                intent.putExtra("Id", Id);
////                intent.putExtra("Full_Name", Full_Name);
////                intent.putExtra("Username", Username);
////                intent.putExtra("Email", Email);
//                getContext().startActivity(intent);
//            }
//        });
//        new GetImageFromURL(viewHolder.ivw).execute(imagepath[position]);




        Picasso.get().load(imagepath[position]).into(viewHolder.ivw);

        return r;
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        TextView tvw3;

//        Button btn4;

        ViewHolder(View v){
            tvw1=(TextView)v.findViewById(R.id.tvprofilename);
            tvw2=(TextView)v.findViewById(R.id.tvemail);
            tvw3=(TextView)v.findViewById(R.id.statusnewsfeed);
            ivw=(ImageView)v.findViewById(R.id.imageView);
//            btn4 = (Button)v.findViewById(R.id.btnNewsfeed);

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

