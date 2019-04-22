package iti.jets.mad.tripplannerproject.model.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

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

public class SignUpWithFireBase {

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "SignUpWithFireBase";
    private Context context;
    private boolean flag=false;
    private static final int RC_SIGN_IN =101 ;

    public SignUpWithFireBase(Context context) {
        firebaseAuth=FirebaseAuth.getInstance();
        this.context=context;

    }
    public boolean register(String email,String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                               if(task.isSuccessful())
                                               {
                                                  flag=true;
                                                   Log.i(TAG,task.getResult().toString());
                                               }
                                               else
                                               {
                                                  flag=false;
                                                   Log.i(TAG,task.getResult().toString());
                                               }

                                           }
                                       }
                );
        return flag;
    }
    public Intent googleTooken() {
        /*
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken()
                .requestEmail()
                .build();
         */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        
        firebaseAuth = FirebaseAuth.getInstance();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        return signInIntent;
    }
    public boolean firebaseAuthWithGoogle(GoogleSignInAccount account , Activity registerView) {

        flag=false;
        AuthCredential credential;
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
       try {
            credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
           firebaseAuth.signInWithCredential(credential)
                   .addOnCompleteListener(registerView, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information

                               FirebaseUser user = firebaseAuth.getCurrentUser();
                               flag = true;

                           } else {
                               // If sign in fails, display a message to the user.
                               flag = false;
                           }

                       }
                   });
       }
       catch (Exception e) {
           String mes = e.getMessage();

       }finally {

       }
       return flag;

    }
    public void activityResult(int requestCode, Intent data , Activity registerView ) {


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account,registerView);
            } catch (ApiException e) {
                String message= e.getMessage();
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

}
