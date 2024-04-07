package fr.selquicode.go4lunch.data.firebase;

import android.content.Context;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository {

    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthRepository(FirebaseAuth instance) {
        this.firebaseAuth = instance;
    }

    /**
     * To get the currentUser logged
     * @return FirebaseUser
     */
    public FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }

    /**
     * To know if the currentUser is already logged
     * @return boolean
     */
    public boolean isUserLogged(){
        return getCurrentUser() != null;
    }

    /**
     * To logout the user
     * @param context - context af the App
     * @return void type Task
     */
    public Task<Void> signOut(Context context){
        return AuthUI.getInstance().signOut(context);
    }
}
