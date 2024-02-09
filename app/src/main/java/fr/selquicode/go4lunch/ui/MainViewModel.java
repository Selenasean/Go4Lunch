package fr.selquicode.go4lunch.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;

public class MainViewModel extends ViewModel {


    @NonNull
    private final PermissionChecker permissionChecker;

    @NonNull
    private final LocationRepository locationRepository;
    private final FirestoreRepository firestoreRepository;
    private final FirebaseAuthRepository firebaseAuthRepository;
    private String userLoggedId;


    public MainViewModel(
            @NonNull PermissionChecker permissionChecker,
            @NonNull LocationRepository locationRepository,
            FirestoreRepository firestoreRepository,
            FirebaseAuthRepository firebaseAuthRepository) {

        this.permissionChecker = permissionChecker;
        this.locationRepository = locationRepository;
        this.firestoreRepository =firestoreRepository;
        this.firebaseAuthRepository = firebaseAuthRepository;

        userLoggedId = firebaseAuthRepository.getCurrentUser().getUid();
    }

    /**
     * Method to start or stop the location's request according the user choice
     */
    @SuppressLint("MissingPermission")
    public void refresh() {
        boolean hasGpsPermission = permissionChecker.hasLocationPermission();
        if (hasGpsPermission) {
            locationRepository.startLocationRequest();
        } else {
            locationRepository.stopLocationRequest();
        }
    }

    /**
     * Method to logout the user currently logged
     * @param context the context of the UI
     * @return a Task type Void
     */
    public Task<Void> signOut(Context context) {
        return firebaseAuthRepository.signOut(context);
    }

    public LiveData<User> getUserLogged(){
        return firestoreRepository.userLogged(userLoggedId);
    }
}
