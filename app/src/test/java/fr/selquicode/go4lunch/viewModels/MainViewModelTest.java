package fr.selquicode.go4lunch.viewModels;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.location.Location;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;


import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.ui.MainViewModel;


@RunWith(JUnit4.class)
public class MainViewModelTest {

    private final PermissionChecker permissionCheckerMock = mock(PermissionChecker.class);
    private final LocationRepository locationRepositoryMock = mock(LocationRepository.class);
    private final FirestoreRepository firestoreRepositoryMock = mock(FirestoreRepository.class);
    private final FirebaseAuthRepository firebaseAuthRepositoryMock = mock(FirebaseAuthRepository.class);
    private final PlaceRepository placeRepositoryMock = mock(PlaceRepository.class);
    private final FirebaseUser firebaseUserMock = mock();
    private  MainViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup(){
        User userLogged = new User(
                "idLogged",
                "nameLogged",
                "emailLogged@email.com",
                null,
                null,
                null,
                "photoUrlLogged",
                null
        );
        when(firebaseAuthRepositoryMock.getCurrentUser()).thenReturn(firebaseUserMock);
        when(firebaseUserMock.getUid()).thenReturn(userLogged.getId());

        viewModel = new MainViewModel(
                permissionCheckerMock,
                locationRepositoryMock,
                firestoreRepositoryMock,
                firebaseAuthRepositoryMock,
                placeRepositoryMock
        );

    }

    private Location mockLocation(Double longitude, Double latitude){
        Location location = mock();
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);

        return location;
    }

    @Test
    public void refresh_whenPermissionCheckerIsTrue(){
        when(permissionCheckerMock.hasLocationPermission()).thenReturn(true);
        //method to test
        viewModel.refresh();
        Mockito.verify(locationRepositoryMock).startLocationRequest();
    }

    @Test
    public void refresh_whenPermissionCheckerIsFalse(){
        when(permissionCheckerMock.hasLocationPermission()).thenReturn(false);
        //method to test
        viewModel.refresh();
        Mockito.verify(locationRepositoryMock).stopLocationRequest();
    }


    @Test
    public void searchedQuery_whenLocationIsNotNull(){
        Location locationMock = mockLocation(1.0,2.0);
        when(locationRepositoryMock.getLocation()).thenReturn(locationMock);
        //method to test
        viewModel.searchQuery("string");

        verify(placeRepositoryMock).searchedPlaces("string", locationMock);
    }


}
