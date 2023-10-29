package fr.selquicode.go4lunch.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.model.Place;

public class ListViewViewModel extends ViewModel {

    private PlaceRepository placeRepository;

    public ListViewViewModel(PlaceRepository repository){
        this.placeRepository = repository;
    }

    /**
     * Get Places
     * @return places type LiveData
     */
    public LiveData<List<Place>> getPlaces(){ return placeRepository.getPlaces();}

    //TODO : recupérer la liste de restaurant et la mapper dans le viewState, pour l'UI
}