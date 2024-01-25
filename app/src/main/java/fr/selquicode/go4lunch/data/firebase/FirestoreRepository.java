package fr.selquicode.go4lunch.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import fr.selquicode.go4lunch.data.model.CreateUserRequest;
import fr.selquicode.go4lunch.data.model.User;

public class FirestoreRepository {

    private static final String TAG = "firestoreRepo";
    private static final String COLLECTION_NAME_USERS = "users";
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());

    /**
     * Constructor
     * @param firebaseFirestore to access data from Firestore
     */
    public FirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    /**
     * To get user collection in db
     * @return Collection user type CollectionReference
     */
    private CollectionReference getUsersCollection() {
        return firebaseFirestore.collection(COLLECTION_NAME_USERS);
    }


    /**
     * To create a new user from scratch if he's new,
     * or to update a user already known,
     * used when a user try to login
     * @param userRequest which is the info we get from the user logged
     */
    public void createUser(CreateUserRequest userRequest){
        //create a User from the CreateUserRequest
        User userToCreate = new User(
                userRequest.getId(),
                userRequest.getDisplayName(),
                null,
                null,
                userRequest.getPhotoUserUrl(),
                null
        );

        //then
        Task<DocumentSnapshot> userData = this.getUsersCollection().document(userRequest.getId()).get();
        userData.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User user = task.getResult().toObject(User.class);
                    if(user != null){
                        //in case where the user is already known, update data in db
                        //TODO update
                        getUsersCollection().document(userRequest.getId()).update(
                                "displayName", userRequest.getDisplayName(),
                                "photoUserUrl", userRequest.getPhotoUserUrl()
                        );
                    }else{
                        //in case where the user is unknown
                        getUsersCollection().document(userRequest.getId()).set(userToCreate);
                    }
                }

            }
        });

    }

    /**
     * Request to get user's list to display in workmates' fragment
     * @return a list of users type LiveData for UI
     */
    public LiveData<List<User>> getUsers(){
        MutableLiveData<List<User>> usersListMutableLiveData = new MutableLiveData<>();
        this.getUsersCollection()
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        if(error == null && value != null){
                            List<User> usersList = value.toObjects(User.class);
                            Log.i(TAG, usersList.size() + "");
                            usersListMutableLiveData.setValue(usersList);
                        }else{
                            Log.i(TAG, "task.getException");
                        }
                    }
                });
        return usersListMutableLiveData;
    }

    /**
     * Request to get the specific user logged
     * @param userId id of the user currently logged
     * @return user's data
     */
    public LiveData<User> userLogged(String userId){
        MutableLiveData<User> userLoggedDataLD = new MutableLiveData<>();
        this.getUsersCollection().document(userId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        if(error == null && value != null){
                            User userLogged = value.toObject(User.class);
                            Log.i("firestoreRepo", String.valueOf(userLogged));
                            userLoggedDataLD.setValue(userLogged);
                        }else{
                            //TODO deal with error
                            Log.e(TAG,"task.getException in userLogged()");
                        }
                    }
                });
        return userLoggedDataLD;
    }

    /**
     * Request to get a list of user who choose a specific place to eat
     * @param placeId id of the restaurant chosen to eat
     * @return a list of users type LiveData for UI
     */
    public LiveData<List<User>> getUsersWhoChose(String placeId){
        MutableLiveData<List<User>> usersWhoChoseListMLD = new MutableLiveData<>();
        this.getUsersCollection()
                .whereEqualTo("restaurantId", placeId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        if(error == null && value != null){
                            List<User> usersWhoChoseList = value.toObjects(User.class);
                            usersWhoChoseListMLD.setValue(usersWhoChoseList);
                        }else{
                            //TODO : deal with error
                            Log.e(TAG, "task.getException");
                        }

                    }
                });
        return usersWhoChoseListMLD;
    }

    //TODO : updtate user when he choose a place to eat
    // - use id and restaurant name
    // - check if the place chosen is already chosen in db

}
