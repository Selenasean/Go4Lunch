package fr.selquicode.go4lunch.data.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Geometry {

    @SerializedName("location")
    @Expose
    private LatLngLiteral location;
    @SerializedName("viewport")
    @Expose
    private Bounds viewport;


    public Geometry(LatLngLiteral location){
        this.location = location;
    }

    //public Geometry(){};

    public LatLngLiteral getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Geometry)) return false;
        Geometry geometry = (Geometry) o;
        return Objects.equals(getLocation(), geometry.getLocation()) && Objects.equals(viewport, geometry.viewport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocation(), viewport);
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "location=" + location +
                ", viewport=" + viewport +
                '}';
    }

}
