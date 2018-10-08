package com.example.tejas.admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class adminHomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

    }




    public void OpenAddPanel(View view) {
        startActivity(new Intent(adminHomeScreen.this,dealDetailsAdd.class));
    }

    public void OpenUpdatePanel(View view) {
        startActivity(new Intent(adminHomeScreen.this,DealsActivity.class));
    }

    public void adminHomeScreenLogout(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(adminHomeScreen.this,LoginActivity.class));
                    }
                });
    }
}
