package fr.selquicode.go4lunch.ui.map;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;

public class MapViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private LocationRepository locationRepository;
    private PermissionChecker permissionChecker;
    private LiveData<List<Place>> placesLiveData;

    public MapViewModel(PlaceRepository placeRepository, LocationRepository locationRepository, PermissionChecker permissionChecker){
        this.placeRepository = placeRepository;
        this.locationRepository = locationRepository;
        this.permissionChecker = permissionChecker;

        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();
        // to get the list of restaurant using user's localisation from LocationRepository
        placesLiveData = Transformations.switchMap(locationLiveData, placeRepository::getPlaces);
    }

    /**
     * Get Places
     * @return places type LiveData
     */
    public LiveData<List<Place>> getPlaces(){ return placesLiveData;}

    /**
     * Request Permission for the map
     */
    @SuppressLint("MissingPermission")
    public void locateTheUserOnMap(GoogleMap map){
        if(permissionChecker.hasLocationPermission()){
            map.setMyLocationEnabled(true);
        }
    }



}