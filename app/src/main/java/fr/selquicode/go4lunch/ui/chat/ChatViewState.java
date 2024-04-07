package fr.selquicode.go4lunch.ui.chat;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ChatViewState {

    @NonNull
    String workmatePictureUrl, workmateName, userPictureUrl, userName;

    public ChatViewState(@NonNull String workmatePictureUrl,
                         @NonNull String workmateName,
                         @NonNull String userPictureUrl,
                         @NonNull String userName) {

    }

    //GETTERS
    @NonNull
    public String getWorkmatePictureUrl() {
        return workmatePictureUrl;
    }

    @NonNull
    public String getWorkmateName() {
        return workmateName;
    }

    @NonNull
    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "ChatViewState{" +
                "workmatePictureUrl='" + workmatePictureUrl + '\'' +
                ", workmateName='" + workmateName + '\'' +
                ", userPictureUrl='" + userPictureUrl + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatViewState)) return false;
        ChatViewState that = (ChatViewState) o;
        return Objects.equals(getWorkmatePictureUrl(), that.getWorkmatePictureUrl()) && Objects.equals(getWorkmateName(), that.getWorkmateName()) && Objects.equals(getUserPictureUrl(), that.getUserPictureUrl()) && Objects.equals(getUserName(), that.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkmatePictureUrl(), getWorkmateName(), getUserPictureUrl(), getUserName());
    }
}
