package com.example.anbmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterBandNoticeBoard extends RecyclerView.Adapter<RecyclerViewAdapterBandNoticeBoard.MyViewHolder> {
    private Context mContext;
    private  List<BandsNoticeBoardDetails> mData;
    private OnItemClickListener nListener;



    public interface  OnItemClickListener{
//        void onItemClick(int i);
        void onDeleteClick(int i);
    }
    public void SetOnItemClickListener(OnItemClickListener listener){
        nListener = listener;
    }

    public RecyclerViewAdapterBandNoticeBoard(Context mContext, List<BandsNoticeBoardDetails> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycardviewforbandmembersprofile,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        bandsdetails details = mData.get(position);
        BandsNoticeBoardDetails details = mData.get(i);

        myViewHolder._ntbmembers.setText(details.getNotice_Title());
        myViewHolder._dbmembers.setText(details.getDate());
        myViewHolder._stbmembers.setText(details.getStart_Time());
        myViewHolder._etbmembers.setText(details.getEnd_Time());
        myViewHolder._vbmembers.setText(details.getVenue());
        myViewHolder._desbmembers.setText(details.getDescription());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView _ntbmembers,_dbmembers,_stbmembers,_etbmembers,_vbmembers,_desbmembers,_seemorebmembers,_a,_b,_c,_d,_e,_seelessbmembers;
        private ImageView _ivdelbmembers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            _ntbmembers = (TextView)itemView.findViewById(R.id.ntbmembers);
            _dbmembers = (TextView)itemView.findViewById(R.id.dbmembers);
            _stbmembers = (TextView)itemView.findViewById(R.id.stbmembers);
            _etbmembers = (TextView)itemView.findViewById(R.id.etbmembers);
            _vbmembers = (TextView)itemView.findViewById(R.id.vbmembers);
            _desbmembers = (TextView)itemView.findViewById(R.id.desbmembers);
            _seelessbmembers = (TextView)itemView.findViewById(R.id.seelessbmembers);
            _seemorebmembers = (TextView)itemView.findViewById(R.id.seemorebmembers);
            _a = (TextView)itemView.findViewById(R.id.a);
            _b = (TextView)itemView.findViewById(R.id.b);
            _c = (TextView)itemView.findViewById(R.id.c);
            _d = (TextView)itemView.findViewById(R.id.d);
            _e = (TextView)itemView.findViewById(R.id.e);
            _ivdelbmembers = (ImageView) itemView.findViewById(R.id.ivdelbmembers);

            _seemorebmembers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _a.setVisibility(View.VISIBLE);
                    _b.setVisibility(View.VISIBLE);
                    _c.setVisibility(View.VISIBLE);
                    _d.setVisibility(View.VISIBLE);
                    _e.setVisibility(View.VISIBLE);
                    _dbmembers.setVisibility(View.VISIBLE);
                    _stbmembers.setVisibility(View.VISIBLE);
                    _etbmembers.setVisibility(View.VISIBLE);
                    _vbmembers.setVisibility(View.VISIBLE);
                    _desbmembers.setVisibility(View.VISIBLE);
                    _seelessbmembers.setVisibility(View.VISIBLE);
                    _seemorebmembers.setVisibility(View.GONE);


                }
            });
            _seelessbmembers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _a.setVisibility(View.GONE);
                    _b.setVisibility(View.GONE);
                    _c.setVisibility(View.GONE);
                    _d.setVisibility(View.GONE);
                    _e.setVisibility(View.GONE);
                    _dbmembers.setVisibility(View.GONE);
                    _stbmembers.setVisibility(View.GONE);
                    _etbmembers.setVisibility(View.GONE);
                    _vbmembers.setVisibility(View.GONE);
                    _desbmembers.setVisibility(View.GONE);
                    _seelessbmembers.setVisibility(View.GONE);
                    _seemorebmembers.setVisibility(View.VISIBLE);

                }
            });


            _ivdelbmembers.setOnClickListener(new View.OnClickListener() {
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
