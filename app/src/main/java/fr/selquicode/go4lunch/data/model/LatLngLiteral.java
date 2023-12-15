package fr.selquicode.go4lunch.data.model;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LatLngLiteral {

    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double  lng;


    public LatLngLiteral(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    //GETTERS
    public double getLat() {
        return lat;
    }

    public double getLng() {
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

    @NonNull
    @Override
    public String toString() {
        return "LatLngLiteral{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
