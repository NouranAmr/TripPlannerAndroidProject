package iti.jets.mad.tripplannerproject.screens.registerscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Patterns;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import iti.jets.mad.tripplannerproject.model.services.SignUpWithFireBase;
import iti.jets.mad.tripplannerproject.model.services.UserSharedPerferences;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;
import iti.jets.mad.tripplannerproject.screens.loginscreen.LoginActivity;


public class RegisterPresenterImpl implements RegisterContract.RegisterPresenter{

    private static final int RC_SIGN_IN =9001 ;
    private RegisterContract.RegisterView registerView;
    private static final String TAG = "RegisterActivity";

    private Context context;
    private SignUpWithFireBase signUpWithFireBase;

    public RegisterPresenterImpl(RegisterContract.RegisterView registerView) {
        this.registerView = registerView;
        context= (Context) registerView;
        signUpWithFireBase=new SignUpWithFireBase(context);
    }

    @Override
    public void register( String email,  String password) {
        signUpWithFireBase=new SignUpWithFireBase(context);
        boolean flag=signUpWithFireBase.register(email,password);
        if(flag==false)
        {
            updateMessage("Registered Successfully");
            toHomeActivity();
            UserSharedPerferences sharedPref;
            sharedPref = UserSharedPerferences.getInstance();
            sharedPref.saveISLogged_IN(context, true);

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
