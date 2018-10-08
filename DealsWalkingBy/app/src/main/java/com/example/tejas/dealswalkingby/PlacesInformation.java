package com.example.tejas.dealswalkingby;

import android.net.Uri;

import com.example.tejas.dealswalkingby.Model.Location;

import java.net.URI;

/**
 * Created by Tejas on 25-01-2018.
 */

public class PlacesInformation {

    public String place_name;
    public Uri place_image;
    public String place_deal_description;
    public Location place_location;

    public PlacesInformation(){

    }

    public PlacesInformation(String place_name,Uri place_image,String place_deal_description,Location place_location){
        this.place_name = place_name;
        this.place_image = place_image;
        this.place_deal_description = place_deal_description;
        this.place_location = place_location;
    }

}
