package iti.jets.mad.tripplannerproject.screens.registerscreen;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface RegisterContract {
    interface RegisterView {
        void showToast(String message);
        void errorEmail(String message);
        void errorPassword(String message);
        void errorUserName(String message);
    }

    interface RegisterPresenter {
        void register(String password, String email);
        void toLoginActivity();
        void toHomeActivity();
        Intent googleTooken();
        void firebaseAuthWithGoogle(GoogleSignInAccount account);
        void updateMessage(String message);
        void activityResult(int requestCode, Intent data);
        void getSharedPreferences();
        boolean validateEmail(String email);
        void updateErrorEmail(String message);
        boolean validatePassword(String password);
        void updateErrorPassword(String message);
        boolean validateUsername(String userName);
        void updateErrorUsername(String userName);
    }
}
