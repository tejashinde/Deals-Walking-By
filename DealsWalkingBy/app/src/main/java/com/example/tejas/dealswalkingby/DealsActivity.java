package com.example.tejas.dealswalkingby;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DealsActivity extends AppCompatActivity  implements DealsAdapter.OnItemClicked{

    RecyclerView mRecyclerView;
    DealsAdapter mDealsAdapter;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    List<DealStructure> mDealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        mDealList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFirebaseDatabase = FirebaseDatabase.getInstance();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mDatabaseReference = mFirebaseDatabase.getReference().child("Deals");





        mDealsAdapter = new DealsAdapter(this,mDealList);
        mRecyclerView.setAdapter(mDealsAdapter);




//        DealStructure dealStructure = new DealStructure("name","asdfas",
//                "name","asdfas","name","asdfas","sadf");
//        mDealList.add(dealStructure);
//        mDealList.add(dealStructure);
//        mDealList.add(dealStructure);
//        mDealList.add(dealStructure);
//        mDealList.add(dealStructure);
//        mDealList.add(dealStructure);
//
//        mDealsAdapter.notifyDataSetChanged();


        GetData();

    }

    private void GetData() {



        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                DealStructure dealStructure = dataSnapshot.getValue(DealStructure.class);

                if (dealStructure != null) {
                    mDealList.add(dealStructure);
                    mDealsAdapter.notifyDataSetChanged();

                }
                else
                    ShowToast("no Data");


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
        });

    }

    private void ShowToast(String taost) {
        Toast.makeText(this,taost,Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnItemClickedHere(DealStructure dealStructure) {


//        ShowToast(dealStructure.getDeal_latitude());

//        ShowToast(dealStructure.getDeal_title());

//        ShowToast(lat +":"+lude);


        Intent gotoMap = new Intent(DealsActivity.this,DealMap.class);
        gotoMap.putExtra("lat",dealStructure.getDeal_latitude());
        gotoMap.putExtra("lg",dealStructure.getDeal_longitude());
        gotoMap.putExtra("placeName",dealStructure.getDeal_title());
        startActivity(gotoMap);



    }
}
