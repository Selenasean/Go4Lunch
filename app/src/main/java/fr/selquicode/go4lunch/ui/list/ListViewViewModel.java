package fr.selquicode.go4lunch.ui.list;

import android.media.Image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlaceOpeningHours;

public class ListViewViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private MutableLiveData<List<ListViewState>> listVSMutableLiveData = new MutableLiveData<>();

    public ListViewViewModel(PlaceRepository repository){
        this.placeRepository = repository;
        LiveData<List<ListViewState>>  listLiveData = Transformations.map(placeRepository.getPlaces(), places -> {
            List<ListViewState> list = parseToViewState(places);
            return listVSMutableLiveData.setValue(list);
            }
        );
    }

    private List<ListViewState> parseToViewState(List<Place> places) {
        List<ListViewState> listViewState = new ArrayList<>();

        for(Place place : places){

            listViewState.add(new ListViewState(
                    place.getPlaceId(),
                    place.getName(),
                    place.getAddress(),
                    place.getPlacePhoto().get(0),
                    place.getOpening().isOpenNow(),
                    place.getRating()
            ));
        }
        return listViewState;
    }

    /**
     * Get Places
     * @return places type LiveData
     */
    public LiveData<List<ListViewState>> getPlaces(){ return listVSMutableLiveData; }

    //TODO : recupérer la liste de restaurant et la mapper dans le viewState, pour l'UI
}