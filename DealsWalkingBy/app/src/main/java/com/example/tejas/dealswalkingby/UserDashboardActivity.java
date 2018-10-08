package com.example.tejas.dealswalkingby;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserDashboardActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private EditText dashboardName,dashboardSurname,dashboardPhone,dashboardBillingAddress;
    private ImageView dashboardImage;
    private Button buttonUpdateInformation;
    private Uri photoUrl;
    private static final int loadedPhoto = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        dashboardImage = (ImageView) findViewById(R.id.dashboardImageView);
        dashboardName = (EditText) findViewById(R.id.dashboardName);
        getNameAndImage();
        dashboardSurname = (EditText) findViewById(R.id.dashboardSurname);
        dashboardPhone = (EditText) findViewById(R.id.dashboardPhone);
        dashboardBillingAddress = (EditText) findViewById(R.id.dashboardBillingAddress);
        buttonUpdateInformation = (Button) findViewById(R.id.buttonUpdateInformation);

        dashboardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,loadedPhoto);
            }
        });

      buttonUpdateInformation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              saveUserInformation();
              finish();
              startActivity(new Intent(UserDashboardActivity.this,UserNavigationHome.class));
          }
      });

    }
    private void saveUserInformation() {
        String name = dashboardName.getText().toString().trim();
        String surname = dashboardSurname.getText().toString().trim();
        String phone = dashboardPhone.getText().toString().trim();
        String billingAddress = dashboardBillingAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,surname,phone,billingAddress);

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mDatabaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this,"Information Updated",Toast.LENGTH_SHORT).show();

    }

    private void getNameAndImage(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            for(UserInfo profile : user.getProviderData())
            {
                photoUrl = profile.getPhotoUrl();
                String mName = profile.getDisplayName();
                dashboardName.setText(mName);
                Picasso.with(getApplicationContext())
                        .load(photoUrl.toString())
                        .resize(100,100)
                        .centerCrop()
                        .into(dashboardImage);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == loadedPhoto && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            dashboardImage.setImageURI(selectedImage);
        }
    }
}

