package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CreateUserRequest {

    @NonNull
    String id, displayName;

    @Nullable
    String photoUserUrl;

    public CreateUserRequest(@NonNull String id, @NonNull String displayName, @Nullable String photoUserUrl) {
        this.id = id;
        this.displayName = displayName;
        this.photoUserUrl = photoUserUrl;
    }

    //GETTERS
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    public String getPhotoUserUrl() {
        return photoUserUrl;
    }
}
