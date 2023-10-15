package fr.selquicode.go4lunch.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

//@SuppressWarnings({"ALL", "unused"})
public class PlacesNearbySearchResponse {


    @SerializedName("results")
    @Expose
    private List<Place> results;


    public PlacesNearbySearchResponse(List<Place> results) {
        this.results = results;
    }
    
    //public PlacesNearbySearchResponse(){}

    public List<Place> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "PlacesNearbySearchResponse{" +
                "results=" + results +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlacesNearbySearchResponse)) return false;
        PlacesNearbySearchResponse that = (PlacesNearbySearchResponse) o;
        return Objects.equals(getResults(), that.getResults());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResults());
    }
}
