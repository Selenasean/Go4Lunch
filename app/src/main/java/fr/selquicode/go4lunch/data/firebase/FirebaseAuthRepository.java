package fr.selquicode.go4lunch.data.firebase;

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
}
