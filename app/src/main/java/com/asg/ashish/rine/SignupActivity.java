package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignupActivity extends AppCompatActivity {

    EditText firstname, lastname, email, password;
    LoginButton facebook;
    SignInButton google;
    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    boolean check;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstname = (EditText)findViewById(R.id.name);
        lastname = (EditText)findViewById(R.id.lastname);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        google = (SignInButton)findViewById(R.id.googlesign_in_button);
        facebook = (LoginButton)findViewById(R.id.facebook);
        if (isNetworkAvailable()){
            check = (AccessToken.getCurrentAccessToken() == null);
            if(check){
                FacebookSdk.sdkInitialize(getApplicationContext());
                AppEventsLogger.activateApp(this);
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Intent i = new Intent(SignupActivity.this, Rine_home.class);
                                startActivity(i);
                                finish();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(SignupActivity.this,"Sign In Cancelled", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Toast.makeText(SignupActivity.this,"Something went Wrong ", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else{
                Intent i = new Intent(SignupActivity.this, Rine_home.class);
                startActivity(i);
                finish();
            }

        }
        else Toast.makeText(SignupActivity.this,"Check Your connection", Toast.LENGTH_SHORT).show();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Inactivity block");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Log.d(TAG, "Intry block");
                ProgressDialog mDialog = new ProgressDialog(SignupActivity.this);
                mDialog.setMessage("Loading");
                mDialog.show();
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            ProgressDialog mDialog = new ProgressDialog(SignupActivity.this);
                            mDialog.setMessage("Loading");
                            mDialog.show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent myIntent = new Intent(SignupActivity.this, Rine_home.class);
                            startActivity(myIntent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        if(isNetworkAvailable()){
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        else Toast.makeText(SignupActivity.this,"Check Your connection", Toast.LENGTH_SHORT).show();

    }



    public void signin(View view){
        Intent myIntent = new Intent(SignupActivity.this, Sign_in.class);
        startActivity(myIntent);
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void OnReg(View view){
        if(isNetworkAvailable()){
            String name1 = firstname.getText().toString();
            String name2 = lastname.getText().toString();
            String name3 = name1 + " " + name2;
            String mail = email.getText().toString();
            String pass = password.getText().toString();
            String type = "register";
            Background_Worker background_worker = new Background_Worker(this);
            background_worker.execute(type, name3, mail, pass);
            Intent i = new Intent(SignupActivity.this, Rine_home.class);
            startActivity(i);
            finish();
        }
        else Toast.makeText(SignupActivity.this,"Check Your connection", Toast.LENGTH_SHORT).show();

    }
}
