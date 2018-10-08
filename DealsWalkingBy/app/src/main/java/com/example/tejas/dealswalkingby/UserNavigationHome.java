package com.example.tejas.dealswalkingby;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class UserNavigationHome extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private TextView name,email;
    private Uri photoUrl;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_navigation_home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Open,R.string.Close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(UserNavigationHome.this,LoginActivity.class));
                }
            }
        };
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        profilePicture = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.imageView);
        name = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.nameTextView);
        email = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.emailTextView);

        getCurrentInfo();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id){
                    case R.id.dashboard:
                        finish();
                        startActivity(new Intent(UserNavigationHome.this,UserDashboardActivity.class));
                        return true;


                    case R.id.places:
                        finish();
                        startActivity(new Intent(UserNavigationHome.this,NearbyPlaces.class));
                        return true;

//
                    case R.id.Deals:
                        finish();
                        startActivity(new Intent(UserNavigationHome.this,DealsActivity.class));
                        return true;
//
                    case R.id.Feedback:
                        finish();
                        startActivity(new Intent(UserNavigationHome.this,AreaSearchMain.class));
                        return true;

                    case R.id.chat:
                        finish();
                        startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                        return true;

                    case R.id.logout:
                        mFirebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        return true;




                    default:
                        return true;

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCurrentInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            for(UserInfo profile : user.getProviderData())
            {
                String providerId = profile.getProviderId();

                String uid = profile.getUid();

                String mName = profile.getDisplayName();
                String mEmail = profile.getEmail();
                photoUrl = profile.getPhotoUrl();

                name.setText(mName);
                email.setText(mEmail);

                Picasso.with(getApplicationContext())
                        .load(photoUrl.toString())
                        .resize(100,100)
                        .centerCrop()
                        .into(profilePicture);
            }
        }
    }
}
