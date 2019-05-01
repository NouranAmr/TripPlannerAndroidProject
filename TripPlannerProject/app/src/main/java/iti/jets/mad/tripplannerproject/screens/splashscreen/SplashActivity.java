package iti.jets.mad.tripplannerproject.screens.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.services.UserSharedPerferences;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;
import iti.jets.mad.tripplannerproject.screens.loginscreen.LoginActivity;
import iti.jets.mad.tripplannerproject.screens.registerscreen.RegisterActivity;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "ITS SPLASH";
    private ImageView splashImageView;
    private static final String SETTING_INFOS = "User_Info";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "PASSWORD";
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImageView = findViewById(R.id.splashImageView);
        Glide.with(this).asGif().load(R.drawable.travello_splash).into(splashImageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               /* Intent intent=new Intent(SplashActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();*/

                UserSharedPerferences sharedPref;
                sharedPref = UserSharedPerferences.getInstance();
                GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (alreadyloggedAccount != null) {
                    Toast.makeText(getApplicationContext(), "Already Logged In", Toast.LENGTH_SHORT).show();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // ...
                                    //Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();

                                    Log.w(TAG, "Logged In Google");
                                    startActivity(new Intent(getApplicationContext(),HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            });


                }
                else if (sharedPref.getISLogged_IN(SplashActivity.this)) {

                    Log.w(TAG, "Logged In Normal");
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
                else{

                    Log.w(TAG, "Not Logged In");
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }
        },7000);



    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
}


