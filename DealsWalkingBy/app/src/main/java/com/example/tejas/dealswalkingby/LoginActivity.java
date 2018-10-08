package com.example.tejas.dealswalkingby;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButtonLogin;
    private EditText mEditTextEmail,mEditTextPassword;
    private TextView mTextViewNotRegistered;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser()!=null){
            //Start profile Activity
            finish();
            startActivity(new Intent(LoginActivity.this,NearbyPlaces.class));
        }

        mButtonLogin = (Button) findViewById(R.id.loginButton);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mTextViewNotRegistered = (TextView) findViewById(R.id.textViewNotRegistered);

        mProgressDialog = new ProgressDialog(this);

        mButtonLogin.setOnClickListener(LoginActivity.this);
        mTextViewNotRegistered.setOnClickListener(LoginActivity.this);
    }

    private void userLogin(){
    String email = mEditTextEmail.getText().toString().trim();
    String password = mEditTextPassword.getText().toString().trim();

    //Checking if Email and Password are Empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Please enter Email ID",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressDialog.setMessage("Logging you in");
        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressDialog.dismiss();
                if(task.isSuccessful()){
                    //Start the profile activity
                    finish();
                    startActivity(new Intent(getApplicationContext(),NearbyPlaces.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == mButtonLogin){
            userLogin();
        }
        if(view == mTextViewNotRegistered){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}
