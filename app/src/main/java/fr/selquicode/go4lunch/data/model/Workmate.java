package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Workmate {

    @NonNull
    private final String workmateId, workmateDisplayName;

    @Nullable
    String workmatePhotoUrl, workmateRestaurantId;

    public Workmate(
            @NonNull String workmateId,
            @NonNull String workmateDisplayName,
            @Nullable String workmatePhotoUrl,
            @Nullable String workmateRestaurantId
    ){
        this.workmateId = workmateId;
        this.workmateDisplayName = workmateDisplayName;
        this.workmatePhotoUrl = workmatePhotoUrl;
        this.workmateRestaurantId = workmateRestaurantId;
    }

    //GETTERS

    @NonNull
    public String getWorkmateId() {
        return workmateId;
    }

    @NonNull
    public String getWorkmateDisplayName() {
        return workmateDisplayName;
    }

    @Nullable
    public String getWorkmatePhotoUrl() {
        return workmatePhotoUrl;
    }

    @Nullable
    public String getWorkmateRestaurantId() {
        return workmateRestaurantId;
    }

    //UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workmate)) return false;
        Workmate workmate = (Workmate) o;
        return Objects.equals(getWorkmateId(), workmate.getWorkmateId()) && Objects.equals(getWorkmateDisplayName(), workmate.getWorkmateDisplayName()) && Objects.equals(getWorkmatePhotoUrl(), workmate.getWorkmatePhotoUrl()) && Objects.equals(getWorkmateRestaurantId(), workmate.getWorkmateRestaurantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkmateId(), getWorkmateDisplayName(), getWorkmatePhotoUrl(), getWorkmateRestaurantId());
    }

    @NonNull
    @Override
    public String toString() {
        return "Wormate{" +
                "workmateId='" + workmateId + '\'' +
                ", workmateDisplayName='" + workmateDisplayName + '\'' +
                ", workmatePhotoUrl='" + workmatePhotoUrl + '\'' +
                ", workmateRestaurantId='" + workmateRestaurantId + '\'' +
                '}';
    }
}
