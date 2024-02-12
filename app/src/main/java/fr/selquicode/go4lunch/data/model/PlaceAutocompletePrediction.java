package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class PlaceAutocompletePrediction {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("matched_substrings")
    @Expose
    private List<PlaceAutocompleteMatchedSubstrings> matched_substrings;

    @SerializedName("structured_formatting")
    @Expose
    private PlaceAutocompleteStructuredFormat structured_formatting;

    @SerializedName("terms")
    @Expose
    private List<PlaceAutocompleteTerm> terms;

    @SerializedName("place_id")
    @Expose String place_id;

    public PlaceAutocompletePrediction(
            String description,
            List<PlaceAutocompleteMatchedSubstrings> matched_substrings,
            PlaceAutocompleteStructuredFormat structured_formatting,
            List<PlaceAutocompleteTerm> terms,
            String place_id) {
        this.description = description;
        this.matched_substrings = matched_substrings;
        this.structured_formatting = structured_formatting;
        this.terms = terms;
        this.place_id = place_id;
    }

    //GETTERS

    public String getDescription() {
        return description;
    }

    public List<PlaceAutocompleteMatchedSubstrings> getMatched_substrings() {
        return matched_substrings;
    }

    public PlaceAutocompleteStructuredFormat getStructured_formatting() {
        return structured_formatting;
    }

    public List<PlaceAutocompleteTerm> getTerms() {
        return terms;
    }

    public String getPlace_id() {
        return place_id;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "PlaceAutocompletePrediction{" +
                "description='" + description + '\'' +
                ", matched_substrings=" + matched_substrings +
                ", structured_formatting=" + structured_formatting +
                ", terms=" + terms +
                ", place_id='" + place_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceAutocompletePrediction)) return false;
        PlaceAutocompletePrediction that = (PlaceAutocompletePrediction) o;
        return Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getMatched_substrings(), that.getMatched_substrings()) && Objects.equals(getStructured_formatting(), that.getStructured_formatting()) && Objects.equals(getTerms(), that.getTerms()) && Objects.equals(getPlace_id(), that.getPlace_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getMatched_substrings(), getStructured_formatting(), getTerms(), getPlace_id());
    }
}
