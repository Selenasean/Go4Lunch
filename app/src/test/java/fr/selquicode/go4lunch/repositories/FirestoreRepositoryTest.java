package fr.selquicode.go4lunch.repositories;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoFramework;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.CreateMessageRequest;
import fr.selquicode.go4lunch.data.model.CreateUserRequest;
import fr.selquicode.go4lunch.data.model.Message;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.data.model.UserSender;
import fr.selquicode.go4lunch.utils.LiveDataTestUtils;

@RunWith(JUnit4.class)
public class FirestoreRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final FirebaseFirestore firestoreMock = mock();
    private final CollectionReference usersCollectionMock = mock();
    private final QuerySnapshot querySnapshotMock = mock();
    private final DocumentSnapshot documentSnapshotMock = mock();
    private final Task<DocumentSnapshot> taskDocumentSnapshotMock = mock();
    private final DocumentReference documentReferenceMock = mock();
    private final Query queryMock = mock();
    private final CollectionReference chatCollectionMock = mock();
    private final CollectionReference messageCollectionMock = mock();

    private final String COLLECTION_MESSAGE = "messages";
    private final String CHAT_COLLECTION_NAME = "chats";
    private final String USER_COLLECTION_NAME = "users";
    private final String RESTAURANT_TO_EAT = "restaurantId";
    private final String USER_NAME = "displayName";
    private final String USER_PICTURE = "photoUserUrl";
    private final String RESTAURANT_NAME = "restaurantName";
    private final String RESTAURANT_ADDRESS = "restaurantAddress";
    private final String FAVORITE_PLACE = "favoritePlacesId";

    private final FirestoreRepository firestoreRepository = new FirestoreRepository(firestoreMock);

    //DATA FOR TEST
    private final List<User> dummyUsersList = new ArrayList<>();
    private final User firstUser = new User(
            "id1",
            "name1",
            "email1@email.com",
            null,
            null,
            null,
            "photoUser1",
            null
    );

    private final User secondUser = new User(
            "id2",
            "name2",
            "email2@email.com",
            "idRestaurant",
            "restaurant",
            "restaurant address",
            "photoUser2",
            null
    );


    @Before
    public void setup() {
        dummyUsersList.add(firstUser);
        dummyUsersList.add(secondUser);

        when(firestoreMock.collection(USER_COLLECTION_NAME)).thenReturn(usersCollectionMock);
    }

    //TEST

    @Test
    public void getUsers() throws InterruptedException {
        List<User> users = dummyUsersList;
        when(querySnapshotMock.toObjects(User.class)).thenReturn(users);

        //method to test
        LiveData<List<User>> liveDataUsers = firestoreRepository.getUsers();

        //get the callback
        ArgumentCaptor<EventListener<QuerySnapshot>> argumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(usersCollectionMock).addSnapshotListener(argumentCaptor.capture());
        EventListener<QuerySnapshot> eventListener = argumentCaptor.getValue();
        eventListener.onEvent(querySnapshotMock, null);

        List<User> actualUsers = LiveDataTestUtils.getOrAwaitValue(liveDataUsers);

        Mockito.verify(firestoreMock).collection(USER_COLLECTION_NAME);
        verifyNoMoreInteractions(firestoreMock);
        assertThat(actualUsers).isEqualTo(users);
    }

    @Test
    public void createUserIfAlreadyExist() {
        //mock all elements from firestore
        when(usersCollectionMock.document("id1")).thenReturn(documentReferenceMock);
        when(documentReferenceMock.get()).thenReturn(taskDocumentSnapshotMock);
        when(taskDocumentSnapshotMock.getResult()).thenReturn(documentSnapshotMock);
        when(documentSnapshotMock.toObject(User.class)).thenReturn(firstUser);
        when(taskDocumentSnapshotMock.isSuccessful()).thenReturn(true);

        //create user in db, so he already exist when we want to update
        firestoreRepository.createUser(new CreateUserRequest(
                "id1",
                "name33",
                "email1@email.com",
                "photoUser33"

        ));

        //get callback
        ArgumentCaptor<OnCompleteListener<DocumentSnapshot>> argumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(taskDocumentSnapshotMock).addOnCompleteListener(argumentCaptor.capture());
        OnCompleteListener<DocumentSnapshot> onCompleteListener = argumentCaptor.getValue();
        onCompleteListener.onComplete(taskDocumentSnapshotMock);

        Mockito.verify(documentReferenceMock).update(
                USER_NAME, "name33",
                USER_PICTURE, "photoUser33"
        );

    }

    @Test
    public void createUserIfDoesNotExist() throws InterruptedException {
        //mock elements from firestore
        when(usersCollectionMock.document("id3")).thenReturn(documentReferenceMock);
        when(documentReferenceMock.get()).thenReturn(taskDocumentSnapshotMock);
        when(taskDocumentSnapshotMock.getResult()).thenReturn(documentSnapshotMock);
        when(documentSnapshotMock.toObject(User.class)).thenReturn(null);
        when(taskDocumentSnapshotMock.isSuccessful()).thenReturn(true);
        //create a user
        firestoreRepository.createUser(new CreateUserRequest(
                "id3",
                "name3",
                "email3@email.com",
                "photoUser3"

        ));
        //get the callback
        ArgumentCaptor<OnCompleteListener<DocumentSnapshot>> argumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(taskDocumentSnapshotMock).addOnCompleteListener(argumentCaptor.capture());
        OnCompleteListener<DocumentSnapshot> onCompleteListener = argumentCaptor.getValue();
        onCompleteListener.onComplete(taskDocumentSnapshotMock);
        //user created
        User userToCreate = new User(
                "id3",
                "name3",
                "email3@email.com",
                null,
                null,
                null,
                "photoUser3",
                null
        );

        Mockito.verify(documentReferenceMock).set(userToCreate);
    }


    @Test
    public void getOneUser() throws InterruptedException {
        when(usersCollectionMock.document(firstUser.getId())).thenReturn(documentReferenceMock);
        when(documentSnapshotMock.toObject(User.class)).thenReturn(firstUser);

        //method to test
        LiveData<User> userLiveData = firestoreRepository.getOneUser("id1");

        //get the callback
        ArgumentCaptor<EventListener<DocumentSnapshot>> argumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(documentReferenceMock).addSnapshotListener(argumentCaptor.capture());
        EventListener<DocumentSnapshot> eventListener = argumentCaptor.getValue();
        eventListener.onEvent(documentSnapshotMock, null);

        User userExpected = LiveDataTestUtils.getOrAwaitValue(userLiveData);

        Mockito.verify(firestoreMock).collection(USER_COLLECTION_NAME);
        verifyNoMoreInteractions(firestoreMock);

        assertThat(userExpected.getId()).isEqualTo(firstUser.getId());
    }

    @Test
    public void getUsersWhoChoose() throws InterruptedException {
        //create user who chose a restaurant
        List<User> usersWhoChoose = new ArrayList<>();
        usersWhoChoose.add(secondUser);
        usersWhoChoose.add(new User(
                "id4",
                "name4",
                "email4@email.com",
                "idRestaurant",
                "restaurant",
                "restaurant address",
                "photoUser4",
                null
        ));

        when(querySnapshotMock.toObjects(User.class)).thenReturn(usersWhoChoose);
        when(usersCollectionMock.whereEqualTo(RESTAURANT_TO_EAT, "idRestaurant")).thenReturn(queryMock);

        //method to test
        LiveData<List<User>> liveDataUsersList = firestoreRepository.getUsersWhoChose("idRestaurant");

        //get the callback
        ArgumentCaptor<EventListener<QuerySnapshot>> argumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(queryMock).addSnapshotListener(argumentCaptor.capture());
        EventListener<QuerySnapshot> eventListener = argumentCaptor.getValue();
        eventListener.onEvent(querySnapshotMock, null);

        List<User> usersExpected = LiveDataTestUtils.getOrAwaitValue(liveDataUsersList);

        Mockito.verify(firestoreMock).collection(USER_COLLECTION_NAME);
        Mockito.verifyNoMoreInteractions(firestoreMock);
        assertThat(usersExpected).isEqualTo(usersWhoChoose);
        assertThat(usersExpected.get(0).getRestaurantId()).isEqualTo(usersWhoChoose.get(0).getRestaurantId());
    }

    @Test
    public void updateRestaurantChosenWhenUserNeverChoose() {
        //mock all elements from firestore
        when(usersCollectionMock.document("id1")).thenReturn(documentReferenceMock);
        when(documentReferenceMock.get()).thenReturn(taskDocumentSnapshotMock);
        when(taskDocumentSnapshotMock.getResult()).thenReturn(documentSnapshotMock);
        //user who doesn't choose a restaurant
        when(documentSnapshotMock.toObject(User.class)).thenReturn(firstUser);
        when(taskDocumentSnapshotMock.isSuccessful()).thenReturn(true);

        //method to verify
        firestoreRepository.updateRestaurantChosen(
                "id1",
                "restaurantId",
                "restaurant",
                "addressRestaurant"
        );

        //get the callback
        ArgumentCaptor<OnCompleteListener<DocumentSnapshot>> argumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(taskDocumentSnapshotMock).addOnCompleteListener(argumentCaptor.capture());
        OnCompleteListener<DocumentSnapshot> onCompleteListener = argumentCaptor.getValue();
        onCompleteListener.onComplete(taskDocumentSnapshotMock);

        Mockito.verify(documentReferenceMock).update(
                RESTAURANT_TO_EAT, "restaurantId",
                RESTAURANT_NAME, "restaurant",
                RESTAURANT_ADDRESS, "addressRestaurant"
        );
    }

    @Test
    public void updateRestaurantChosenWhenUserAlreadyChoose() {
        //mock all elements from firestore
        when(usersCollectionMock.document("id2")).thenReturn(documentReferenceMock);
        when(documentReferenceMock.get()).thenReturn(taskDocumentSnapshotMock);
        when(taskDocumentSnapshotMock.getResult()).thenReturn(documentSnapshotMock);
        //user who had choose a restaurant
        when(documentSnapshotMock.toObject(User.class)).thenReturn(secondUser);
        when(taskDocumentSnapshotMock.isSuccessful()).thenReturn(true);

        //method to verify
        firestoreRepository.updateRestaurantChosen(
                "id2",
                "idRestaurant",
                "restaurant",
                "restaurant address"
        );

        //get the callback
        ArgumentCaptor<OnCompleteListener<DocumentSnapshot>> argumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(taskDocumentSnapshotMock).addOnCompleteListener(argumentCaptor.capture());
        OnCompleteListener<DocumentSnapshot> onCompleteListener = argumentCaptor.getValue();
        onCompleteListener.onComplete(taskDocumentSnapshotMock);

        Mockito.verify(documentReferenceMock).update(
                RESTAURANT_TO_EAT, null,
                RESTAURANT_NAME, null,
                RESTAURANT_ADDRESS, null
        );
    }

    @Test
    public void addToFavoriteList() {
        String RESTAURANT_FAV_ID = "restaurantFavoriteId";

        //mock elements from firestore
        when(usersCollectionMock.document("id7")).thenReturn(documentReferenceMock);

        //method to test
        firestoreRepository.addToFavoriteList("id7", RESTAURANT_FAV_ID);

        Mockito.verify(documentReferenceMock).update(
                eq(FAVORITE_PLACE), any()
        );

    }

    // CHAT PART //
    @Test
    public void createMessageForChat() {
        //mock elements from firebase
        when(usersCollectionMock.document("id1")).thenReturn(documentReferenceMock);
        when(documentReferenceMock.get()).thenReturn(taskDocumentSnapshotMock);
        when(taskDocumentSnapshotMock.getResult()).thenReturn(documentSnapshotMock);
        when(taskDocumentSnapshotMock.isSuccessful()).thenReturn(true);
        //user sender
        when(documentSnapshotMock.toObject(User.class)).thenReturn(firstUser);

        //mock chat collection & message collection from firestore
        when(firestoreMock.collection(CHAT_COLLECTION_NAME)).thenReturn(chatCollectionMock);
        when(chatCollectionMock.document("id2_id1")).thenReturn(documentReferenceMock);
        when(documentReferenceMock.collection(COLLECTION_MESSAGE)).thenReturn(messageCollectionMock);

        //method to verify
        LocalDateTime dateTime = LocalDateTime.of(2024, 4, 26, 18, 0);
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        firestoreRepository.createMessageForChat(
                new CreateMessageRequest("message", new Timestamp(date)),
                "id1",
                "id2"
        );

        //get the callback
        ArgumentCaptor<OnCompleteListener<DocumentSnapshot>> argumentCaptor = ArgumentCaptor.captor();
        verify(taskDocumentSnapshotMock).addOnCompleteListener(argumentCaptor.capture());
        OnCompleteListener<DocumentSnapshot> listener = argumentCaptor.getValue();
        listener.onComplete(taskDocumentSnapshotMock);

        //create a UserSender
        UserSender userSender = new UserSender(
                firstUser.getId(),
                firstUser.getDisplayName(),
                firstUser.getPhotoUserUrl() != null ? firstUser.getPhotoUserUrl() : ""
        );

        verify(messageCollectionMock).add(
                new Message(
                        "message",
                        new Timestamp(date),
                        userSender
                )
        );
    }
}
