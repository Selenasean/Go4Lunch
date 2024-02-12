package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class PlaceAutocompleteStructuredFormat {

    @SerializedName("main_text")
    @Expose
    private final String main_text;

    @SerializedName("main_text_matched_substrings")
    @Expose
    private final List<PlaceAutocompleteMatchedSubstrings> main_text_matched_substrings;

    public PlaceAutocompleteStructuredFormat(
            String main_text,
            List<PlaceAutocompleteMatchedSubstrings> main_text_matched_substrings) {
        this.main_text = main_text;
        this.main_text_matched_substrings = main_text_matched_substrings;
    }

    //GETTERS
    public String getMain_text() {
        return main_text;
    }

    public List<PlaceAutocompleteMatchedSubstrings> getMain_text_matched_substrings() {
        return main_text_matched_substrings;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "PlaceAutocompleteStructuredFormat{" +
                "main_text='" + main_text + '\'' +
                ", main_text_matched_substrings=" + main_text_matched_substrings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceAutocompleteStructuredFormat)) return false;
        PlaceAutocompleteStructuredFormat that = (PlaceAutocompleteStructuredFormat) o;
        return Objects.equals(main_text, that.main_text) && Objects.equals(main_text_matched_substrings, that.main_text_matched_substrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(main_text, main_text_matched_substrings);
    }
}
