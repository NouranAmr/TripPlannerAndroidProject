package iti.jets.mad.tripplannerproject.screens.registerscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;

import iti.jets.mad.tripplannerproject.R;


public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView{

    private static final int RC_SIGN_IN =9001 ;
    private Button signup,signin;
    private EditText nametxt , emailtxt, passtxt,repasstxt;
    private RegisterPresenterImpl registerPresenter;
    private SignInButton googleButton;
    private boolean flag=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter =new RegisterPresenterImpl(this);
        signin=findViewById(R.id.loginBtn);
        signup=findViewById(R.id.btn_signUp);
        nametxt=findViewById(R.id.usernameTxt);
        emailtxt=findViewById(R.id.emailTxt);
        passtxt=findViewById(R.id.passwordTxt);
        repasstxt=findViewById(R.id.repasswordTxt);
        googleButton=findViewById(R.id.googleBtn);
        flag=getIntent().getBooleanExtra("flag",false);
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
                String name=nametxt.getText().toString();
                String password=passtxt.getText().toString();
                String repassword=repasstxt.getText().toString();
                String email=emailtxt.getText().toString().trim();
                if(password.equals(repassword) && registerPresenter.validateEmail(email)&&registerPresenter.validatePassword(password)&&registerPresenter.validateUsername(name)) {

                    registerPresenter.register(password, email);

                }
                else if(!password.equals(repassword))
                {
                    repasstxt.setError("Password Not Match");
                }
            }
        });
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent= registerPresenter.googleTooken();
                startActivityForResult(signInIntent, RC_SIGN_IN);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        registerPresenter.activityResult(requestCode,data);

    }
}
