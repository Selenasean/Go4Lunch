package fr.selquicode.go4lunch.ui.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class WorkmatesDetailViewState {

    @NonNull
    private final String workmateId, displayName;

    @Nullable
    String photoUrl, restaurantToEatId;

    public WorkmatesDetailViewState(
            @NonNull String workmateId,
            @NonNull String displayName,
            @Nullable String photoUrl,
            @Nullable String restaurantToEatId
    ){
        this.workmateId = workmateId;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.restaurantToEatId = restaurantToEatId;
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
    public String getRestaurantToEatId() {
        return restaurantToEatId;
    }

    //UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkmatesDetailViewState)) return false;
        WorkmatesDetailViewState that = (WorkmatesDetailViewState) o;
        return Objects.equals(getWorkmateId(), that.getWorkmateId()) && Objects.equals(getDisplayName(), that.getDisplayName()) && Objects.equals(getPhotoUrl(), that.getPhotoUrl()) && Objects.equals(getRestaurantToEatId(), that.getRestaurantToEatId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkmateId(), getDisplayName(), getPhotoUrl(), getRestaurantToEatId());
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmatesDetailViewState{" +
                "workmateId='" + workmateId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", restaurantToEatId='" + restaurantToEatId + '\'' +
                '}';
    }
}
