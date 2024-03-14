package fr.selquicode.go4lunch.ui.list;


import android.location.Location;
import android.location.LocationManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlacePhoto;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.ui.utils.RatingCalculator;

public class ListViewModel extends ViewModel {

    private final PlaceRepository placeRepository;
    private final LocationRepository locationRepository;
    LiveData<Location> locationLiveData;
    private final MediatorLiveData<List<ListViewState>> listMediatorLiveData = new MediatorLiveData<>();

    public ListViewModel(PlaceRepository placeRepository,
                         LocationRepository locationRepository) {
        this.placeRepository = placeRepository;
        this.locationRepository = locationRepository;

        //get the user's location
        locationLiveData = locationRepository.getLocationLiveData();

        //get the list of places from the user's location
        LiveData<List<Place>> placesLiveData = Transformations.switchMap(locationLiveData, placeRepository::getPlaces);

        // to get searchedPlaces from place repository
        LiveData<List<String>> searchedPlacesLiveData = placeRepository.getSearchedPlaces();

        listMediatorLiveData.addSource(
                placesLiveData,
                places -> combine(
                        places,
                        searchedPlacesLiveData.getValue()
                )
        );

        listMediatorLiveData.addSource(
                searchedPlacesLiveData,
                searchedPlaces -> combine(
                        placesLiveData.getValue(),
                        searchedPlaces
                )
        );
    }

    private void combine(List<Place> places, List<String> searchedPlaceId) {
        if(places == null){
            return;
        }
        if(searchedPlaceId == null){
            List<ListViewState> placesListParsed = parseToViewState(places);
            listMediatorLiveData.setValue(placesListParsed);
        }else{
            List<Place> filteredPlaces = new ArrayList<>();
            for(Place place : places){
                if(searchedPlaceId.contains(place.getPlaceId())){
                    filteredPlaces.add(place);
                }
            }
            List<ListViewState> filteredListParsed = parseToViewState(filteredPlaces);
            listMediatorLiveData.setValue(filteredListParsed);

        }

    }

    private List<ListViewState> parseToViewState(List<Place> places) {
        List<ListViewState> listViewState = new ArrayList<>();
        Location userLocation = locationRepository.getLocation();

        for (Place place : places) {

            //to get the first photo of the restaurant from the photo's list
            List<PlacePhoto> photosList = place.getPlacePhotos();
            PlacePhoto photo;
            if (photosList == null || photosList.isEmpty()) {
                photo = null;
            } else {
                photo = photosList.get(0);
            }

            // calculate the rating for 3 stars
            float rating = RatingCalculator.calculateRating((float) place.getRating());

            //calculate distance between user's location and restaurant's location
            assert place.getGeometry() != null;
            Location restaurantLocation = new Location(LocationManager.GPS_PROVIDER);
            restaurantLocation.setLatitude(place.getGeometry().getLocation().getLat());
            restaurantLocation.setLongitude(place.getGeometry().getLocation().getLng());
            float distance = userLocation.distanceTo(restaurantLocation);
            String distanceString = String.valueOf(distance).split("\\.")[0] + "m";

            assert place.getPlaceId() != null;
            listViewState.add(new ListViewState(
                    place.getPlaceId(),
                    place.getName() == null ? "" : place.getName(),
                    place.getVicinity() == null ? "" : place.getVicinity(),
                    photo,
                    distanceString,
                    place.getOpening() == null ? null : place.getOpening().isOpenNow(),
                    rating
            ));
        }
        //sort by closer place first
        listViewState.sort((o1, o2) -> {
            String distanceString1 = o1.getDistance().split("m")[0];
            String distanceString2 = o2.getDistance().split("m")[0];
            float distance1 = Float.parseFloat(distanceString1);
            float distance2 = Float.parseFloat(distanceString2);
            return Float.compare(distance1, distance2);
        });
        return listViewState;
    }

    /**
     * Get Places
     *
     * @return places type LiveData
     */
    public LiveData<List<ListViewState>> getPlaces() {
        return listMediatorLiveData;
    }

}