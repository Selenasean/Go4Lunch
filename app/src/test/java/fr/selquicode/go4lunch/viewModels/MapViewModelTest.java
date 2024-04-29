package fr.selquicode.go4lunch.viewModels;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.location.Location;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.common.truth.Truth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Bounds;
import fr.selquicode.go4lunch.data.model.Geometry;
import fr.selquicode.go4lunch.data.model.LatLngLiteral;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlaceOpeningHours;
import fr.selquicode.go4lunch.data.model.PlacePhoto;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.ui.map.MapViewModel;
import fr.selquicode.go4lunch.ui.map.MapViewState;
import fr.selquicode.go4lunch.utils.LiveDataTestUtils;

@RunWith(JUnit4.class)
public class MapViewModelTest {

    private final PlaceRepository placeRepositoryMock = mock();
    private final LocationRepository locationRepositoryMock = mock();
    private final PermissionChecker permissionCheckerMock = mock();
    private final FirestoreRepository firestoreRepositoryMock = mock();
    private final FirebaseAuthRepository firebaseAuthRepositoryMock = mock();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MapViewModel viewModel;

    //DATA FOR TEST
    private List<Place> places;
    private List<String> searchedPlaces;
    private List<User> users;
    private MutableLiveData<List<String>> searchedPlacesLiveData;

    @Before
    public void setup() {
        //mock of Location
        Location locationMock = mockLocation(1.0, 2.0);
        LiveData<Location> locationLiveData = new MutableLiveData<>(locationMock);
        when(locationRepositoryMock.getLocationLiveData()).thenReturn(locationLiveData);

        //mock of user logged id
        FirebaseUser userAuth = mock();
        when(userAuth.getUid()).thenReturn("id1");
        when(firebaseAuthRepositoryMock.getCurrentUser()).thenReturn(userAuth);

        //mock of List<Place>
        places = Arrays.asList(
               createPlace(
                        "placeId1",
                       "place1",
                        "vicinity1"),
               createPlace(
                        "placeId2",
                        "place2",
                        "vicinity2"
                        ),
               createPlace(
                        "placeId3",
                        "place3",
                        "vicinity3"
                        )
        );
        LiveData<List<Place>> placesLiveData = new MutableLiveData<>(places);
        when(placeRepositoryMock.getPlaces(locationMock)).thenReturn(placesLiveData);

        // mock of List<User>
        users = Arrays.asList(
                new User(
                        "id1",
                        "name1",
                        "email1@email.com",
                        null,
                        null,
                        null,
                        "photoUser1",
                        null),
                new User(
                        "id2",
                        "name2",
                        "email2@email.com",
                        "placeId1",
                        "place1",
                        "vicinity1",
                        "photoUser2",
                        null),
                new User("id3",
                        "name3",
                        "email3@email.com",
                        null,
                        null,
                        null,
                        "photoUser3",
                        null)
        );
        LiveData<List<User>> usersLiveData = new MutableLiveData<>(users);
        when(firestoreRepositoryMock.getUsers()).thenReturn(usersLiveData);

        //mock List<Place> searched when it's empty
        searchedPlaces = new ArrayList<>();
        searchedPlacesLiveData = new MutableLiveData<>(searchedPlaces);
        when(placeRepositoryMock.getSearchedPlaces()).thenReturn(searchedPlacesLiveData);

        viewModel = new MapViewModel(
                placeRepositoryMock,
                locationRepositoryMock,
                permissionCheckerMock,
                firestoreRepositoryMock,
                firebaseAuthRepositoryMock);
    }

    //UTILS METHOD
    private Location mockLocation(Double longitude, Double latitude) {
        Location location = mock();
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);

        return location;
    }

    private Geometry createGeometry(){
        return new Geometry(
                new LatLngLiteral(1.9, 2.7),
                new Bounds(
                        new LatLngLiteral(1.0, 2.0),
                        new LatLngLiteral(2.0, 1.0)));
    }

    private Place createPlace(String placeId, String placeName, String vicinity){
        return new Place(
                placeId,
                createGeometry(),
                vicinity,
                placeName,
                new PlaceOpeningHours(true),
                Collections.singletonList(
                        new PlacePhoto(
                                299,
                                Arrays.asList("string", "string"),
                                "photo_reference",
                                400)),
                2,
                "phone",
                "website"

        );
    }

    //TEST

    @Test
    public void getPlaces_whenSearchedPlacesIsNull() throws InterruptedException {
        //fake list of viewState
        List<MapViewState> mapViewStateList = Arrays.asList(
                new MapViewState(
                        "placeId1",
                        "place1",
                        true,
                        createGeometry()
                ),
                new MapViewState(
                        "placeId2",
                        "place2",
                        false,
                        createGeometry()
                ),
                new MapViewState(
                        "placeId3",
                        "place3",
                        false,
                        createGeometry()
                )

        );

        List<MapViewState> actualList = LiveDataTestUtils.getOrAwaitValue(viewModel.getPlaces());
        Truth.assertThat(actualList).containsExactlyElementsIn(mapViewStateList);
    }

    @Test
    public void getPlaces_whenThereIsSearchedPlaces() throws InterruptedException {
        //mock of List<Place> searched when it's not empty
        searchedPlaces = Arrays.asList("placeId2", "placeId3");
        searchedPlacesLiveData.setValue(searchedPlaces);

        //fake list of view state
        List<MapViewState> mapViewStateList = Arrays.asList(
                new MapViewState(
                        "placeId2",
                        "place2",
                        false,
                        createGeometry()
                ),
                new MapViewState(
                        "placeId3",
                        "place3",
                        false,
                        createGeometry()
                )

        );

        List<MapViewState> actualList = LiveDataTestUtils.getOrAwaitValue(viewModel.getPlaces());
        Truth.assertThat(actualList).containsExactlyElementsIn(mapViewStateList);
    }
}
