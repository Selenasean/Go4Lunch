package fr.selquicode.go4lunch.ui.list;


import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlacePhoto;
import fr.selquicode.go4lunch.ui.utils.RatingCalculator;

public class ListViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private LocationRepository  locationRepository;
    private LiveData<List<ListViewState>> listLiveData;

    public ListViewModel(PlaceRepository placeRepository, LocationRepository locationRepository){
        this.placeRepository = placeRepository;
        this.locationRepository = locationRepository;

        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();
        LiveData<List<Place>> placesLiveData = Transformations.switchMap(locationLiveData, location -> placeRepository.getPlaces(location));
        listLiveData = Transformations.map(placesLiveData, this::parseToViewState);

    }

    private List<ListViewState> parseToViewState(List<Place> places) {
        //to get currentLocation

        List<ListViewState> listViewState = new ArrayList<>();


        for(Place place : places){
            //to get the first photo of the restaurant from the photo's list
            List<PlacePhoto> photosList = place.getPlacePhotos();
            PlacePhoto photo;
            if (photosList == null || photosList.size() == 0) {
                 photo = null;
            }else{
                photo = photosList.get(0);
            }
            // calculate the rating for 3 stars
            float rating = RatingCalculator.calculateRating((float)place.getRating());

            listViewState.add(new ListViewState(
                    place.getPlaceId(),
                    place.getName() == null ? "": place.getName(),
                    place.getVicinity() == null ? "" : place.getVicinity(),
                    photo,
                    0,
                    place.getOpening() == null ? null : place.getOpening().isOpenNow(),
                    rating
            ));
        }
        return listViewState;
    }

    /**
     * Get Places
     * @return places type LiveData
     */
    public LiveData<List<ListViewState>> getPlaces(){ return listLiveData; }

}