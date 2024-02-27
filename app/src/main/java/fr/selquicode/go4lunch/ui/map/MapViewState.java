package fr.selquicode.go4lunch.ui.map;

import androidx.annotation.NonNull;

import java.util.Objects;

import fr.selquicode.go4lunch.data.model.Geometry;

public class MapViewState {

    @NonNull
    String placeId;
    @NonNull
    String name;
    boolean isWorkmatesEatingThere;

    @NonNull
    Geometry geometry;

    public MapViewState(@NonNull String placeId,
                        @NonNull String name,
                        boolean isWorkmatesEatingThere,
                        @NonNull Geometry geometry) {

        this.placeId = placeId;
        this.name = name;
        this.isWorkmatesEatingThere = isWorkmatesEatingThere;
        this.geometry = geometry;
    }

    //GETTERS
    @NonNull
    public String getPlaceId() {
        return placeId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public boolean isWorkmatesEatingThere() {
        return isWorkmatesEatingThere;
    }

    @NonNull
    public Geometry getGeometry() {
        return geometry;
    }

    //UTILS
    @NonNull
    @Override
    public String toString() {
        return "MapViewState{" +
                "placeId='" + placeId + '\'' +
                ", name='" + name + '\'' +
                ", isWorkmatesEatingThere=" + isWorkmatesEatingThere +
                ", geometry=" + geometry +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapViewState)) return false;
        MapViewState that = (MapViewState) o;
        return isWorkmatesEatingThere() == that.isWorkmatesEatingThere() && Objects.equals(getPlaceId(), that.getPlaceId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getGeometry(), that.getGeometry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceId(), getName(), isWorkmatesEatingThere(), getGeometry());
    }
}
