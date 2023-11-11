package fr.selquicode.go4lunch.ui.map;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Place;

public class MapViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private LocationRepository locationRepository;
    private LiveData<List<Place>> placesLiveData;

    public MapViewModel(PlaceRepository placeRepository, LocationRepository locationRepository){
        this.placeRepository = placeRepository;
        this.locationRepository = locationRepository;

        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();
        placesLiveData = Transformations.switchMap(locationLiveData, placeRepository::getPlaces);
    }

    /**
     * Get Places
     * @return places type LiveData
     */
    public LiveData<List<Place>> getPlaces(){ return placesLiveData;}



}