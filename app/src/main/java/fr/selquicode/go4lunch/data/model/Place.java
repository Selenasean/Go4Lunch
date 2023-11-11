package fr.selquicode.go4lunch.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Place {

    @SerializedName("place_id")
    @Expose
    private String placeId;

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("opening_hours")
    @Expose
    private PlaceOpeningHours opening;

    @SerializedName("photos")
    @Expose
    private List<PlacePhoto> placePhoto;

    @SerializedName("rating")
    @Expose
    private double rating;


    public Place(@Nullable String placeId,
                 @Nullable Geometry geometry,
                 @Nullable String vicinity,
                 @Nullable String name,
                 @Nullable PlaceOpeningHours opening,
                 @Nullable List<PlacePhoto> placePhoto,
                 @Nullable double rating){

        this.placeId = placeId;
        this.geometry = geometry;
        this.vicinity = vicinity;
        this.name = name;
        this.opening = opening;
        this.placePhoto = placePhoto;
        this.rating = rating;
    }

    //GETTERS
    @Nullable
    public String getPlaceId() {
        return placeId;
    }

    @Nullable
    public Geometry getGeometry() {
        return geometry;
    }

    @Nullable
    public String getVicinity(){ return vicinity; }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public PlaceOpeningHours getOpening() {
        return opening;
    }

    @Nullable
    public List<PlacePhoto> getPlacePhotos() {
        return placePhoto;
    }

    @Nullable
    public double getRating() {
        return rating;
    }

   //METHODS UTILS
   @Override
   public boolean equals(Object o) {
       if (this == o) return true;
       if (!(o instanceof Place)) return false;
       Place place = (Place) o;
       return Double.compare(place.getRating(), getRating()) == 0 && getPlaceId().equals(place.getPlaceId()) && getGeometry().equals(place.getGeometry()) && getVicinity().equals(place.getVicinity()) && getName().equals(place.getName()) && getOpening().equals(place.getOpening()) && getPlacePhotos().equals(place.getPlacePhotos());
   }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceId(), getGeometry(), getVicinity(), getName(), getOpening(), getPlacePhotos(), getRating());
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeId='" + placeId + '\'' +
                ", geometry=" + geometry +
                ", vicinity='" + vicinity + '\'' +
                ", name='" + name + '\'' +
                ", opening=" + opening +
                ", placePhoto=" + placePhoto +
                ", rating=" + rating +
                '}';
    }

}
