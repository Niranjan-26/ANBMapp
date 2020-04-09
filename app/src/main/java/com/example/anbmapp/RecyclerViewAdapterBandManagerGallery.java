package com.example.anbmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterBandManagerGallery extends RecyclerView.Adapter<RecyclerViewAdapterBandManagerGallery.MyViewHolder> {
    private Context mContext;
    private  List<Bands_uploads_details> mData;
    private OnItemClickListener nListener;



    public interface  OnItemClickListener{
//        void onItemClick(int i);
        void onDeleteClick(int i);
    }
    public void SetOnItemClickListener(OnItemClickListener listener){
        nListener = listener;
    }

    public RecyclerViewAdapterBandManagerGallery(Context mContext, List<Bands_uploads_details> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layoutforbandmanagergallery,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        bandsdetails details = mData.get(position);
        Bands_uploads_details details = mData.get(i);

        myViewHolder.tv_caption.setText(details.getCaption());
        String urlforpic = details.getUrl_upload();
        Picasso.get().load(urlforpic).into(myViewHolder.tv_Images);

    }
//    public Bands_uploads_details getNoteAt(int i){
//        return mData.get(i);
//
//    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_caption;
        private ImageView tv_Images,_deletebutton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_caption = (TextView)itemView.findViewById(R.id.tvcaption);
            tv_Images = (PhotoView) itemView.findViewById(R.id.imageView);
            _deletebutton = (ImageView) itemView.findViewById(R.id.deletebutton);

            _deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nListener != null){
                        int i = getAdapterPosition();
                        if(i != RecyclerView.NO_POSITION){
                            nListener.onDeleteClick(i);
                        }

                    }

                }
            });


        }
    }
}
