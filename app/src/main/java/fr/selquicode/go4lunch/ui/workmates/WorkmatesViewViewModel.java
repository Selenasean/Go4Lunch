package fr.selquicode.go4lunch.ui.workmates;

import androidx.lifecycle.ViewModel;

import fr.selquicode.go4lunch.data.PlaceRepository;

public class WorkmatesViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private PlaceRepository placeRepository;

    public WorkmatesViewViewModel(PlaceRepository repository){
        this.placeRepository = repository;
    }
}