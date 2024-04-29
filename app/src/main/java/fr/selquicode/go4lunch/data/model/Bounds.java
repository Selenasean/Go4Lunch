package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Bounds {

    @SerializedName("northeast")
    @Expose
    private final LatLngLiteral northeast;

    @SerializedName("southwest")
    @Expose
    private final LatLngLiteral southwest;


    public Bounds(@NonNull LatLngLiteral northeast, @NonNull LatLngLiteral southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    //GETTERS
    @NonNull
    public LatLngLiteral getNortheast() {
        return northeast;
    }

    @NonNull
    public LatLngLiteral getSouthwest() {
        return southwest;
    }

    //METHODS UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bounds)) return false;
        Bounds bounds = (Bounds) o;
        return Objects.equals(getNortheast(), bounds.getNortheast()) && Objects.equals(getSouthwest(), bounds.getSouthwest());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNortheast(), getSouthwest());
    }

    @NonNull
    @Override
    public String toString() {
        return "Bounds{" +
                "northeast=" + northeast +
                ", southwest=" + southwest +
                '}';
    }
}
