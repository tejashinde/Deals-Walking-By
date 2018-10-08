package com.example.tejas.dealswalkingby;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;

    private TextView mTextViewLogout;
    private EditText mEditTextName, mEditTextPhone;
    private Button mButtonSaveInformation;

    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextPhone = (EditText) findViewById(R.id.editTextPhone);
        mTextViewLogout = (TextView) findViewById(R.id.textViewLogout);
        mButtonSaveInformation = (Button) findViewById(R.id.buttonSaveInformation);

        mTextViewLogout.setOnClickListener(this);
        mButtonSaveInformation.setOnClickListener(this);
    }

    private void saveUserInformation() {
        String name = mEditTextName.getText().toString().trim();
        String phone = mEditTextPhone.getText().toString().trim();

//        UserInformation userInformation = new UserInformation(name,phone);

        FirebaseUser user = mFirebaseAuth.getCurrentUser();

//        mDatabaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this,"Information Saved",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View view) {
        if (view == mTextViewLogout) {
            mFirebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        if (view == mButtonSaveInformation) {
            saveUserInformation();
        }
    }
}
