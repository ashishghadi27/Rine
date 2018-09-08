package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
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
import com.google.firebase.auth.UserInfo;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    String newstring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstname = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        google = findViewById(R.id.googlesign_in_button);
        facebook = findViewById(R.id.facebook);
        facebook.setReadPermissions(Arrays.asList("public_profile", "email"));


        if (isNetworkAvailable()){
            check = (AccessToken.getCurrentAccessToken() == null);
            SharedPreferences sharedPreferences = getSharedPreferences("Issignedin",MODE_PRIVATE);
            String verify = sharedPreferences.getString("verify", "notsigned");
            if(verify.equals("signed")){
                Intent i = new Intent(SignupActivity.this, Rine_home.class);
                startActivity(i);
                finish();

            }
            else if(check){
                FacebookSdk.sdkInitialize(getApplicationContext());
                AppEventsLogger.activateApp(this);
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                final Bundle params = new Bundle();
                                params.putString("fields", "name,email,gender,picture.type(large)");
                                new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                                        new GraphRequest.Callback() {
                                            @Override
                                            public void onCompleted(GraphResponse response) {
                                                if (response != null) {
                                                    try {
                                                        JSONObject data = response.getJSONObject();
                                                        if (data.has("picture")) {
                                                            String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                                            String name = data.getString("name");
                                                            String mail = data.getString("email");
                                                            Log.v("THE EMAIL IS:", mail);
                                                            String username = mail;
                                                            String regex = "(\\w+)@";
                                                            Pattern p = Pattern.compile(regex);
                                                            Matcher m = p.matcher(username);
                                                            String user="";
                                                            if(m.find())
                                                                user = m.group(0).replace("@","" );

                                                            Background_Worker background_worker = new Background_Worker(SignupActivity.this);
                                                            background_worker.execute("register", name, mail, mail, user);
                                                            SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString("name", name);
                                                            editor.putString("email", mail);
                                                            editor.putString("profilepic", profilePicUrl);
                                                            editor.apply();

                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(SignupActivity.this,"EXCEPTION",Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                        }).executeAsync();
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
                            String name = user.getDisplayName();
                            for (UserInfo profile : user.getProviderData()) {

                                // Get the profile photo's url
                                Uri photoUrl = profile.getPhotoUrl();

                                // Variable holding the original String portion of the url that will be replaced
                                String originalPieceOfUrl = "s96-c/photo.jpg";

                                // Variable holding the new String portion of the url that does the replacing, to improve image quality
                                String newPieceOfUrlToAdd = "s400-c/photo.jpg";
                                String photoPath = photoUrl.toString();

                                // Replace the original part of the Url with the new part
                                newstring = photoPath.replace(originalPieceOfUrl, newPieceOfUrlToAdd);

                                // Check if the Url path is null
                            }// End if

                            Log.v("THE PHOTO URL IS: ", newstring);
                            String mail = user.getEmail();
                            String username = mail;
                            String regex = "(\\w+)@";
                            Pattern p = Pattern.compile(regex);
                            Matcher m = p.matcher(username);
                            String User="";
                            if(m.find())
                                User = m.group(0).replace("@","" );

                            Background_Worker background_worker = new Background_Worker(SignupActivity.this);
                            background_worker.execute("register", name, mail, mail, User);
                            SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("name", name);
                            editor.putString("profilepic", newstring);
                            editor.putString("email", mail);
                            editor.apply();
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
            String username = mail;
            String regex = "(\\w+)@";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(username);
            String user="";
            if(m.find())
                user = m.group(0).replace("@","" );
            Background_Worker background_worker = new Background_Worker(this);
            background_worker.execute(type, name3, mail, pass, user);

            SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name", name3);
            editor.putString("email", mail);
            editor.apply();

        }
        else Toast.makeText(SignupActivity.this,"Check Your connection", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent myIntent = new Intent(SignupActivity.this, Rine_home.class);
            startActivity(myIntent);
            finish();
        }
    }
}
