package com.example.tejas.dealswalkingby;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class adminLogin extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Authenticated-Users");


        AuthUI.getInstance().signOut(getApplicationContext());
        SetAuthListenerToActivity();



    }

    private void SetAuthListenerToActivity() {

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {

                    // startActivity(new Intent(getActivity(),Login.class));
                    onSignedInInitiilze(currentUser.getDisplayName());
                    Toast.makeText(getApplication(),"Signed In already",Toast.LENGTH_LONG).show();

                } else {
                    // if the user isn't authenticated

                    onSignedOutCleanUp();
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

                    //Toast.makeText(getActivity(),"Please Sign In",Toast.LENGTH_SHORT).show();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);

                }


            }
        };

    }

    private void onSignedInInitiilze(String displayName) {

        AttachDatabaseReadListeners();

    }

    private void AttachDatabaseReadListeners() {


        final FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {


                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {


                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);


        }

    }
    private void onSignedOutCleanUp() {
        detachDeatabaseReasdListener();
    }

    private void detachDeatabaseReasdListener() {

        if(mChildEventListener!=null){
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }


    }
}