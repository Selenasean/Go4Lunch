package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PlaceAutocompleteStatus {

    @SerializedName("status")
    @Expose
    private final String status;

    public PlaceAutocompleteStatus(String status) {
        this.status = status;
    }

    //GETTERS

    public String getStatus() {
        return status;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "PlaceAutocompleteStatus{" +
                "status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceAutocompleteStatus)) return false;
        PlaceAutocompleteStatus that = (PlaceAutocompleteStatus) o;
        return Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus());
    }
}
