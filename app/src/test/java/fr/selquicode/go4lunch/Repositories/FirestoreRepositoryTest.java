package fr.selquicode.go4lunch.Repositories;

import static com.google.common.base.Verify.verify;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.utils.LiveDataTestUtils;

@RunWith(JUnit4.class)
public class FirestoreRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final FirebaseFirestore firestoreMock = mock();
    private final CollectionReference usersCollectionMock = mock();
    private final QuerySnapshot querySnapshotMock = mock();
    private final Task<DocumentSnapshot> documentSnapshotMock = mock();

    private final String USER_COLLECTION_NAME = "users";

    private final FirestoreRepository firestoreRepository = new FirestoreRepository(firestoreMock);

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
    public void setup(){
        dummyUsersList.add(firstUser);
        dummyUsersList.add(secondUser);
    }

    @Test
    public void getUsers() throws InterruptedException {
        when(firestoreMock.collection(USER_COLLECTION_NAME)).thenReturn(usersCollectionMock);

        List<User> users = dummyUsersList;
        when(querySnapshotMock.toObjects(User.class)).thenReturn(users);

        LiveData<List<User>> liveDataUsers = firestoreRepository.getUsers();

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
    public void createUser(){
        //create user
        //then check if user exist

        //when call firestore.collection return collectionMock
        when(firestoreMock.collection(USER_COLLECTION_NAME).document(anyString())).thenReturn(usersCollectionMock.document(anyString()));

        User userToCreate = dummyUsersList.get(0);
        when(documentSnapshotMock.getResult().toObject(User.class)).thenReturn(userToCreate);
        




    }

    @Test
    public void getOneUser(){}

    @Test
    public void getUserWhoChoose(){}

    @Test
    public void updateRestaurantChosen(){}

    @Test
    public void addToFavoriteList(){}

    @Test
    public void removeFromFavoriteList(){}

    // CHAT PART //

    @Test
    public void getAllMessage(){}

    @Test
    public void createMessageForChat(){}
}
