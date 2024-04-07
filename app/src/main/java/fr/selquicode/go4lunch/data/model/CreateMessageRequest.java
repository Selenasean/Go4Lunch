package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class CreateMessageRequest {

    @NonNull
    private String message;

    @NonNull
    private Timestamp dateCreated;


    public CreateMessageRequest() {
    }

    public CreateMessageRequest(
            @NonNull String message,
            @NonNull Timestamp dateCreated) {
        this.message = message;
        this.dateCreated = dateCreated;
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


    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "CreateMessageRequest{" +
                "message='" + message + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateMessageRequest)) return false;
        CreateMessageRequest that = (CreateMessageRequest) o;
        return Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getDateCreated(), that.getDateCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getDateCreated());
    }
}
