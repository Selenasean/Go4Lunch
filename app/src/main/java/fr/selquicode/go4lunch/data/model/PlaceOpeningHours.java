package fr.selquicode.go4lunch.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class PlaceOpeningHours {

    @SerializedName("open_now")
    @Expose
    private boolean isOpenNow;

    @SerializedName("weekday_text")
    @Expose
    private List<String> weekdayText;

    @SerializedName("periods")
    @Expose
    private List<PlaceOpeningHoursPeriod> periods;


    public PlaceOpeningHours(boolean isOpenNow, List<String> weekdayText, List<PlaceOpeningHoursPeriod> periods){
        this.isOpenNow = isOpenNow;
        this.weekdayText = weekdayText;
        this.periods = periods;
    }

    //GETTERS
    public boolean isOpenNow() {
        return isOpenNow;
    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }

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
