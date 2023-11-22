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

    @SerializedName("international_phone_number")
    @Expose
    private String phone;

    @SerializedName("website")
    @Expose
    private String website;

    public Place(@Nullable String placeId,
                 @Nullable Geometry geometry,
                 @Nullable String vicinity,
                 @Nullable String name,
                 @Nullable PlaceOpeningHours opening,
                 @Nullable List<PlacePhoto> placePhoto,
                 @Nullable double rating,
                 @Nullable String phone,
                 @Nullable String website){

        this.placeId = placeId;
        this.geometry = geometry;
        this.vicinity = vicinity;
        this.name = name;
        this.opening = opening;
        this.placePhoto = placePhoto;
        this.rating = rating;
        this.phone = phone;
        this.website = website;

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

    @Nullable
    public String getPhone() {
        return phone;
    }

    @Nullable
    public String getWebsite() {
        return website;
    }

   //METHODS UTILS
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
               ", phone='" + phone + '\'' +
               ", website='" + website + '\'' +
               '}';
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        Place place = (Place) o;
        return Double.compare(place.getRating(), getRating()) == 0 && Objects.equals(getPlaceId(), place.getPlaceId()) && Objects.equals(getGeometry(), place.getGeometry()) && Objects.equals(getVicinity(), place.getVicinity()) && Objects.equals(getName(), place.getName()) && Objects.equals(getOpening(), place.getOpening()) && Objects.equals(placePhoto, place.placePhoto) && Objects.equals(getPhone(), place.getPhone()) && Objects.equals(getWebsite(), place.getWebsite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceId(), getGeometry(), getVicinity(), getName(), getOpening(), placePhoto, getRating(), getPhone(), getWebsite());
    }

}
