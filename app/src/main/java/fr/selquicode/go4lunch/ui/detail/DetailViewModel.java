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
        //calculate rating for 3stars
        float rating = (float) place.getRating() * 3 / 5;

        PlaceDetailsViewState placeDetailsViewState = new PlaceDetailsViewState(
                place.getName() == null ? "" : place.getName(),
                place.getVicinity() == null ? "" : place.getVicinity(),
                place.getPhone() == null ? "" : place.getPhone(),
                place.getWebsite() == null ? "" : place.getWebsite(),
                place.getPlacePhotos().get(0),
                rating);

        return placeDetailsViewState;

    }
}

