package fr.selquicode.go4lunch.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository {

    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthRepository(FirebaseAuth instance) {
        this.firebaseAuth = instance;
    }

    public FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }

    public boolean isUserLogged(){
        return getCurrentUser() != null;
    }
}
