package iti.jets.mad.tripplannerproject.screens.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.screens.addtripscreen.AddTripActivity;
import iti.jets.mad.tripplannerproject.screens.homescreen.historyfragment.HistoryFragment;
import iti.jets.mad.tripplannerproject.screens.homescreen.homefragment.HomeFragment;
import iti.jets.mad.tripplannerproject.screens.homescreen.profilefragment.ProfileFragment;
import iti.jets.mad.tripplannerproject.screens.registerscreen.RegisterActivity;
import iti.jets.mad.tripplannerproject.screens.splashscreen.SplashActivity;


public class HomeActivity extends AppCompatActivity {
    private MenuItem logoutitem;
    private FloatingActionButton floatingActionButton;
    private GoogleApiClient mGoogleApiClient;

    public HomeActivity() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        logoutitem=menu.findItem(R.id.LogoutToolBarID);

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar=findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navBottomListener);

        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.LogoutToolBarID: {
                FirebaseAuth.getInstance().signOut();
                // LoginManager.getInstance().logOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {

                                mGoogleApiClient.disconnect();
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(), SplashActivity.class);
                                startActivity(i);
                            }
                        });
                finish();
                //startActivity(new Intent(this, RegisterActivity.class).putExtra("flag",true));
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navBottomListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;
                    FragmentManager fragmentManager=null;
                    FragmentTransaction fragmentTransaction=null;
                    switch (menuItem.getItemId()){

                        case R.id.nav_home:
                            selectedFragment=new HomeFragment();
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container,selectedFragment);
                            fragmentTransaction.commit();
                            break;
                        case R.id.nav_profile:
                            selectedFragment=new ProfileFragment();
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container,selectedFragment);
                            fragmentTransaction.commit();
                            break;
                        case R.id.nav_history:
                            selectedFragment=new HistoryFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    }
