package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class PlacePhoto {

    @SerializedName("height")
    @Expose
    private double height;

    @SerializedName("html_attributions")
    @Expose
    private List<String> html_attributions;

    @SerializedName("photo-_reference")
    @Expose
    private String photo_reference;

    @SerializedName("width")
    @Expose
    private double width;


    public PlacePhoto(@NonNull double height,
                      @NonNull List<String> html_attributions,
                      @NonNull String photo_reference,
                      @NonNull double width){

        this.height = height;
        this.html_attributions = html_attributions;
        this.photo_reference = photo_reference;
        this.width =width;
    }

    //GETTERS
    @NonNull
    public double getHeight() {
        return height;
    }

    @NonNull
    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    @NonNull
    public String getPhoto_reference() {
        return photo_reference;
    }

    @NonNull
    public double getWidth() {
        return width;
    }

    //METHODS UTILS
    @Override
    public String toString() {
        return "PlacePhoto{" +
                "height=" + height +
                ", html_attributions=" + html_attributions +
                ", photo_reference='" + photo_reference + '\'' +
                ", width=" + width +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlacePhoto)) return false;
        PlacePhoto that = (PlacePhoto) o;
        return Double.compare(that.getHeight(), getHeight()) == 0 && Double.compare(that.getWidth(), getWidth()) == 0 && getHtml_attributions().equals(that.getHtml_attributions()) && getPhoto_reference().equals(that.getPhoto_reference());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeight(), getHtml_attributions(), getPhoto_reference(), getWidth());
    }

}
