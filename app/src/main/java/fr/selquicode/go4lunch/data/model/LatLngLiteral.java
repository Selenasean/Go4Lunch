package fr.selquicode.go4lunch.data.model;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LatLngLiteral {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double  lng;


    public LatLngLiteral(@Nullable double lat, @Nullable  double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    //GETTERS
    @Nullable
    public Double getLat() {
        return lat;
    }

    @Nullable
    public Double getLng() {
        return lng;
    }

    //METHODS UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LatLngLiteral)) return false;
        LatLngLiteral that = (LatLngLiteral) o;
        return Objects.equals(getLat(), that.getLat()) && Objects.equals(getLng(), that.getLng());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLat(), getLng());
    }

    @Override
    public String toString() {
        return "LatLngLiteral{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
