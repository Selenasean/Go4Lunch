package fr.selquicode.go4lunch.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import fr.selquicode.go4lunch.data.model.CreateUserRequest;
import fr.selquicode.go4lunch.data.model.User;

public class FirestoreRepository {

    private static final String TAG = "firestoreRepo";
    private static final String COLLECTION_NAME_USERS = "users";
    private static final String FAVORITE_PLACE = "favoritePlacesId";
    private static final String RESTAURANT_TO_EAT = "restaurantId";
    private static final String RESTAURANT_NAME = "restaurantName";
    private static final String RESTAURANT_ADDRESS = "restaurantAddress";
    private static final String USER_NAME = "displayName";
    private static final String USER_PICTURE = "photoUserUrl";
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());

    /**
     * Constructor
     *
     * @param firebaseFirestore to access data from Firestore
     */
    public FirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    /**
     * To get user collection in db
     *
     * @return Collection user type CollectionReference
     */
    private CollectionReference getUsersCollection() {
        return firebaseFirestore.collection(COLLECTION_NAME_USERS);
    }


    /**
     * To create a new user from scratch if he's new,
     * or to update a user already known,
     * used when a user try to login
     *
     * @param userRequest which is the info we get from the user logged
     */
    public void createUser(CreateUserRequest userRequest) {
        //create a User from the CreateUserRequest
        User userToCreate = new User(
                userRequest.getId(),
                userRequest.getDisplayName(),
                userRequest.getEmail(),
                null,
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
                if (task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    if (user != null) {
                        //in case where the user is already known, update data in db
                        getUsersCollection().document(userRequest.getId()).update(
                                USER_NAME, userRequest.getDisplayName(),
                                USER_PICTURE, userRequest.getPhotoUserUrl()
                        );
                    } else {
                        //in case where the user is unknown
                        getUsersCollection().document(userRequest.getId()).set(userToCreate);
                    }
                }

            }
        });

    }

    /**
     * Request to get user's list to display in workmates' fragment
     *
     * @return a list of users type LiveData for UI
     */
    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> usersListMutableLiveData = new MutableLiveData<>();
        this.getUsersCollection()
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        if (error == null && value != null) {
                            List<User> usersList = value.toObjects(User.class);
                            Log.i(TAG, usersList.size() + "");
                            usersListMutableLiveData.setValue(usersList);
                        } else {
                            Log.i(TAG, "task.getException");
                        }
                    }
                });
        return usersListMutableLiveData;
    }

    /**
     * Request to get the specific user logged
     *
     * @param userId id of the user currently logged
     * @return user's data
     */
    public LiveData<User> userLogged(String userId) {
        MutableLiveData<User> userLoggedDataLD = new MutableLiveData<>();
        this.getUsersCollection().document(userId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        if (error == null && value != null) {
                            User userLogged = value.toObject(User.class);
                            userLoggedDataLD.setValue(userLogged);
                            Log.i("repofirestore", String.valueOf(
                                    userLogged
                            ));
                        } else {
                            //TODO deal with error
                            Log.e(TAG, "task.getException in userLogged()");
                        }
                    }
                });
        return userLoggedDataLD;
    }

    /**
     * Request to get a list of user who choose a specific place to eat
     *
     * @param placeId id of the restaurant chosen to eat
     * @return a list of users type LiveData for UI
     */
    public LiveData<List<User>> getUsersWhoChose(String placeId) {
        MutableLiveData<List<User>> usersWhoChoseListMLD = new MutableLiveData<>();
        this.getUsersCollection()
                .whereEqualTo(RESTAURANT_TO_EAT, placeId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        if (error == null && value != null) {
                            List<User> usersWhoChoseList = value.toObjects(User.class);
                            usersWhoChoseListMLD.setValue(usersWhoChoseList);
                        } else {
                            //TODO : deal with error
                            Log.e(TAG, "task.getException in getUsersWhaChose()");
                        }

                    }
                });
        return usersWhoChoseListMLD;
    }

    /**
     * Request to update a restaurant chosen by user logged
     *
     * @param userId         user id logged
     * @param placeId        restaurant id clicked by user logged
     * @param restaurantName name of the restaurant clicked
     */
    public void updateRestaurantChosen(String userId, String placeId, String restaurantName, String restaurantAddress) {
        this.getUsersCollection().document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            if (user != null) {
                                if (user.getRestaurantId() == null || !Objects.equals(user.getRestaurantId(), placeId)) {
                                    //means that the user has never chose a restaurant : set a value
                                    //or that the user has clicked on a different restaurant from the one he chose
                                    getUsersCollection().document(userId).update(
                                            RESTAURANT_TO_EAT, placeId,
                                            RESTAURANT_NAME, restaurantName,
                                            RESTAURANT_ADDRESS, restaurantAddress
                                    );
                                }
                                if (Objects.equals(user.getRestaurantId(), placeId) && user.getRestaurantId() != null) {
                                    //means that the user has already chosen this particular restaurant : set values to null
                                    getUsersCollection().document(userId).update(
                                            RESTAURANT_TO_EAT, null,
                                            RESTAURANT_NAME, null,
                                            RESTAURANT_ADDRESS, null
                                    );
                                }

                            } else {
                                //means user maybe == null
                                // TODO : deal with error if task == null
                                Log.e(TAG, "task.getException in updateRestaurantChosen");
                            }

                        }
                    }
                });
    }


    /**
     * Request to add a restaurant to the favorite list
     *
     * @param userId       String of the user id
     * @param restaurantId String of the current place id
     */
    public void addToFavoriteList(String userId, String restaurantId) {
        this.getUsersCollection().document(userId).update(
                FAVORITE_PLACE,
                FieldValue.arrayUnion(restaurantId));
    }

    /**
     * Request to remove a restaurant from the favorite list
     *
     * @param userId       String of iid user
     * @param restaurantId String of the current place id
     */
    public void removeFromFavoriteList(String userId, String restaurantId) {
        Log.i("reporemove", "remove");
        this.getUsersCollection().document(userId).update(
                FAVORITE_PLACE,
                FieldValue.arrayRemove(restaurantId));
    }

    @WorkerThread
    public User getUserLoggedSynchronously(String userLoggedId) throws ExecutionException, InterruptedException {
            Task<DocumentSnapshot> task = this.getUsersCollection().document(userLoggedId).get();
            DocumentSnapshot result = Tasks.await(task);
            return  result.toObject(User.class);
    }

    @WorkerThread
    public List<User> getUsersWhoChooseSynchronously(String placeId) throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = this.getUsersCollection().whereEqualTo(RESTAURANT_TO_EAT, placeId).get();
        QuerySnapshot result = Tasks.await(task);
        return result.toObjects(User.class);
    }
}
