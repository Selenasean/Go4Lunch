package fr.selquicode.go4lunch.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.CreateUserRequest;

public class LogInViewModel extends ViewModel {

    @NonNull
    private final FirebaseAuthRepository firebaseAuthRepository;
    @NonNull
    private final FirestoreRepository firestoreRepository;

    public LogInViewModel(@NonNull FirebaseAuthRepository firebaseAuthRepository,@NonNull FirestoreRepository  firestoreRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.firestoreRepository = firestoreRepository;
    }

    /**
     * To get current user logged from FirebaseAut
     * @return a FirebaseUser
     */
    public FirebaseUser getCurrentUser(){
        return firebaseAuthRepository.getCurrentUser();
    }

    /**
     * Create a user from the user currently logged in FireStore
     */
    public void createUser(){
        FirebaseUser currentUser = getCurrentUser();
        CreateUserRequest userToCreate = new CreateUserRequest(
                currentUser.getUid(),
                currentUser.getDisplayName()  == null ? "" : currentUser.getDisplayName(),
                Objects.requireNonNull(currentUser.getEmail()),
                currentUser.getPhotoUrl() == null ? null : currentUser.getPhotoUrl().toString()
        );
        firestoreRepository.createUser(userToCreate);
    }

    /**
     * To know if user register is logged
     * @return boolean
     */
    public boolean isUserLogged(){
        return firebaseAuthRepository.isUserLogged();
    }
}
