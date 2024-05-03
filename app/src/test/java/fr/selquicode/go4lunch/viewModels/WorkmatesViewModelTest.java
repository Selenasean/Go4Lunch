package fr.selquicode.go4lunch.viewModels;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.truth.Truth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewModel;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewState;
import fr.selquicode.go4lunch.utils.LiveDataTestUtils;

public class WorkmatesViewModelTest {

    private final FirestoreRepository firestoreRepositoryMock = mock();
    private final FirebaseAuthRepository firebaseAuthRepositoryMock = mock();
    private final FirebaseUser firebaseUserMock = mock();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    //DATA FOR TEST
    private List<User> usersList;
    private WorkmatesViewModel viewModel;

    @Before
    public void setup(){
        //mock of firebaseAuthRepository & getting user currently logged
        User userLogged = new User(
                "id1",
                "name1",
                "email1@email.com",
                null,
                null,
                null,
                "photoUser1",
                null);
        when(firebaseAuthRepositoryMock.getCurrentUser()).thenReturn(firebaseUserMock);
        when(firebaseUserMock.getUid()).thenReturn(userLogged.getId());

        //mock of List<User> from firestoreRepository
        usersList = Arrays.asList(
                userLogged,
                new User(
                        "id2",
                        "2name name",
                        "email2@email.com",
                        null,
                        null,
                        null,
                        "photoUser2",
                        null),
                new User(
                        "id3",
                        "name3",
                        "email3@email.com",
                        "placeId1",
                        "place1",
                        "vicinity1",
                        "photoUser3",
                        null)
        );
        LiveData<List<User>> usersListLiveData = new MutableLiveData<>(usersList);
        when(firestoreRepositoryMock.getUsers()).thenReturn(usersListLiveData);

        viewModel = new WorkmatesViewModel(firestoreRepositoryMock, firebaseAuthRepositoryMock);
    }

    //TEST
    @Test
    public void userCurrentlyLoggedIsFiltered_whenGetUsers() throws InterruptedException {
        //fake list of WorkmatesViewState,
        //where userLogged is filtered,
        //where display name is compose of only one word (the first one of the entire displayName String)
        //where user who chose a place is the first of the list
        List<WorkmatesViewState> workmatesViewStateList = Arrays.asList(
                new WorkmatesViewState(
                        "id3",
                        "name3",
                        "photoUser3",
                        "place1",
                        "placeId1"
                ),
                new WorkmatesViewState(
                        "id2",
                        "2name",
                        "photoUser2",
                        null,
                        null
                )
        );

        List<WorkmatesViewState> actualList = LiveDataTestUtils.getOrAwaitValue(viewModel.getUsers());

        Truth.assertThat(actualList).containsExactlyElementsIn(workmatesViewStateList).inOrder();
        //verify that boolean hasChosenRestaurant is true for the first user of the list
        Truth.assertThat(actualList.get(0).hasChosenRestaurant()).isEqualTo(true);
        //verify user's display name is only one word from the string given
        Truth.assertThat(actualList.get(1).getDisplayName()).isEqualTo("2name");
    }

}


