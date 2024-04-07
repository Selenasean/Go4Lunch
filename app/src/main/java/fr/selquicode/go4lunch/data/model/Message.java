package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class Message {

    private String message;

    @NonNull
    private Timestamp dateCreated;

    @NonNull
    private UserSender userSender;

    public Message() {
    }

    public Message(@NonNull String message,
                   @NonNull Timestamp dateCreated,
                   @NonNull UserSender userSender) {
        this.message = message;
        this.dateCreated = dateCreated;
        this.userSender = userSender;
    }

    //GETTER

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    @NonNull
    public UserSender getUserSender() {
        return userSender;
    }

    //UTILS

    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", dateCreated=" + dateCreated +
                ", userSender=" + userSender +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return Objects.equals(getMessage(), message1.getMessage()) && Objects.equals(getDateCreated(), message1.getDateCreated()) && Objects.equals(getUserSender(), message1.getUserSender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getDateCreated(), getUserSender());
    }
}
