package fr.selquicode.go4lunch.ui.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

import fr.selquicode.go4lunch.data.model.PlacePhoto;
import fr.selquicode.go4lunch.data.model.Workmate;

public class DetailViewState {

    @NonNull
    private final String idRestaurant;

    @Nullable
    private final String nameRestaurant, vicinity, phoneNumber, website;

    @Nullable
    private final PlacePhoto restaurantImg;

    private final float ratings;

    @NonNull
    private final List<Workmate> workmateList;

    private final boolean isUserLoggedChose;
    private final boolean isPlaceInFavorites;

    public DetailViewState(
            @NonNull String idRestaurant,
            @Nullable String nameRestaurant,
            @Nullable String vicinity,
            @Nullable String phoneNumber,
            @Nullable String website,
            @Nullable PlacePhoto restaurantImg,
            float ratings,
            @NonNull List<Workmate> workmateList,
            boolean isUserLoggedChose,
            boolean isPlaceInFavorites
    ) {
        this.idRestaurant = idRestaurant;
        this.nameRestaurant = nameRestaurant;
        this.vicinity = vicinity;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.restaurantImg = restaurantImg;
        this.ratings = ratings;
        this.workmateList = workmateList;
        this.isUserLoggedChose = isUserLoggedChose;
        this.isPlaceInFavorites = isPlaceInFavorites;
    }

    //GETTERS

    @NonNull
    public String getIdRestaurant() {
        return idRestaurant;
    }

    @Nullable
    public String getNameRestaurant() {
        return nameRestaurant;
    }

    @Nullable
    public String getVicinity() {
        return vicinity;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public String getWebsite() {
        return website;
    }

    @Nullable
    public PlacePhoto getRestaurantImg() {
        return restaurantImg;
    }

    public float getRatings() {
        return ratings;
    }

    @NonNull
    public List<Workmate> getWorkmateList() {
        return workmateList;
    }

    public boolean isUserLoggedChose() {
        return isUserLoggedChose;
    }

    public boolean isPlaceInFavorites(){ return isPlaceInFavorites; }


    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "DetailViewState{" +
                "idRestaurant='" + idRestaurant + '\'' +
                ", nameRestaurant='" + nameRestaurant + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", restaurantImg=" + restaurantImg +
                ", ratings=" + ratings +
                ", workmateList=" + workmateList +
                ", isUserLoggedChose=" + isUserLoggedChose +
                ", isPlaceInFavorites=" + isPlaceInFavorites +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailViewState)) return false;
        DetailViewState viewState = (DetailViewState) o;
        return Float.compare(viewState.getRatings(), getRatings()) == 0 && isUserLoggedChose() == viewState.isUserLoggedChose() && isPlaceInFavorites() == viewState.isPlaceInFavorites() && Objects.equals(getIdRestaurant(), viewState.getIdRestaurant()) && Objects.equals(getNameRestaurant(), viewState.getNameRestaurant()) && Objects.equals(getVicinity(), viewState.getVicinity()) && Objects.equals(getPhoneNumber(), viewState.getPhoneNumber()) && Objects.equals(getWebsite(), viewState.getWebsite()) && Objects.equals(getRestaurantImg(), viewState.getRestaurantImg()) && Objects.equals(getWorkmateList(), viewState.getWorkmateList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdRestaurant(), getNameRestaurant(), getVicinity(), getPhoneNumber(), getWebsite(), getRestaurantImg(), getRatings(), getWorkmateList(), isUserLoggedChose(), isPlaceInFavorites());
    }
}
