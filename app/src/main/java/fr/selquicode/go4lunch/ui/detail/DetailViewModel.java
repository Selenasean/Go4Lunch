package fr.selquicode.go4lunch.ui.detail;


import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.model.Place;

public class DetailViewModel extends ViewModel {

    private PlaceRepository placeRepository;

    private LiveData<PlaceDetailsViewState> placeDetailsLiveData;

    public DetailViewModel(PlaceRepository placeRepository, String placeId){
        this.placeRepository = placeRepository;


        LiveData<Place> placeLiveData = placeRepository.getPlaceDetails(placeId);
        placeDetailsLiveData = Transformations.map(placeLiveData,place -> parseToViewState(place));



    }

    public LiveData<PlaceDetailsViewState> getPlaceDetails(){
        return placeDetailsLiveData;
    }



    private PlaceDetailsViewState parseToViewState(Place place){
        new PlaceDetailsViewState(
                place.getName(),
                place.getVicinity(),
                place.getPhone(),
                place.getWebsite(),
                place.getPlacePhotos().get(0),
                place.getRating());

        return null;
    }
}
