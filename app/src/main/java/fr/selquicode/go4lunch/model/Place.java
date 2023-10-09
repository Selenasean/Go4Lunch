package fr.selquicode.go4lunch.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Place {

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;


    public Place(Geometry geometry){
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        Place place = (Place) o;
        return Objects.equals(getGeometry(), place.getGeometry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGeometry());
    }

    @Override
    public String toString() {
        return "Place{" +
                "geometry=" + geometry +
                '}';
    }

}
