package iti.jets.mad.tripplannerproject.screens.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.services.UserSharedPerferences;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;
import iti.jets.mad.tripplannerproject.screens.registerscreen.RegisterActivity;


public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView{

    private LoginContract.LoginPresenter loginPresenter;
    private Button signin,signUp;
    private TextInputEditText email,password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter=new LoginPresenterImpl(this);
        email=findViewById(R.id.emailTxt);
        password=findViewById(R.id.passwordTxt);
        signin=findViewById(R.id.signinBtn);
        signUp=findViewById(R.id.signUpBtn);

        firebaseAuth= FirebaseAuth.getInstance();

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
}
