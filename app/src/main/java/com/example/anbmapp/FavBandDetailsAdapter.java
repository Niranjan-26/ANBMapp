package com.example.anbmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavBandDetailsAdapter extends RecyclerView.Adapter<FavBandDetailsAdapter.ViewHolder> {

    ArrayList<bandsdetails> bandsdetailsArrayList;
    Context context;
    boolean fromMain = false;

    public FavBandDetailsAdapter(ArrayList<bandsdetails>bandsdetailsArrayList, boolean fromMain){
        this.bandsdetailsArrayList = bandsdetailsArrayList;
        this.fromMain = fromMain;
    }

    @NonNull
    @Override
    public FavBandDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate the custom layout
        View view;
        if (fromMain){
            view = inflater.inflate(R.layout.mylistforhomefragment_fav, parent, false);

        }else{
            view = inflater.inflate(R.layout.mylistforhomefragment, parent, false);
        }
        //return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavBandDetailsAdapter.ViewHolder holder, int position) {
        bandsdetails details = bandsdetailsArrayList.get(position);

        TextView tv_band_name = holder.tv_band_name;
        TextView tv_genre = holder.tv_genre;
        TextView tv_based_city = holder.tv_based_city;
        TextView tv_email = holder.tv_email;
        TextView tv_band_id = holder.tv_band_id;


        tv_band_name.setText(details.getBand_Name());
        tv_genre.setText(details.getGenre());
        tv_based_city.setText(details.getBased_City());
        tv_email.setText(details.getEmail());
        tv_band_id.setText(details.getBand_Id());

    }

    @Override
    public int getItemCount() {
        return bandsdetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_band_name, tv_genre, tv_based_city, tv_email, tv_band_id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_band_name = (TextView)itemView.findViewById(R.id.tvBandNamerecyler);
            tv_genre = (TextView)itemView.findViewById(R.id.tvBandGenrerecyler);
            tv_based_city = (TextView)itemView.findViewById(R.id.tvBandBasedCityrecyler);
            tv_email = (TextView)itemView.findViewById(R.id.tvBandEmailrecyler);
            tv_band_id = (TextView)itemView.findViewById(R.id.tvBandIdrecyler);


        }
    }
}
