package fr.selquicode.go4lunch.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class PlaceOpeningHours {

    /**
     * Type Boolean is used here because we need to know
     * if the restaurant is open (isOpenNow() = true), is closed (isOpenNow() = false)
     * or if PlaceOpeningHours = null because the request's response doesn't have that field
     */
    @SerializedName("open_now")
    @Expose
    private Boolean isOpenNow;

    @SerializedName("weekday_text")
    @Expose
    private List<String> weekdayText;

    @SerializedName("periods")
    @Expose
    private List<PlaceOpeningHoursPeriod> periods;


    public PlaceOpeningHours(@Nullable Boolean isOpenNow,
                             @Nullable List<String> weekdayText,
                             @Nullable  List<PlaceOpeningHoursPeriod> periods){
        this.isOpenNow = isOpenNow;
        this.weekdayText = weekdayText;
        this.periods = periods;
    }

    //GETTERS
    @Nullable
    public Boolean isOpenNow() {
        return isOpenNow;
    }

    @Nullable
    public List<String> getWeekdayText() {
        return weekdayText;
    }

    @Nullable
    public List<PlaceOpeningHoursPeriod> getPeriods() {
        return periods;
    }

    //METHODS UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceOpeningHours)) return false;
        PlaceOpeningHours that = (PlaceOpeningHours) o;
        return isOpenNow() == that.isOpenNow() && getWeekdayText().equals(that.getWeekdayText()) && getPeriods().equals(that.getPeriods());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOpenNow(), getWeekdayText(), getPeriods());
    }

    @Override
    public String toString() {
        return "PlaceOpeningHours{" +
                "isOpenNow=" + isOpenNow +
                ", weekdayText=" + weekdayText +
                ", periods=" + periods +
                '}';
    }
}
