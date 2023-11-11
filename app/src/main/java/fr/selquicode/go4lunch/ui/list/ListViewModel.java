package fr.selquicode.go4lunch.ui.list;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlacePhoto;

public class ListViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private LiveData<List<ListViewState>> listLiveData;

    public ListViewModel(PlaceRepository repository){
        this.placeRepository = repository;
//        listLiveData = Transformations.map(placeRepository.getPlaces(), places -> {
//            List<ListViewState> list = parseToViewState(places);
//            return list;
//            }
//        );
    }

    private List<ListViewState> parseToViewState(List<Place> places) {
        List<ListViewState> listViewState = new ArrayList<>();

        for(Place place : places){
            List<PlacePhoto> photosList = place.getPlacePhotos();
            PlacePhoto photo;
            if (photosList == null || photosList.size() == 0) {
                 photo = null;
            }else{
                photo = photosList.get(0);
            }


            listViewState.add(new ListViewState(
                    place.getPlaceId(),
                    place.getName() == null ? "": place.getName(),
                    place.getFormattedAddress() == null ? "" : place.getFormattedAddress(),
                    photo,
                    place.getOpening() == null ? null : place.getOpening().isOpenNow(),
                    place.getRating()
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