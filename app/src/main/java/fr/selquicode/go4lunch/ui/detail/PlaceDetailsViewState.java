package fr.selquicode.go4lunch.ui.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import fr.selquicode.go4lunch.data.model.PlacePhoto;

public class PlaceDetailsViewState {

    @Nullable
    private final String nameRestaurant, vicinity, phoneNumber, website;

    @Nullable
    private final PlacePhoto restaurantImg;

    @NonNull
    private final float ratings;

    public PlaceDetailsViewState(@Nullable String nameRestaurant,
                                 @Nullable String vicinity,
                                 @Nullable String phoneNumber,
                                 @Nullable String website,
                                 @Nullable PlacePhoto restaurantImg,
                                 @NonNull float ratings){
        this.nameRestaurant = nameRestaurant;
        this.vicinity = vicinity;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.restaurantImg = restaurantImg;
        this.ratings = ratings;

    }

    //GETTERS
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
