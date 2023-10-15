package fr.selquicode.go4lunch.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Bounds {

    @SerializedName("northeast")
    @Expose
    private LatLngLiteral northeast;
    @SerializedName("southwest")
    @Expose
    private LatLngLiteral southwest;


    public Bounds(LatLngLiteral northeast, LatLngLiteral southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    //public Bounds(){};

    public LatLngLiteral getNortheast() {
        return northeast;
    }

    public LatLngLiteral getSouthwest() {
        return southwest;
    }

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

    @Override
    public String toString() {
        return "Bounds{" +
                "northeast=" + northeast +
                ", southwest=" + southwest +
                '}';
    }
}
