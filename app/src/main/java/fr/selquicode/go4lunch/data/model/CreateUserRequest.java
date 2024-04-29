package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

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

    //METHOD UTILS
    @NonNull
    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", photoUserUrl='" + photoUserUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateUserRequest)) return false;
        CreateUserRequest that = (CreateUserRequest) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDisplayName(), that.getDisplayName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPhotoUserUrl(), that.getPhotoUserUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDisplayName(), getEmail(), getPhotoUserUrl());
    }
}
