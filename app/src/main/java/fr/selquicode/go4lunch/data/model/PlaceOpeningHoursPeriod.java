package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PlaceOpeningHoursPeriod {

    @SerializedName("open")
    @Expose
    private PlaceOpeningHoursPeriodDetail open;

    @SerializedName("close")
    @Expose
    private PlaceOpeningHoursPeriodDetail close;


    public PlaceOpeningHoursPeriod(@NonNull PlaceOpeningHoursPeriodDetail open, @Nullable PlaceOpeningHoursPeriodDetail close){
        this.close = close;
        this.open = open;
    }

    //GETTERS
    @NonNull
    public PlaceOpeningHoursPeriodDetail getOpen() {
        return open;
    }

    @Nullable
    public PlaceOpeningHoursPeriodDetail getClose() {
        return close;
    }

    //METHODS UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceOpeningHoursPeriod)) return false;
        PlaceOpeningHoursPeriod that = (PlaceOpeningHoursPeriod) o;
        return getOpen().equals(that.getOpen()) && getClose().equals(that.getClose());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOpen(), getClose());
    }

    @Override
    public String toString() {
        return "PlaceOpeningHoursPeriod{" +
                "open=" + open +
                ", close=" + close +
                '}';
    }


}
