package fr.selquicode.go4lunch.ui.map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.model.Place;

public class MapViewViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private GoogleMap map;

    public MapViewViewModel(PlaceRepository repository){
        this.placeRepository = repository;


    }

    /**
     * Get Places
     * @return places type LiveData
     */
    public LiveData<List<Place>> getPlaces(){ return placeRepository.getPlaces();}



}