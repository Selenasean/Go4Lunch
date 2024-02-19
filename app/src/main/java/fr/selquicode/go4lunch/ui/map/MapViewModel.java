package fr.selquicode.go4lunch.ui.map;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;
import fr.selquicode.go4lunch.data.place.PlaceRepository;

public class MapViewModel extends ViewModel {

    private final PlaceRepository placeRepository;
    private final LocationRepository locationRepository;
    private final PermissionChecker permissionChecker;
    private MediatorLiveData<List<Place>> placesMediatorLivedata = new MediatorLiveData<>();

    public MapViewModel(PlaceRepository placeRepository,
                        LocationRepository locationRepository,
                        PermissionChecker permissionChecker) {
        this.placeRepository = placeRepository;
        this.locationRepository = locationRepository;
        this.permissionChecker = permissionChecker;

        // to get the list of restaurant using user's localisation from LocationRepository
        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();
        LiveData<List<Place>> placesLiveData = Transformations.switchMap(locationLiveData, placeRepository::getPlaces);
        // to get searchedPlaces from place repository
        LiveData<List<String>> searchedPlacesLiveData = placeRepository.getSearchedPlaces();

        placesMediatorLivedata.addSource(
                placesLiveData,
                places -> combine(
                        places,
                        searchedPlacesLiveData.getValue()
                ));

        placesMediatorLivedata.addSource(
                searchedPlacesLiveData,
                searchedPlaces -> combine (
                        placesLiveData.getValue(),
                        searchedPlaces
                ));

    }

    private void combine(List<Place> places, List<String> searchedPlacesId) {
        if(places == null){
            return;
        }else{
            if(searchedPlacesId == null){
                Log.i("mapVM", "places = " + places.toString());
                placesMediatorLivedata.setValue(places);
            }else{
                Log.i("mapVM", "searchedPLaces =" + searchedPlacesId);
                List<Place> filteredPlaces = new ArrayList<>();
                for(String placeId : searchedPlacesId){
                    for(Place place : places){{
                        Log.i("mapVM", "place.getPlaceId =" + place.getPlaceId());
                        if(Objects.equals(placeId, place.getPlaceId())){
                            filteredPlaces.add(place);
                            Log.i("MapVM", "filteredPlaces = " + filteredPlaces.toString());
                        }
                    }}
                }
                Log.i("MapVM", "filteredPlaces = " + filteredPlaces.toString());
                placesMediatorLivedata.setValue(filteredPlaces);
            }
        }

    }


    /**
     * Get Places
     *
     * @return places type LiveData
     */
    public LiveData<List<Place>> getPlaces() {
        return placesMediatorLivedata;
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