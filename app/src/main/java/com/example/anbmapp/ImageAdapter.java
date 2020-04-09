package com.example.anbmapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImageIds = new int[] {R.drawable.albatross, R.drawable.shadows, R.drawable.side};

    ImageAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mImageIds[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Toast.makeText(mContext, "slide 1 clicked", Toast.LENGTH_SHORT).show();
                }else if(position == 1){
                    Toast.makeText(mContext, "slide 2 clicked", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "slide 3 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        container.addView(imageView, 0);
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}
