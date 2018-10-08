package com.example.tejas.dealswalkingby;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Tejas on 23-02-2018.
 */

public class SliderAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    public SliderAdapter(Context mContext)
    {
        this.mContext = mContext;
    }


    public int[] slide_images = {

            R.drawable.deals_icon,
            R.drawable.location_icon,
            R.drawable.food_icon

    };

    public String[] slide_headings = {

        "Deals",
        "Nearby",
        "Mapview"

    };

    public String[] slide_descriptions = {
      "Deals walking by presents you with various activity related deals close by depending on your location. Log on to see the same!," + "Hola!",
              "A nearby tag with which you can view live places choosing activities you can perform," + "Bonjour",
        "A map view with which you can navigate yourself to the places where the deals reside in no time!," + "Namaste"

    };




    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slideLayout_images);
        TextView slideHeading = (TextView) view.findViewById(R.id.slideLayout_headings);
        TextView slideDescription = (TextView) view.findViewById(R.id.slideLayout_descriptions);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descriptions[position]);

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView(((RelativeLayout)object));

    }
}
