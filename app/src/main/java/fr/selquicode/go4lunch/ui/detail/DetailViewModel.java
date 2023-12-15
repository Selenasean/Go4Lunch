package fr.selquicode.go4lunch.ui.detail;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlacePhoto;
import fr.selquicode.go4lunch.ui.utils.RatingCalculator;

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
        float rating = RatingCalculator.calculateRating((float) place.getRating());
        //get first photo of the restaurant from the list
        PlacePhoto photo;
        List<PlacePhoto> photolist = place.getPlacePhotos();
        if(photolist == null || photolist.size() == 0){
            photo = null;
        }else{
            photo = photolist.get(0);
        }

        PlaceDetailsViewState placeDetailsViewState = new PlaceDetailsViewState(
                place.getName() == null ? "" : place.getName(),
                place.getVicinity() == null ? "" : place.getVicinity(),
                place.getPhone(),
                place.getWebsite(),
                photo,
                rating);

        return placeDetailsViewState;

    }

}

