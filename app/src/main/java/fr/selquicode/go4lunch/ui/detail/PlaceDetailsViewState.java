package fr.selquicode.go4lunch.ui.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import fr.selquicode.go4lunch.data.model.PlacePhoto;

public class PlaceDetailsViewState {

    @NonNull
    private final String idRestaurant;

    @Nullable
    private final String nameRestaurant, vicinity, phoneNumber, website;

    @Nullable
    private final PlacePhoto restaurantImg;

    @NonNull
    private final float ratings;


    public PlaceDetailsViewState(
            @NonNull String idRestaurant,
            @Nullable String nameRestaurant,
            @Nullable String vicinity,
            @Nullable String phoneNumber,
            @Nullable String website,
            @Nullable PlacePhoto restaurantImg,
            @NonNull float ratings
    ){
        this.idRestaurant = idRestaurant;
        this.nameRestaurant = nameRestaurant;
        this.vicinity = vicinity;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.restaurantImg = restaurantImg;
        this.ratings = ratings;

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

    //METHOD UTILS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceDetailsViewState)) return false;
        PlaceDetailsViewState that = (PlaceDetailsViewState) o;
        return Float.compare(that.getRatings(), getRatings()) == 0 && Objects.equals(getIdRestaurant(), that.getIdRestaurant()) && Objects.equals(getNameRestaurant(), that.getNameRestaurant()) && Objects.equals(getVicinity(), that.getVicinity()) && Objects.equals(getPhoneNumber(), that.getPhoneNumber()) && Objects.equals(getWebsite(), that.getWebsite()) && Objects.equals(getRestaurantImg(), that.getRestaurantImg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdRestaurant(), getNameRestaurant(), getVicinity(), getPhoneNumber(), getWebsite(), getRestaurantImg(), getRatings());
    }

    @NonNull
    @Override
    public String toString() {
        return "PlaceDetailsViewState{" +
                "idRestaurant='" + idRestaurant + '\'' +
                ", nameRestaurant='" + nameRestaurant + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", restaurantImg=" + restaurantImg +
                ", ratings=" + ratings +
                '}';
    }
}
