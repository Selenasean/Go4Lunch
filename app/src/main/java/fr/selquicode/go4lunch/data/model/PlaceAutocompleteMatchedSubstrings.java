package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PlaceAutocompleteMatchedSubstrings {

    @SerializedName("length")
    @Expose
    private final double length;

    @SerializedName("offset")
    private final double offset;

    public PlaceAutocompleteMatchedSubstrings(double length, double offset) {
        this.length = length;
        this.offset = offset;
    }

    //GETTERS
    public double getLength() {
        return length;
    }

    public double getOffset() {
        return offset;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "PlaceAutocompleteMatchedSubstrings{" +
                "length=" + length +
                ", offset=" + offset +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceAutocompleteMatchedSubstrings)) return false;
        PlaceAutocompleteMatchedSubstrings that = (PlaceAutocompleteMatchedSubstrings) o;
        return Double.compare(that.getLength(), getLength()) == 0 && Double.compare(that.getOffset(), getOffset()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLength(), getOffset());
    }
}
