package iti.jets.mad.tripplannerproject.screens.loginscreen;


public interface LoginContract {
    interface LoginView {
        void showToast(String message);

        void clearTxt();
    }

    interface LoginPresenter {
        void login(String username, String password);
        void toHomeActivity();
        void updateMessage(String message);
        void clearViewTxt();
        void sharedPreferences(String email, String password);
        void getSharedPreferences();

    }
}
