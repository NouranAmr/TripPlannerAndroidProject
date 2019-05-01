package iti.jets.mad.tripplannerproject.screens.registerscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.services.UserSharedPerferences;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;


public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView {

    private static final int RC_SIGN_IN = 101;
    private static final String TAG = "firebase execption :";
    private Button signup, signin;
    private EditText nametxt, emailtxt, passtxt, repasstxt;
    private RegisterPresenterImpl registerPresenter;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenterImpl(this);
        signin = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.btn_signUp);
        nametxt = findViewById(R.id.usernameTxt);
        emailtxt = findViewById(R.id.emailTxt);
        passtxt = findViewById(R.id.passwordTxt);
        repasstxt = findViewById(R.id.repasswordTxt);
        firebaseAuth = FirebaseAuth.getInstance();
        // registerPresenter.sharedPreferences(emailtxt.getText().toString(),passtxt.getText().toString(),flag);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.toLoginActivity();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nametxt.getText().toString();
                final String password = passtxt.getText().toString();
                String repassword = repasstxt.getText().toString();
                String email = emailtxt.getText().toString().trim();

                if (password.equals(repassword) && registerPresenter.validateEmail(email) && registerPresenter.validatePassword(password) && registerPresenter.validateUsername(name)) {

                    // registerPresenter.register(email, password);
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                                           if (task.isSuccessful()) {
                                                               Toast.makeText(RegisterActivity.this, "Registerd Suceesfully", Toast.LENGTH_SHORT).show();
                                                               UserSharedPerferences sharedPref;
                                                               sharedPref = UserSharedPerferences.getInstance();
                                                               sharedPref.saveISLogged_IN(RegisterActivity.this, true);
                                                               startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                                           } else {
                                                               Toast.makeText(RegisterActivity.this, "Registerd failed", Toast.LENGTH_SHORT).show();
                                                               startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                                                               passtxt.setText("");
                                                               repasstxt.setText("");
                                                               nametxt.setText("");
                                                               emailtxt.setText("");
                                                           }

                                                       }
                                                   }
                            );


                } else if (!password.equals(repassword)) {
                    repasstxt.setError("Password Not Match");
                }
            }
        });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorEmail(String message) {
        emailtxt.setError(message);
    }

    @Override
    public void errorPassword(String message) {
        passtxt.setError(message);
    }

    @Override
    public void errorUserName(String message) {
        nametxt.setError(message);
    }

    @Override
    protected void onStart() {
       /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();*/
        super.onStart();


    }
}
