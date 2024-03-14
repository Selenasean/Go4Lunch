package fr.selquicode.go4lunch.ui.map;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;
import fr.selquicode.go4lunch.data.place.PlaceRepository;

public class MapViewModel extends ViewModel {

    private final PlaceRepository placeRepository;
    private final LocationRepository locationRepository;
    private final PermissionChecker permissionChecker;
    private final FirestoreRepository firestoreRepository;
    private final MediatorLiveData<List<MapViewState>> placesMediatorLivedata = new MediatorLiveData<>();

    public MapViewModel(PlaceRepository placeRepository,
                        LocationRepository locationRepository,
                        PermissionChecker permissionChecker,
                        FirestoreRepository firestoreRepository) {
        this.placeRepository = placeRepository;
        this.locationRepository = locationRepository;
        this.permissionChecker = permissionChecker;
        this.firestoreRepository = firestoreRepository;

        // to get the list of restaurant using user's localisation from LocationRepository
        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();
        LiveData<List<Place>> placesLiveData = Transformations.switchMap(locationLiveData, placeRepository::getPlaces);
        // to get searchedPlaces from place repository
        LiveData<List<String>> searchedPlacesLiveData = placeRepository.getSearchedPlaces();
        //get all users
        LiveData<List<User>> usersListLiveData = firestoreRepository.getUsers();

        placesMediatorLivedata.addSource(
                placesLiveData,
                places -> combine(
                        places,
                        searchedPlacesLiveData.getValue(),
                        usersListLiveData.getValue()
                )
        );

        placesMediatorLivedata.addSource(
                searchedPlacesLiveData,
                searchedPlaces -> combine(
                        placesLiveData.getValue(),
                        searchedPlaces,
                        usersListLiveData.getValue()
                )
        );

        placesMediatorLivedata.addSource(
                usersListLiveData,
                users -> combine(
                        placesLiveData.getValue(),
                        searchedPlacesLiveData.getValue(),
                        users
                )
        );


    }

    private void combine(List<Place> places, List<String> searchedPlacesId, List<User> users) {
        if (places == null || users == null) {
            return;
        } else {
            //TODO : find how compare place.getId and user.getRestaurantId
            if (searchedPlacesId == null) {
                Log.i("mapVM", "places = " + places.toString());
                List<MapViewState> listParsed = parseToMapViewState(places, users);
                placesMediatorLivedata.setValue(listParsed);
            } else {
                Log.i("mapVM", "searchedPLaces =" + searchedPlacesId);
                List<Place> filteredPlaces = new ArrayList<>();
                for (Place place : places) {
                    if (searchedPlacesId.contains(place.getPlaceId())) {
                        filteredPlaces.add(place);
                        Log.i("mapVM", "place.getPlaceId =" + place.getPlaceId());
                    }
                }
                Log.i("MapVM", "filteredPlaces = " + filteredPlaces.toString());
                List<MapViewState> filteredListParsed = parseToMapViewState(filteredPlaces, users);
                placesMediatorLivedata.setValue(filteredListParsed);
            }
        }

    }

    private List<MapViewState> parseToMapViewState(List<Place> places, List<User> users) {
    //TODO : parse list into MapViewState & find how defined boolean according to user.getRestaurantId

        List<MapViewState> mapViewStateList = places.stream()
                .map( place ->
                        new MapViewState(
                                place.getPlaceId(),
                                place.getName(),
                                false,
                                place.getGeometry()
                        ))
                .collect(Collectors.toList());
        return mapViewStateList;
    }


    /**
     * Get Places
     * @return places type LiveData
     */
    public LiveData<List<MapViewState>> getPlaces() {
        return placesMediatorLivedata;
    }

    /**
     * Get user's location
     */
    public LatLng getUserLocation(){
        if(locationRepository.getLocation() == null){
            return null;
        }
        return new LatLng(locationRepository.getLocation().getLatitude(), locationRepository.getLocation().getLongitude());
    }

    /**
     * Request Permission for the map
     */
    @SuppressLint("MissingPermission")
    public void locateTheUserOnMap(GoogleMap map) {
        if (permissionChecker.hasLocationPermission()) {
            map.setMyLocationEnabled(true);
        }
    }


}