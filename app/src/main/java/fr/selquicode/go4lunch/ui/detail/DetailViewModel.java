package fr.selquicode.go4lunch.ui.detail;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlacePhoto;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.data.model.Workmate;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.domain.NotificationSchedule;
import fr.selquicode.go4lunch.ui.utils.RatingCalculator;

public class DetailViewModel extends ViewModel {

    private final FirestoreRepository firestoreRepository;
    private final FirebaseAuthRepository firebaseAuthRepository;
    private final String placeId;
    private final MediatorLiveData<DetailViewState> detailMediatorLiveData = new MediatorLiveData<>();
    private String userLoggedId;
    private NotificationSchedule notificationSchedule;

    /**
     * Constructor
     *
     * @param placeRepository     where we get all the places
     * @param placeId             to know which restaurant we wanna get
     * @param firestoreRepository to get info in db
     * @param firebaseAuthRepository where we get user logged data
     */
    public DetailViewModel(
            PlaceRepository placeRepository,
            String placeId,
            FirestoreRepository firestoreRepository,
            FirebaseAuthRepository firebaseAuthRepository,
            NotificationSchedule notificationSchedule) {

        this.firestoreRepository = firestoreRepository;
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.placeId = placeId;
        this.notificationSchedule = notificationSchedule;


        // defined user logged id
        userLoggedId = firebaseAuthRepository.getCurrentUser().getUid();
        //get a place using his id
        LiveData<Place> placeLiveData = placeRepository.getPlaceDetails(placeId);

        // get a list of users who choose the restaurant displayed
        LiveData<List<User>> workmatesWhoChoseListLD = firestoreRepository.getUsersWhoChose(placeId);

        //get a boolean if user logged has chosen the restaurant displayed or not
        LiveData<User> userLoggedData = firestoreRepository.getOneUser(userLoggedId);

        //combine the sources of the mediatorLivedata for the UI
        detailMediatorLiveData.addSource(
                placeLiveData,
                placeDetail -> combine(
                        placeDetail,
                        workmatesWhoChoseListLD.getValue(),
                        userLoggedData.getValue()
                )
        );
        detailMediatorLiveData.addSource(
                workmatesWhoChoseListLD,
                workmatesWhoChoose -> combine(
                        placeLiveData.getValue(),
                        workmatesWhoChoose,
                        userLoggedData.getValue()
                )
        );
        detailMediatorLiveData.addSource(
                userLoggedData,
                userLogged -> combine(
                        placeLiveData.getValue(),
                        workmatesWhoChoseListLD.getValue(),
                        userLogged
                )
        );

    }

    /**
     * combine the 3 sources of LiveData into one by parsing the data into right type
     *
     * @param place              = a place
     * @param workmatesWhoChose  = a list of workmates who chose the restaurant displayed in UI
     * @param userLogged = user logged data
     */
    private void combine(
            Place place,
            List<User> workmatesWhoChose,
            User userLogged) {

        if (place == null || userLogged == null) {
            return;
        }

        //calculate rating for 3stars
        float rating = RatingCalculator.calculateRating((float) place.getRating());
        //get first photo of the restaurant from the list
        PlacePhoto photo;
        List<PlacePhoto> photolist = place.getPlacePhotos();
        if (photolist == null || photolist.isEmpty()) {
            photo = null;
        } else {
            photo = photolist.get(0);
        }

        //parse List<User> into List<Workmate>
        List<Workmate> workmatesList = new ArrayList<>();
        if (workmatesWhoChose != null) {
            workmatesList = parseToWorkmatesList(workmatesWhoChose);
        }

        //create a DetailViewState
        assert place.getPlaceId() != null;
        DetailViewState viewState = new DetailViewState(
                place.getPlaceId(),
                place.getName() == null ? null : place.getName(),
                place.getVicinity() == null ? null : place.getVicinity(),
                place.getPhone(),
                place.getWebsite(),
                photo,
                rating,
                workmatesList,
                Objects.equals(userLogged.getRestaurantId(), placeId),
                userLogged.getFavoritePlacesId().contains(placeId)
        );
        //set final value into mediatorLiveData
        detailMediatorLiveData.setValue(viewState);
    }

    /**
     * Method to parse a List of User into a List of Workmate
     *
     * @param workmatesWhoChose : a List of User
     * @return a List of Workmate
     */
    private List<Workmate> parseToWorkmatesList(List<User> workmatesWhoChose) {
        return workmatesWhoChose.stream()
                .filter(user -> !user.getId().equals(userLoggedId))
                .map(user ->
                        new Workmate(
                                user.getId(),
                                user.getDisplayName().contains(" ") ?
                                        user.getDisplayName().split(" ")[0] : user.getDisplayName(),
                                user.getPhotoUserUrl(),
                                user.getRestaurantId()
                        )
                )
                .collect(Collectors.toList());
    }

    /**
     * To get all the data we want to display in UI
     *
     * @return a mediatorLiveData which is the sum of the 3 LiveData used for this UI
     */ 
    public LiveData<DetailViewState> getDetailViewStateLD() {
        return detailMediatorLiveData;
    }

    /**
     * Method that update from the firestore repository the restaurant chosen by the user logged
     *
     * @param restaurantName a String of the restaurant's name
     */
    public void onRestaurantChoice(String restaurantName, String restaurantAddress) {
        DetailViewState detailViewState = getDetailViewStateLD().getValue();
        if(detailViewState != null){
            if(!detailViewState.isUserLoggedChose()){
                notificationSchedule.scheduleNotification();
            }else{
                notificationSchedule.notificationToCancelled();
            }
        }

        firestoreRepository.updateRestaurantChosen(
                userLoggedId,
                placeId,
                restaurantName,
                restaurantAddress);


    }


    public void onFavoriteChoice(Boolean isPlaceInFavorites) {
        Log.i("vmdetail", "onfavoritechoice");
        if (isPlaceInFavorites) {
            Log.i("vmdetail", "id deja existant");
            firestoreRepository.removeFromFavoriteList(userLoggedId, placeId);
        } else {
            Log.i("vmdetail", "id non existant");
            firestoreRepository.addToFavoriteList(userLoggedId, placeId);
        }
    }
}

