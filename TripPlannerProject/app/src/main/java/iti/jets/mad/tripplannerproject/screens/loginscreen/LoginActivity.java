package iti.jets.mad.tripplannerproject.screens.loginscreen;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iti.jets.mad.tripplannerproject.R;


public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView{

    private LoginContract.LoginPresenter loginPresenter;
    private Button signin;
    private TextInputEditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter=new LoginPresenterImpl(this);
        email=findViewById(R.id.emailTxt);
        password=findViewById(R.id.passwordTxt);
        signin=findViewById(R.id.signinBtn);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(email.getText().toString().trim(),password.getText().toString());

            }
        });


    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearTxt() {
        password.setText("");
        email.setText("");

    }
}
