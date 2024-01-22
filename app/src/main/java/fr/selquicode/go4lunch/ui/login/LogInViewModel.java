package fr.selquicode.go4lunch.ui.login;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.CreateUserRequest;

public class LogInViewModel extends ViewModel {

    private final FirebaseAuthRepository firebaseAuthRepository;
    private final FirestoreRepository firestoreRepository;

    public LogInViewModel(FirebaseAuthRepository firebaseAuthRepository, FirestoreRepository  firestoreRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.firestoreRepository = firestoreRepository;
    }


    public FirebaseUser getCurrentUser(){
        return firebaseAuthRepository.getCurrentUser();
    }

    public void createUser(){
        FirebaseUser currentUser = getCurrentUser();
        CreateUserRequest userToCreate = new CreateUserRequest(
                currentUser.getUid(),
                currentUser.getDisplayName()  == null ? "" : currentUser.getDisplayName(),
                currentUser.getPhotoUrl() == null ? null : currentUser.getPhotoUrl().toString()
        );
        firestoreRepository.createUser(userToCreate);
    }

    public boolean isUserLogged(){
        return firebaseAuthRepository.isUserLogged();
    }
}
