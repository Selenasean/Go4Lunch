package fr.selquicode.go4lunch.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesAutocompleteResponse {

    @SerializedName("predictions")
    @Expose
    private List<PlaceAutocompletePrediction> placesPredictions;

    @SerializedName("status")
    @Expose
    private PlaceAutocompleteStatus status;




}
