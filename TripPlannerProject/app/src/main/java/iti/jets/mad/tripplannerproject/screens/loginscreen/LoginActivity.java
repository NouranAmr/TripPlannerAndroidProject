package iti.jets.mad.tripplannerproject.screens.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.services.UserSharedPerferences;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;
import iti.jets.mad.tripplannerproject.screens.registerscreen.RegisterActivity;


public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView{

    private LoginContract.LoginPresenter loginPresenter;
    private static final String TAG = "firebase execption :";
    private static final int RC_SIGN_IN = 101;
    private Button signin,signUp;
    private TextInputEditText email,password;
    private FirebaseAuth firebaseAuth;
    private SignInButton googleButton;

    private boolean flag = false;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter=new LoginPresenterImpl(this);
        email=findViewById(R.id.emailTxt);
        password=findViewById(R.id.passwordTxt);
        signin=findViewById(R.id.signinBtn);
        signUp=findViewById(R.id.signUpBtn);
        googleButton = findViewById(R.id.googleBtn);
        firebaseAuth= FirebaseAuth.getInstance();
        flag = getIntent().getBooleanExtra("flag", false);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginPresenter.login(email.getText().toString().trim(),password.getText().toString());
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString())
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    UserSharedPerferences sharedPref;
                                    sharedPref = UserSharedPerferences.getInstance();
                                    sharedPref.saveISLogged_IN(LoginActivity.this, true);
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                }
                                else
                                {

                                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                    clearTxt();

                                }
                            }
                        });

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = googleTooken();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearTxt() {
        email.setError("Invalid email or password");
        password.setText("");
        email.setText("");

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //registerPresenter.activityResult(requestCode,data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                String message = e.getMessage();
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private Intent googleTooken() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        return mGoogleSignInClient.getSignInIntent();
    }

    private boolean firebaseAuthWithGoogle(GoogleSignInAccount account) {

        flag = false;
        AuthCredential credential;
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        try {
            credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                // FirebaseUser user = firebaseAuth.getCurrentUser();
                                flag = true;
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                flag = false;
                            }

                        }
                    });
        } catch (Exception e) {
            String mes = e.getMessage();

        } finally {

        }
        return flag;

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
