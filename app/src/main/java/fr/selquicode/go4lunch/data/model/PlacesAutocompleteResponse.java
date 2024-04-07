package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class PlacesAutocompleteResponse {

    @SerializedName("predictions")
    @Expose
    private final List<PlaceAutocompletePrediction> placesPredictions;


    public PlacesAutocompleteResponse(List<PlaceAutocompletePrediction> placesPredictions) {
        this.placesPredictions = placesPredictions;

    }

    //GETTERS

    public List<PlaceAutocompletePrediction> getPlacesPredictions() {
        return placesPredictions;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "PlacesAutocompleteResponse{" +
                "placesPredictions=" + placesPredictions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlacesAutocompleteResponse)) return false;
        PlacesAutocompleteResponse that = (PlacesAutocompleteResponse) o;
        return Objects.equals(getPlacesPredictions(), that.getPlacesPredictions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlacesPredictions());
    }
}
