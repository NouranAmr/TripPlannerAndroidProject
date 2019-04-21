package iti.jets.mad.tripplannerproject.screens.splashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.widget.LoginButton;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.services.UserSharedPerferences;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;
import iti.jets.mad.tripplannerproject.screens.loginscreen.LoginActivity;
import iti.jets.mad.tripplannerproject.screens.registerscreen.RegisterActivity;


public class SplashActivity extends AppCompatActivity {

    private ImageView splashImageView;
    private static final String SETTING_INFOS = "User_Info";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "PASSWORD";
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
                if (sharedPref.getISLogged_IN(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(intent);

                }
            }
        },7000);


    }
    }


