package fr.selquicode.go4lunch.ui.workmates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class WorkmatesViewState {

    @NonNull
    private final String workmateId, displayName;

    @Nullable
    private final String restaurantName, restaurantId, photoUrl;



    public WorkmatesViewState(
            @NonNull  String workmateId ,
            @NonNull String displayName,
            @Nullable String photoUrl,
            @Nullable String restaurantName,
            @Nullable String restaurantId){
        this.workmateId = workmateId;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
    }

    //GETTERS
    @NonNull
    public String getWorkmateId() {
        return workmateId;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Nullable
    public String getRestaurantName() {
        return restaurantName;
    }

    @Nullable
    public String getRestaurantId() {
        return restaurantId;
    }

    /**
     * Computed property depending on other values of the ViewState
     * @return boolean
     */
    public boolean hasChosenRestaurant() {
        return restaurantId != null;
    }


    //UTILS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkmatesViewState)) return false;
        WorkmatesViewState that = (WorkmatesViewState) o;
        return Objects.equals(getWorkmateId(), that.getWorkmateId()) && Objects.equals(getDisplayName(), that.getDisplayName()) && Objects.equals(getRestaurantName(), that.getRestaurantName()) && Objects.equals(getRestaurantId(), that.getRestaurantId()) && Objects.equals(getPhotoUrl(), that.getPhotoUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkmateId(), getDisplayName(), getRestaurantName(), getRestaurantId(), getPhotoUrl());
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmatesViewState{" +
                "workmateId='" + workmateId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
