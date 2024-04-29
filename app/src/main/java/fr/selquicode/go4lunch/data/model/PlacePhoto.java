package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class PlacePhoto {

    @SerializedName("height")
    @Expose
    private final double height;

    @SerializedName("html_attributions")
    @Expose
    private final List<String> html_attributions;

    @SerializedName("photo_reference")
    @Expose
    private final String photo_reference;

    @SerializedName("width")
    @Expose
    private final double width;


    public PlacePhoto(double height,
                      @NonNull List<String> html_attributions,
                      @NonNull String photo_reference,
                      double width){

        this.height = height;
        this.html_attributions = html_attributions;
        this.photo_reference = photo_reference;
        this.width =width;
    }

    //GETTERS
    public double getHeight() {
        return height;
    }

    @NonNull
    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    @Nullable
    public String getPhoto_reference() {
        return photo_reference;
    }

    public double getWidth() {
        return width;
    }

    //METHODS UTILS
    @NonNull
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
        return Double.compare(getHeight(), that.getHeight()) == 0 && Double.compare(getWidth(), that.getWidth()) == 0 && Objects.equals(getHtml_attributions(), that.getHtml_attributions()) && Objects.equals(getPhoto_reference(), that.getPhoto_reference());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeight(), getHtml_attributions(), getPhoto_reference(), getWidth());
    }
}
