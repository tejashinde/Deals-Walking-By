package com.example.tejas.dealswalkingby.Remote;

import com.example.tejas.dealswalkingby.Model.MyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Tejas on 15-02-2018.
 */

public interface IGoogleAPIService {
    @GET
    Call<MyPlaces> getNearbyPlaces (@Url String url);
}
