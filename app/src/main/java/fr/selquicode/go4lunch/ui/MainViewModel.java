package fr.selquicode.go4lunch.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.model.Place;

public class MainViewModel extends ViewModel {

    private final PlaceRepository repository;

    public MainViewModel(PlaceRepository placeRepository) {
        repository = placeRepository;
    }

    /**
     * Get Places
     * @return places type LiveData
     */
    public LiveData<List<Place>> getPlacesLiveData(){
        return repository.getPlaces();
    }

}
