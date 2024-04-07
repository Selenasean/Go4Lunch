package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class UserSender {

    @NonNull
    String uid, userName;

    @Nullable
    String imageUrl;

    public UserSender(){}

    public UserSender(@NonNull String uid,
                      @NonNull String userName,
                      @NonNull String imageUrl) {
        this.uid = uid;
        this.userName = userName;
        this.imageUrl = imageUrl;
    }

    //GETTER
    @NonNull
    public String getUid() {
        return uid;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "UserSender{" +
                "uid='" + uid + '\'' +
                ", userName='" + userName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSender)) return false;
        UserSender that = (UserSender) o;
        return Objects.equals(getUid(), that.getUid()) && Objects.equals(getUserName(), that.getUserName()) && Objects.equals(getImageUrl(), that.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getUserName(), getImageUrl());
    }
}
