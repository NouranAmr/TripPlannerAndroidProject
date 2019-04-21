package iti.jets.mad.tripplannerproject.model.services;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInWithFireBase {
    private FirebaseAuth firebaseAuth;
    private boolean flag=false;
    private String email,password;

    public SignInWithFireBase(String email,String password) {

        this.email=email;
        this.password=password;
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public boolean login() {
        firebaseAuth.signInWithEmailAndPassword(this.email,this.password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            flag=true;

                        }
                        else
                        {
                            flag=false;

                        }
                    }
                });
        return flag;
    }
}
