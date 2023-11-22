package fr.selquicode.go4lunch.ui.detail;

import androidx.annotation.NonNull;

import java.util.Objects;

import fr.selquicode.go4lunch.data.model.PlacePhoto;

public class PlaceDetailsViewState {

    @NonNull
    private final String nameRestaurant, vicinity, phoneNumber, website;

    @NonNull
    private PlacePhoto restaurantImg;

    @NonNull
    private double ratings;

    public PlaceDetailsViewState(@NonNull String nameRestaurant,
                                 @NonNull String vicinity,
                                 @NonNull String phoneNumber,
                                 @NonNull String website,
                                 @NonNull PlacePhoto restaurantImg,
                                 @NonNull double ratings){
        this.nameRestaurant = nameRestaurant;
        this.vicinity = vicinity;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.restaurantImg = restaurantImg;
        this.ratings = ratings;

    }

    //GETTERS
    @NonNull
    public String getNameRestaurant() {
        return nameRestaurant;
    }

    @NonNull
    public String getVicinity() {
        return vicinity;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @NonNull
    public String getWebsite() {
        return website;
    }

    @NonNull
    public PlacePhoto getRestaurantImg() {
        return restaurantImg;
    }

    public double getRatings() {
        return ratings;
    }

    //METHOD UTILS
    @Override
    public String toString() {
        return "PlaceDetailViewState{" +
                ", nameRestaurant='" + nameRestaurant + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", restaurantImg=" + restaurantImg +
                ", ratings=" + ratings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceDetailsViewState)) return false;
        PlaceDetailsViewState that = (PlaceDetailsViewState) o;
        return Double.compare(that.getRatings(), getRatings()) == 0 &&  getNameRestaurant().equals(that.getNameRestaurant()) && getVicinity().equals(that.getVicinity()) && getPhoneNumber().equals(that.getPhoneNumber()) && getWebsite().equals(that.getWebsite()) && getRestaurantImg().equals(that.getRestaurantImg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNameRestaurant(), getVicinity(), getPhoneNumber(), getWebsite(), getRestaurantImg(), getRatings());
    }
}
