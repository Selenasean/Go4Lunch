package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PlaceAutocompletePrediction {

    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("place_id")
    @Expose String placeId;

    public PlaceAutocompletePrediction(
            String description,
            String placeId) {
        this.description = description;
        this.placeId = placeId;
    }

    //GETTERS

    public String getDescription() {
        return description;
    }



    public String getPlaceId() {
        return placeId;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "PlaceAutocompletePrediction{" +
                "description='" + description + '\'' +
                ", place_id='" + placeId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceAutocompletePrediction)) return false;
        PlaceAutocompletePrediction that = (PlaceAutocompletePrediction) o;
        return Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPlaceId(), that.getPlaceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getPlaceId());
    }
}
