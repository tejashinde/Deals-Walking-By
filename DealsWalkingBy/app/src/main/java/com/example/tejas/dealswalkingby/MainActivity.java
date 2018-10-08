package com.example.tejas.dealswalkingby;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejas.dealswalkingby.Model.Users;
import com.example.tejas.dealswalkingby.utils.Constants;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SignInButton googleBtn;
    private Button registerButton;
    private EditText mEditTextEmail, mEditTextPassword,mEditTextPhone;
    private TextView mTextViewAlreadyRegistered;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private final static int RC_SIGN_IN = 2;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onStart() {
        super.onStart();

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    //Firebase Database
    //private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        googleBtn = (SignInButton) findViewById(R.id.googleBtn);
        mFirebaseAuth = FirebaseAuth.getInstance();

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    finish();
                    startActivity(new Intent(MainActivity.this,UserNavigationHome.class));
                }
            }
        };

        /*if(mFirebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(MainActivity.this,HomeScreen.class));
        }*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mProgressDialog = new ProgressDialog(this);
        registerButton = (Button) findViewById(R.id.registerButton);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        //mEditTextPhone = (EditText) findViewById(R.id.editTextPhone);
        mTextViewAlreadyRegistered = (TextView) findViewById(R.id.textViewAlreadyRegistered);

        registerButton.setOnClickListener(this);
        mTextViewAlreadyRegistered.setOnClickListener(this);
    }

//    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build();

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(MainActivity.this, "Authentication went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {    
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            //updateUI(user);
//                            String photo_url = null;
//                            if(account.getPhotoUrl() != null)
//                            {
//                                photo_url = account.getPhotoUrl().toString();
//                            }

//                            Users app_user = new Users(account.getDisplayName() + " " + account.getFamilyName(), account.getEmail(), photo_url, FirebaseAuth.getInstance().getCurrentUser().getUid());

//                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                            DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USER_KEY);
//                            databaseReference.child(account.getEmail().replace(".",","))
//                                    .setValue(user, new DatabaseReference.CompletionListener() {
//                                        @Override
//                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                            finish();
//                                            startActivity(new Intent(MainActivity.this,UserNavigationHome.class));
//                                        }
//                                    });
//                            if(BuildConfig.DEBUG) Log.d("Success","Successfull");
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
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
                    startActivity(new Intent(MainActivity.this,UserNavigationHome.class));
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
