package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CreateUserRequest {

    @NonNull
    String id, displayName, email;

    @Nullable
    String photoUserUrl;

    public CreateUserRequest(
            @NonNull String id,
            @NonNull String displayName,
            @NonNull String email,
            @Nullable String photoUserUrl) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
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

    @NonNull
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getPhotoUserUrl() {
        return photoUserUrl;
    }
}
