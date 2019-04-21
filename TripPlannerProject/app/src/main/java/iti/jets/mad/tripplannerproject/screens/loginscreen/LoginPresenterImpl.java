package iti.jets.mad.tripplannerproject.screens.loginscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.firebase.auth.FirebaseAuth;

import iti.jets.mad.tripplannerproject.model.services.SignInWithFireBase;
import iti.jets.mad.tripplannerproject.model.services.UserSharedPerferences;
import iti.jets.mad.tripplannerproject.screens.homescreen.HomeActivity;
import iti.jets.mad.tripplannerproject.screens.registerscreen.RegisterActivity;


public class LoginPresenterImpl implements LoginContract.LoginPresenter {

    private Context context;
    private LoginContract.LoginView loginView;
    private static final String SETTING_INFOS = "User_Info";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "PASSWORD";
    private SignInWithFireBase signInWithFireBase;

    public LoginPresenterImpl(LoginContract.LoginView loginView) {

        this.loginView = loginView;
        context= (Context) loginView;

    }


    @Override
    public void login( String email,  String password) {
        signInWithFireBase= new SignInWithFireBase(email,password);
        boolean flag=signInWithFireBase.login();
        if(flag==false) {

            updateMessage("sign in Successfully");
            toHomeActivity();
            UserSharedPerferences sharedPref;
            sharedPref = UserSharedPerferences.getInstance();
            sharedPref.saveISLogged_IN(context, true);
            clearViewTxt();

        }
        else {
            updateMessage("sign in Failed");

            }

    }

    @Override
    public void toHomeActivity() {
        Intent intent= new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void updateMessage(String message) {
        loginView.showToast(message);
    }

    @Override
    public void clearViewTxt() {
        loginView.clearTxt();
    }
    @Override
    public void sharedPreferences(String email, String password) {
        SharedPreferences settings = context.getSharedPreferences(SETTING_INFOS, 0);
        settings.edit()
                .putString(EMAIL, email)
                .putString(PASSWORD,password)
                .commit();
    }

    @Override
    public void getSharedPreferences() {
        SharedPreferences settings = context.getSharedPreferences(SETTING_INFOS, 0);
        if(settings.contains(EMAIL))
        {
            String name = settings.getString(EMAIL, "");
            String Password = settings.getString(PASSWORD, "");
            context.startActivity(new Intent(context,HomeActivity.class));
        }
    }
}
