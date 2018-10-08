package com.example.tejas.dealswalkingby;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.tejas.dealswalkingby.Adapters.RestaurantAdapter;
import com.example.tejas.dealswalkingby.Model.Geometry;
import com.example.tejas.dealswalkingby.Model.Restaurant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class JsonDataIntoRecyclerView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RestaurantAdapter mRestaurantAdapter;
    private ArrayList<Restaurant> mRestaurantCollection;
    private String subzone;
    private String categorySelected;
    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    String mLongitudeText;
    String mLatitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_data_into_recycler_view);
        init();
        new FetchDataTask().execute();

        Intent intent = getIntent();
        subzone = intent.getStringExtra("subzone");
        if(subzone == null)
        {
            subzone = "pune";
        }
        categorySelected = intent.getStringExtra("categoryID");

        subzone = subzone.replace(" ","%20").trim();

//        if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
//                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
////        Toast.makeText(this, subzone, Toast.LENGTH_SHORT).show();
//
//        }

//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
//                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }
//            mGoogleApiClient.connect();
//        }
    }


    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.restaurant_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRestaurantCollection = new ArrayList<>();
        mRestaurantAdapter = new RestaurantAdapter(mRestaurantCollection, this);
        mRecyclerView.setAdapter(mRestaurantAdapter);
    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                mGoogleApiClient);
//        if (mLastLocation != null) {
//            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
//            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }


    public class FetchDataTask extends AsyncTask<Void, Void, Void> {
        private String mZomatoString;

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Uri builtUri = Uri.parse(getString(R.string.zomato_new) + subzone + getString(R.string.category) + categorySelected);

//            https://developers.zomato.com/api/v2.1/search?entity_type=subzone&q=nibm

            Log.d("URL", builtUri.toString());
//            Uri builtUri = Uri.parse("https://developers.zomato.com/api/v2.1/")
//                    .buildUpon()
//                    .path("search")
//                    .appendQueryParameter("entity_type","subzone")
//                    .appendQueryParameter("q",subzone)
//                    .build();


            URL url;
            try {
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("user-key", "9b953d93b478bec7dfd51ef7cfe6c793");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                mZomatoString = buffer.toString();
                JSONObject jsonObject = new JSONObject(mZomatoString);

                Log.v("Response", jsonObject.toString());

                JSONArray restaurantsArray = jsonObject.getJSONArray("restaurants");

                for (int i = 0; i < restaurantsArray.length(); i++) {

                    Log.v("BRAD_", i + "");
                    String name;
                    String address;
                    String currency;
                    String imageUrl;
                    long lon;
                    long lat;
                    long cost;
                    float rating;
                    String votes;


                    JSONObject jRestaurant = (JSONObject) restaurantsArray.get(i);
                    jRestaurant = jRestaurant.getJSONObject("restaurant");
                    JSONObject jLocation = jRestaurant.getJSONObject("location");
                    JSONObject jRating = jRestaurant.getJSONObject("user_rating");


                    name = jRestaurant.getString("name");
                    address = jLocation.getString("address");
                    lat = jLocation.getLong("latitude");
                    lon = jLocation.getLong("longitude");
                    currency = jRestaurant.getString("currency");
                    cost = jRestaurant.getInt("average_cost_for_two");
                    imageUrl = jRestaurant.getString("thumb");
                    rating = (float) jRating.getDouble("aggregate_rating");
                    votes = String.valueOf(jRating.getInt("votes"));

                    Restaurant restaurant = new Restaurant();
                    restaurant.setName(name);
                    restaurant.setAddress(address);
                    restaurant.setLatitude(lat);
                    restaurant.setLongitude(lon);
                    restaurant.setCurrency(currency);
                    restaurant.setCost(String.valueOf(cost));
                    restaurant.setImageUrl(imageUrl);
                    restaurant.setRating(String.valueOf(rating));
                    restaurant.setDistance(Double.parseDouble(new DecimalFormat("##.##").format(Math.random() * 10)));
                    restaurant.setVotes(votes);


                    mRestaurantCollection.add(restaurant);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mRestaurantAdapter.notifyDataSetChanged();

        }
    }

    private double meterDistanceBetweenPoints(long lat_a, long lng_a, double lat_b, double lng_b) {
        float pk = (float) (180.f / Math.PI);

        long a1 = (long) (lat_a / pk);
        long a2 = (long) (lng_a / pk);
        long b1 = (long) (lat_b / pk);
        long b2 = (long) (lng_b / pk);

        long t1 = (long) (Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2));
        long t2 = (long) (Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2));
        long t3 = (long) (Math.sin(a1) * Math.sin(b1));
        long tt = (long) Math.acos(t1 + t2 + t3);

        return (6366000 * tt) / 1000;
    }

    public static long getRandomNumber(long min, long max) {

        Random random = new Random();
        return random.nextLong() % (max - min) + max;

    }
}
