package fr.selquicode.go4lunch.ui.workmates;

import androidx.lifecycle.ViewModel;

import fr.selquicode.go4lunch.data.PlaceRepository;

public class WorkmatesViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private PlaceRepository placeRepository;

    public WorkmatesViewModel(PlaceRepository repository){
        this.placeRepository = repository;
    }
}