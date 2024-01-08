package fr.selquicode.go4lunch.data.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.selquicode.go4lunch.data.model.CreateUserRequest;
import fr.selquicode.go4lunch.data.model.User;

public class FirestoreRepository {

    private static final String COLLECTION_NAME = "users";
    private final FirebaseFirestore firebaseFirestore;

    public FirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    public void createUser(CreateUserRequest userRequest){
        //in case of the user is new
        User user = new User(
                userRequest.getId(),
                userRequest.getDisplayName(),
                null,
                null,
                userRequest.getPhotoUserUrl(),
                null
        );
        this.getUsersCollection().document(user.getId()).set(user);

        //TODO : in case of the user is already known
    }

    private CollectionReference getUsersCollection() {
        return firebaseFirestore.collection(COLLECTION_NAME);
    }

    //TODO : request to get user's list to display in workmates'fragment

    //TODO : updtate user when he choose a place to eat
    // - use id and restaurant name
    // - check if the place chosen is already chosen in db

}
