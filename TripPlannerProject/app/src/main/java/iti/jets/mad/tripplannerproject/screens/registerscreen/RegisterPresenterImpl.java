package iti.jets.mad.tripplannerproject.screens.registerscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import iti.jets.mad.tripplannerproject.R;
import iti.jets.mad.tripplannerproject.model.services.SignUpWithFireBase;
import iti.jets.mad.tripplannerproject.model.services.UserSharedPerferences;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;
import iti.jets.mad.tripplannerproject.screens.loginscreen.LoginActivity;


public class RegisterPresenterImpl implements RegisterContract.RegisterPresenter{

    private static final int RC_SIGN_IN =9001 ;
    private RegisterContract.RegisterView registerView;
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth firebaseAuth;
    private Context context;
    private static final String SETTING_INFOS = "User_Info";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "PASSWORD";
    private SharedPreferences settings;
    private SignUpWithFireBase signUpWithFireBase;
    private UserSharedPerferences userSharedPerferences;
    private GoogleSignInClient mGoogleSignInClient;

    public RegisterPresenterImpl(RegisterContract.RegisterView registerView) {
        this.registerView = registerView;
        firebaseAuth=FirebaseAuth.getInstance();
        context= (Context) registerView;
        signUpWithFireBase=new SignUpWithFireBase(context);
    }

    @Override
    public void register( String password,  String email) {
        boolean flag=signUpWithFireBase.register(email,password);
        if(flag==false)
        {
            updateMessage("Registered Successfully");
            toHomeActivity();
            userSharedPerferences=new UserSharedPerferences();
            userSharedPerferences.sharedPreferences(email,password,false,context);

        }
        else
        {
            updateMessage("Registered failed");
        }

    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void toHomeActivity() {

        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public Intent googleTooken() {
       return signUpWithFireBase.googleTooken();
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        signUpWithFireBase.firebaseAuthWithGoogle(account,(Activity)registerView);
    }

    @Override
    public void updateMessage(String message) {
        registerView.showToast(message);
    }

    @Override
    public void activityResult(int requestCode, Intent data) {


    signUpWithFireBase.activityResult(requestCode,data,(Activity)registerView);
    }

    @Override
    public void getSharedPreferences() {
       userSharedPerferences.getSharedPreferences(context);
    }

    @Override
    public boolean validateEmail(String email) {
        if(email.isEmpty())
        {
            updateErrorEmail("Field cannot be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            updateErrorEmail("Please enter a valid email");
            return false;
        }
        else
        {
            updateErrorEmail(null);
            return true;
        }
    }

    @Override
    public void updateErrorEmail(String message) {
        registerView.errorEmail(message);
    }

    @Override
    public boolean validatePassword(String password) {
        if (password.isEmpty()) {
            updateErrorPassword("Field can't be empty");
            return false;
        } else {
            updateErrorPassword(null);
            return true;
        }
    }

    @Override
    public boolean validateUsername(String userName) {
        if (userName.isEmpty()) {
            updateErrorUsername("Field can't be empty");
            return false;
        } else if (userName.length() > 15) {
            updateErrorUsername("Username too long");
            return false;
        } else {
            updateErrorUsername(null);
            return true;
        }
    }

    @Override
    public void updateErrorUsername(String userName) {
        registerView.errorUserName(userName);
    }
    @Override
    public void updateErrorPassword(String message) {
        registerView.errorPassword(message);
    }

}
