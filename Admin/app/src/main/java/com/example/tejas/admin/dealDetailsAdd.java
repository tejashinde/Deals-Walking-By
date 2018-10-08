package com.example.tejas.admin;

import android.*;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class dealDetailsAdd extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private final static int FINE_LOCATION = 505;


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 10000;  /* 10 secs */
    private long FASTEST_INTERVAL = 20000; /* 2 sec */

    private LocationManager locationManager;

    int GALLERY_INTENT_CODE = 1;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    EditText deal_title, deal_description, deal_validity,deal_promo_code;
    TextView mTextViewDisplayDate;
    ImageButton deal_image;
    ImageButton deal_location;
    Uri FilePathUri;
    ProgressDialog progressDialog;
    StorageReference storageReference;
    String Storage_Path = "deal_images/";

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String imageName;
    String imageUrl;
    String longitude, latitude;
    String deal_validity_day,deal_validity_month,deal_validity_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details_add);

        mTextViewDisplayDate = (TextView) findViewById(R.id.textViewSelectDate);

        deal_promo_code = (EditText) findViewById(R.id.deal_promo_code);
        deal_title = (EditText) findViewById(R.id.deal_title);
        deal_description = (EditText) findViewById(R.id.deal_description);
        deal_validity = (EditText) findViewById(R.id.deal_validity);

        deal_image = (ImageButton) findViewById(R.id.open_gallery_imageview);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Deals");
        mFirebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(dealDetailsAdd.this);
        storageReference = FirebaseStorage.getInstance().getReference();

        mTextViewDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        dealDetailsAdd.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                deal_validity_day = String.valueOf(day);
                deal_validity_month = String.valueOf(month);
                deal_validity_year = String.valueOf(year);

//                ShowToast(deal_validity_day
//+ deal_validity_month
//+ deal_validity_year);
            }
        };


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        LocationManager locationManager1 = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);



    }


    public void addToFirebase(View view) {
        SendToFirebase();
    }

    public void open_gallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select deal picture"), GALLERY_INTENT_CODE);
    }

    public void giveMeLocationAdmin(View view) {


        checkLocation();
        SendToFirebase();


    }

    private void SendToFirebase() {

        DealStructure dealStructure = new DealStructure(imageName,imageUrl,
                deal_title.getText().toString(),deal_description.getText().toString(),longitude,latitude,
                deal_validity.getText().toString(),deal_validity_day,deal_validity_month,deal_validity_year,deal_promo_code.getText().toString());

//        mDatabaseReference.push().getKey();

        mDatabaseReference.push().setValue(dealStructure);


        ResetAll();

    }

    private void ResetAll() {

        deal_image.setImageBitmap(null);
        deal_validity.setText("");
        deal_description.setText("");
        deal_title.setText("");
        latitude=null;
        longitude=null;
        deal_promo_code=null;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_INTENT_CODE) {
                FilePathUri = data.getData();


                if (FilePathUri != null) {

                    UploadImageFileToFirebaseStorage();
                    ShowToast("Image Selected");
                }


            }
        }
    }

    public void UploadImageFileToFirebaseStorage() {

        final FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            assert currentUser != null;
                            String TempImageName = currentUser.getDisplayName();

                            ShowToast(currentUser.getDisplayName());
                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
//                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            ShowToast("Image Uploaded Successfully...");

                            @SuppressWarnings("VisibleForTests")
                            ImageUploadModel imageUploadModel = new ImageUploadModel(TempImageName.toLowerCase(),
                                    taskSnapshot.getDownloadUrl().toString());

                            imageUrl = imageUploadModel.getImageURL();
                            imageName = imageUploadModel.getImageName();

                            if (imageUrl != null) {
                                ShowToast("Image Url retrieved");
                                Glide.with(getApplicationContext())
                                        .load(imageUrl)
                                        .into(deal_image);
                            }
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
//                            Toast.makeText(getApplication(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            ShowToast("Failed While retrieving URL");
                            ShowToast(currentUser.getDisplayName());
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });

        } else {

//            Toast.makeText(ProfileUser.this, "Please Select Image ", Toast.LENGTH_LONG).show();
//            Toast.makeText(ProfileUser.this, "Failed To Upload ", Toast.LENGTH_LONG).show();

            ShowToast("Please Select Image");

        }
    }

    private void ShowToast(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }





    @Override
    public void onConnected(Bundle bundle) {


        startLocationUpdates();

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {


                ShowToast("asking for permission....");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                ShowToast("Else part");

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }
            return;
        }
        else
            ShowToast("Permission Granted");

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
//            ShowToast("Null");
            startLocationUpdates();
        }
        if (location != null) {

//            ShowToast("Not Null");
            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
//        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.



            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
//        Log.d("reque", "--->>>>");

    }

    @Override
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());

//        ShowToast("Changed...");
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        ShowToast(String.valueOf(latLng));

        this.latitude = String.valueOf(location.getLatitude());
        this.longitude = String.valueOf(location.getLongitude());
        ShowToast("Location retrieved...");
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.




                } else {

                    ShowToast("Please Grant the permission");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    public void dealDeatailsAddBackButtonActivity(View view) {
        startActivity(new Intent(dealDetailsAdd.this,adminHomeScreen.class));
    }
}
