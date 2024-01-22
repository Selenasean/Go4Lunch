package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    @NonNull
    String id, displayName;

    @Nullable
    String restaurantId, restaurantName, photoUserUrl;

    @Nullable
    List<String> favoritePlacesId;


    public User(
            @NonNull String id,
            @NonNull String displayName,
            @Nullable String restaurantId,
            @Nullable String restaurantName,
            @Nullable String photoUserUrl,
            @Nullable List<String> favoritePlacesId
    ) {
        this.id = id;
        this.displayName = displayName;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.photoUserUrl = photoUserUrl;
        this.favoritePlacesId = favoritePlacesId;
    }

    public User() {}


    // GETTERS
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
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
        }else{
            return favoritePlacesId;
        }
    }
}
