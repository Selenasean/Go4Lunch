package fr.selquicode.go4lunch.ui.workmates;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.ui.utils.WorkmatesComparator;

public class WorkmatesViewModel extends ViewModel {

    private final FirestoreRepository firestoreRepository;
    private final FirebaseAuthRepository firebaseAuthRepository;

    /**
     * Constructor
     * @param firestoreRepository to get data from Firestore
     * @param firebaseAuthRepository to get the
     */
    public WorkmatesViewModel(FirestoreRepository firestoreRepository, FirebaseAuthRepository firebaseAuthRepository) {
        this.firestoreRepository = firestoreRepository;
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    /**
     * Method to get all users
     * @return a list of WorkmatesViewState type Livedata for the UI
     */
    public LiveData<List<WorkmatesViewState>> getUsers() {
        LiveData<List<User>> usersList = firestoreRepository.getUsers();
        return Transformations.map(usersList, this::parseToViewState);
    }

    /**
     * Method that parse a List of User into a List of WorkmatesViewState,
     * the user currently logged is filtered from the list
     * @param users  a list of users
     * @return a list of WorkmatesViewState
     */
    private List<WorkmatesViewState> parseToViewState(List<User> users) {
        List<WorkmatesViewState> usersList = users.stream()
                .filter(user -> !user.getId().equals(firebaseAuthRepository.getCurrentUser().getUid()))
                .map(user ->
                        new WorkmatesViewState(
                                user.getId(),
                                user.getDisplayName().contains(" ") ?
                                        user.getDisplayName().split(" ")[0] : user.getDisplayName(),
                                user.getPhotoUserUrl(),
                                user.getRestaurantName(),
                                user.getRestaurantId())
                )
                .sorted(new WorkmatesComparator.ChoiceComparator())
                .collect(Collectors.toList());
        return usersList;
    }


}