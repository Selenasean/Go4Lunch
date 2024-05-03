package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User {

    @NonNull
    private String id, displayName, email;

    @Nullable
    private String restaurantId, restaurantName, photoUserUrl, restaurantAddress;

    @Nullable
     private List<String> favoritePlacesId;

    public User(
            @NonNull String id,
            @NonNull String displayName,
            @NonNull String email,
            @Nullable String restaurantId,
            @Nullable String restaurantName,
            @Nullable String restaurantAddress,
            @Nullable String photoUserUrl,
            @Nullable List<String> favoritePlacesId
    ) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.photoUserUrl = photoUserUrl;
        this.favoritePlacesId = favoritePlacesId;
    }

    public User() {
    }

    // GETTERS
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }
    @Nullable
    public String getRestaurantId() {
        return restaurantId;
    }

    @Nullable
    public String getRestaurantName() {
        return restaurantName;
    }

    @Nullable
    public String getPhotoUserUrl() {
        return photoUserUrl;
    }

    @NonNull
    public List<String> getFavoritePlacesId() {
        if(favoritePlacesId == null){
            return Collections.emptyList();
        }
        return favoritePlacesId;
    }

    @Nullable
    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", photoUserUrl='" + photoUserUrl + '\'' +
                ", restaurantAddress='" + restaurantAddress + '\'' +
                ", favoritePlacesId=" + favoritePlacesId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getDisplayName(), user.getDisplayName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getRestaurantId(), user.getRestaurantId()) && Objects.equals(getRestaurantName(), user.getRestaurantName()) && Objects.equals(getPhotoUserUrl(), user.getPhotoUserUrl()) && Objects.equals(getRestaurantAddress(), user.getRestaurantAddress()) && Objects.equals(getFavoritePlacesId(), user.getFavoritePlacesId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDisplayName(), getEmail(), getRestaurantId(), getRestaurantName(), getPhotoUserUrl(), getRestaurantAddress(), getFavoritePlacesId());
    }
}
