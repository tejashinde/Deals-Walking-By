package com.example.tejas.dealswalkingby;

import android.content.Intent;
import android.net.Credentials;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLogin extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private EditText mEditTextPhone, mEditTextOtp;
    private Button mButtonSendMessage, mButtonVerify;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

    mEditTextPhone = (EditText) findViewById(R.id.editTextPhone);
    mEditTextOtp = (EditText) findViewById(R.id.editTextotp);
    mButtonSendMessage = (Button) findViewById(R.id.buttonSendMessage);
    mButtonVerify = (Button) findViewById(R.id.buttonVerify);
    mFirebaseAuth = FirebaseAuth.getInstance();

    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mVerificationCode = s;
            Toast.makeText(getApplicationContext(),"OTP sent to the number",Toast.LENGTH_SHORT).show();
        }
    };

    }

    public void sendOtp(View view){
        String phoneNumber =mEditTextPhone.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60,TimeUnit.SECONDS,this,mCallbacks);
    }

    public void signInWithPhone(PhoneAuthCredential credential){
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Signed in successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verify(View view){
        String mInputCode = mEditTextOtp.getText().toString();
        if(mVerificationCode.equals("")){
            verifyPhoneNumber(mVerificationCode,mInputCode);
        }
    }

    public void verifyPhoneNumber(String mVerificationCode,String mInputCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationCode,mInputCode);
        signInWithPhone(credential);
    }
}
