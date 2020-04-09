package com.example.anbmapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private  List<bandsdetails> mData;
    private  List<bandsdetails> nDatafull;

    //interfaces for onlicklistener item
    private OnItemClickListener mListener;



    public interface  OnItemClickListener{
        void onItemClick(int i);
    }
    public void SetOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public RecyclerViewAdapter(Context mContext, List<bandsdetails> mData) {
        this.mContext = mContext;
        this.mData = mData;
        //for searching filter
        nDatafull = new ArrayList<>(mData);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mylistforhomefragment,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        bandsdetails details = mData.get(position);
        bandsdetails details = mData.get(i);

        myViewHolder.tv_band_name.setText(details.getBand_Name());
        myViewHolder.tv_genre.setText(details.getGenre());
        myViewHolder.tv_based_city.setText(details.getBased_City());
        myViewHolder.tv_email.setText(details.getEmail());
        myViewHolder.tv_band_id.setText(details.getBand_Id());
        myViewHolder.tv_phone.setText(details.getPhone());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
// adding filter
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<bandsdetails> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(nDatafull);

            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (bandsdetails details : nDatafull){
                    if (details.getBand_Name().toLowerCase().contains(filterPattern)){
                        filteredList.add(details);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_band_name, tv_genre, tv_based_city, tv_email, tv_band_id, tv_phone;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_band_name = (TextView)itemView.findViewById(R.id.tvBandNamerecyler);
            tv_genre = (TextView)itemView.findViewById(R.id.tvBandGenrerecyler);
            tv_based_city = (TextView)itemView.findViewById(R.id.tvBandBasedCityrecyler);
            tv_email = (TextView)itemView.findViewById(R.id.tvBandEmailrecyler);
            tv_band_id = (TextView)itemView.findViewById(R.id.tvBandIdrecyler);
            tv_phone = (TextView)itemView.findViewById(R.id.tvBandPhonerecyler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int i = getAdapterPosition();
                        if(i != RecyclerView.NO_POSITION){
                            mListener.onItemClick(i);
                        }

                    }
                }
            });





        }


//    RecyclerViewAdapter(List<bandsdetails>mData){
//        this.mData = mData;
//
//
//    }
    }
}
