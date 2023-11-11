package fr.selquicode.go4lunch.data.model;


import androidx.annotation.NonNull;
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

    @SerializedName("adr_adress")
    @Expose
    private String address;

    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;

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
                 @Nullable String address,
                 @Nullable String formattedAddress,
                 @Nullable String name,
                 @Nullable PlaceOpeningHours opening,
                 @Nullable List<PlacePhoto> placePhoto,
                 @Nullable double rating){

        this.placeId = placeId;
        this.geometry = geometry;
        this.address = address;
        this.formattedAddress = formattedAddress;
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
    public String getAddress() {
        return address;
    }

    @Nullable
    public String getFormattedAddress() {
        return formattedAddress;
    }

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
       return Double.compare(place.getRating(), getRating()) == 0 && getPlaceId().equals(place.getPlaceId()) && getGeometry().equals(place.getGeometry()) && getAddress().equals(place.getAddress()) && getFormattedAddress().equals(place.getFormattedAddress()) && getName().equals(place.getName()) && getOpening().equals(place.getOpening()) && getPlacePhotos().equals(place.getPlacePhotos());
   }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceId(), getGeometry(), getAddress(), getFormattedAddress(), getName(), getOpening(), getPlacePhotos(), getRating());
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeId='" + placeId + '\'' +
                ", geometry=" + geometry +
                ", address='" + address + '\'' +
                ", formattedAddress='" + formattedAddress + '\'' +
                ", name='" + name + '\'' +
                ", opening=" + opening +
                ", placePhoto=" + placePhoto +
                ", rating=" + rating +
                '}';
    }

}
