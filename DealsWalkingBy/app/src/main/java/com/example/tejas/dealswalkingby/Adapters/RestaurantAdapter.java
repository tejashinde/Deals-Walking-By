package com.example.tejas.dealswalkingby.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tejas.dealswalkingby.Model.Restaurant;
import com.example.tejas.dealswalkingby.R;

import java.util.ArrayList;

/**
 * Created by Tejas on 25-02-2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {

    private ArrayList<Restaurant> mData;
    private Activity mActivity;

    public RestaurantAdapter(ArrayList<Restaurant> data, Activity activity) {
        mData = data;
        mActivity = activity;
    }

    @Override
    public RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jsondatastructure,parent,false);
        return new RestaurantHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantHolder holder, int position) {
        Restaurant restaurant = mData.get(position);

        holder.setName(restaurant.getName());
        holder.setAddress(restaurant.getAddress());
        holder.setCost("Average cost for 2: " + restaurant.getCurrency() + restaurant.getCost());
        holder.setRating(restaurant.getRating());
        holder.setDistance(restaurant.getDistance() + "Kms away");
        holder.setVotes(restaurant.getVotes());

        Glide.with(mActivity)
                .load(restaurant.getImageUrl())
                .into(holder.restaurantImageView);
    }

    @Override
    public int getItemCount() {
        if(mData == null)
            return 0;
        return mData.size();
    }

    public class RestaurantHolder extends RecyclerView.ViewHolder{

        ImageView restaurantImageView;
        TextView restaurantNameTextView;
        TextView restaurantAddressTextView;
        TextView restaurantRatingTextView;
        TextView costTextView;
        TextView distanceTextView;
        TextView votesTextView;

        public RestaurantHolder(View itemView) {
            super(itemView);

            restaurantImageView = (ImageView) itemView.findViewById(R.id.image_view_restaurant);
            restaurantNameTextView = (TextView) itemView.findViewById(R.id.textview_restaurant_name);
            restaurantAddressTextView = (TextView) itemView.findViewById(R.id.textview_restaurant_address);
            restaurantRatingTextView = (TextView) itemView.findViewById(R.id.textview_restaurant_rating);
            costTextView = (TextView) itemView.findViewById(R.id.textview_cost_for_two);
            distanceTextView = (TextView) itemView.findViewById(R.id.textview_restaurant_distance);
            votesTextView = (TextView) itemView.findViewById(R.id.restaurant_votes);
        }

        public void setName(String name){restaurantNameTextView.setText(name);}
        public void setAddress(String address){restaurantAddressTextView.setText(address);}
        public void setRating(String rating){restaurantRatingTextView.setText(rating);}
        public void setCost(String cost){costTextView.setText(cost);}
        public void setDistance(String distance){distanceTextView.setText(distance);}
        public  void setVotes(String votes){votesTextView.setText(votes);}
    }
}
