package fr.selquicode.go4lunch.ui.detail;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlacePhoto;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.ui.utils.RatingCalculator;

public class DetailViewModel extends ViewModel {

    private final PlaceRepository placeRepository;
    private final FirestoreRepository firestoreRepository;
    private final FirebaseAuthRepository firebaseAuthRepository;
    private LiveData<PlaceDetailsViewState> placeDetailsLiveData;
    private final String placeId;

    /**
     * Constructor
     * @param placeRepository where we get all the places
     * @param placeId to know which restaurant we wanna get
     * @param firestoreRepository to get info in db
     */
    public DetailViewModel(
            PlaceRepository placeRepository,
            String placeId,
            FirestoreRepository firestoreRepository,
            FirebaseAuthRepository firebaseAuthRepository)
    {
        this.placeRepository = placeRepository;
        this.firestoreRepository = firestoreRepository;
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.placeId = placeId;

        //get a place using his id
        LiveData<Place> placeLiveData = placeRepository.getPlaceDetails(placeId);
        placeDetailsLiveData = Transformations.map(placeLiveData, this::parseToViewState);
    }

    /**
     * To get the restaurant's details we are interested in, in type LiveData for the UI
     * @return a LiveData
     */
    public LiveData<PlaceDetailsViewState> getPlaceDetails(){
        return placeDetailsLiveData;
    }

    /**
     * To get a list of workmates who chose to eat in the restaurant displayed
     * @return a list of workmates who chose the particular restaurant, type LiveData for the UI
     */
    public LiveData<List<WorkmatesDetailViewState>> getWorkmatesWhoChose(){
        LiveData<List<User>> usersWhoChoseListLD = firestoreRepository.getUsersWhoChose(placeId);
        return Transformations.map(usersWhoChoseListLD, this::parseToWorkmatesDetailViewState);
    }

    /**
     * To parse a list of User into a list of WorkmatesDetailViewState
     * @param users a list of users
     * @return a list of workmatesDetailViewState
     */
    private List<WorkmatesDetailViewState> parseToWorkmatesDetailViewState(List<User> users) {

        List<WorkmatesDetailViewState> workmatesDetailViewStateList = users.stream()
                .filter(user -> !user.getId().equals(firebaseAuthRepository.getCurrentUser().getUid()))
                .map(user ->
                        new WorkmatesDetailViewState(
                                user.getId(),
                                user.getDisplayName().contains(" ") ?
                                        user.getDisplayName().split(" ")[0] : user.getDisplayName(),
                                user.getPhotoUserUrl(),
                                user.getRestaurantId()
                        )
                )
                .collect(Collectors.toList());
        return workmatesDetailViewStateList;
    }

    /**
     * Method to parse a Place into a PlaceDetailsViewState
     * @param place type Place
     * @return a PlaceDetailsViewState
     */
    private PlaceDetailsViewState parseToViewState(Place place){
        Log.i("detailView", place.getPlaceId() + "" );

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
        //create PlaceDetailsViewState from Place
        assert place.getPlaceId() != null;
        return new PlaceDetailsViewState(
                place.getPlaceId(),
                place.getName() == null ? "" : place.getName(),
                place.getVicinity() == null ? "" : place.getVicinity(),
                place.getPhone(),
                place.getWebsite(),
                photo,
                rating);

    }

    /**
     * To compare if restaurant chosen by user logged is the same as the restaurant details displayed
     * @return boolean type LiveData
     */
    public LiveData<Boolean> isRestaurantChosenByUserLogged() {
        LiveData<User> isUserLoggedChoose = firestoreRepository.userLogged(
                firebaseAuthRepository.getCurrentUser().getUid());
        return Transformations.map(isUserLoggedChoose, user -> {
            return Objects.equals(user.getRestaurantId(), placeId);
        });
    }

    /**
     * Method that update from the firestore repository the restaurant chosen by the user logged
     * @param restaurantName a String of the restaurant's name
     */
    public void onRestaurantChoice(String restaurantName) {
        firestoreRepository.updateRestaurantChosen(
                firebaseAuthRepository.getCurrentUser().getUid(),
                placeId,
                restaurantName);
    }
}

