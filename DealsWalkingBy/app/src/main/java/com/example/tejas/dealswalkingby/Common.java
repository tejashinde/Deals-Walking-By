package com.example.tejas.dealswalkingby;

import com.example.tejas.dealswalkingby.Remote.IGoogleAPIService;
import com.example.tejas.dealswalkingby.Remote.RetrofitClient;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tejas on 15-02-2018.
 */

public class Common {
    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";
    public static IGoogleAPIService getGoogleAPIService()
    {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
