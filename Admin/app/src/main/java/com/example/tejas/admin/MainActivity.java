package com.example.tejas.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SignInButton googleBtn;
    private Button registerButton;
    private EditText mEditTextEmail, mEditTextPassword,mEditTextPhone;
    private TextView mTextViewAlreadyRegistered;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    finish();
                    startActivity(new Intent(MainActivity.this,adminHomeScreen.class));
                }
            }
        };

        mProgressDialog = new ProgressDialog(this);
        registerButton = (Button) findViewById(R.id.registerButton);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        //mEditTextPhone = (EditText) findViewById(R.id.editTextPhone);
        mTextViewAlreadyRegistered = (TextView) findViewById(R.id.textViewAlreadyRegistered);

        registerButton.setOnClickListener(this);
        mTextViewAlreadyRegistered.setOnClickListener(this);

    }
    private void registerUser(){
        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT).show();
            //Stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
            //Stopping the function execution further
            return;
        }
        //if validations are okay, the progress dialog is shown

        mProgressDialog.setMessage("Getting you registered...");
        mProgressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressDialog.dismiss();
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,adminHomeScreen.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"User Already Registered",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == registerButton){
            registerUser();
        }
        if(view == mTextViewAlreadyRegistered){
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }
}
