package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PlaceAutocompleteTerm {

    @SerializedName("offset")
    @Expose
    private final double offset;

    @SerializedName("value")
    @Expose
    private final String value;

    public PlaceAutocompleteTerm(double offset, String value) {
        this.offset = offset;
        this.value = value;
    }

    //GETTERS

    public double getOffset() {
        return offset;
    }

    public String getValue() {
        return value;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "PlaceAutocompleteTerm{" +
                "offset=" + offset +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceAutocompleteTerm)) return false;
        PlaceAutocompleteTerm that = (PlaceAutocompleteTerm) o;
        return Double.compare(that.getOffset(), getOffset()) == 0 && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOffset(), getValue());
    }
}
