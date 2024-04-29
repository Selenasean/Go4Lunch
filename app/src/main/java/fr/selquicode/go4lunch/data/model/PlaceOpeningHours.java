package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PlaceOpeningHours {

    /**
     * Type Boolean is used here because we need to know
     * if the restaurant is open (isOpenNow() = true), is closed (isOpenNow() = false)
     * or if PlaceOpeningHours = null because the request's response doesn't have that field
     */
    @SerializedName("open_now")
    @Expose
    private final Boolean isOpenNow;


    public PlaceOpeningHours(@Nullable Boolean isOpenNow) {
        this.isOpenNow = isOpenNow;
    }

    //GETTERS
    @Nullable
    public Boolean isOpenNow() {
        return isOpenNow;
    }


    //METHODS UTILS
    @NonNull
    @Override
    public String toString() {
        return "PlaceOpeningHours{" +
                "isOpenNow=" + isOpenNow +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceOpeningHours)) return false;
        PlaceOpeningHours that = (PlaceOpeningHours) o;
        return Objects.equals(isOpenNow, that.isOpenNow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOpenNow);
    }
}
