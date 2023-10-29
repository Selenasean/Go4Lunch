package fr.selquicode.go4lunch.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PlaceOpeningHoursPeriodDetail {

    @SerializedName("day")
    @Expose
    private Double day;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("date")
    @Expose
    private String date;


    public PlaceOpeningHoursPeriodDetail(Double day, String time, String date){
        this.day = day;
        this.date = date;
        this.time = time;
    }

    //GETTERS
    public Double getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    // METHODS UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceOpeningHoursPeriodDetail)) return false;
        PlaceOpeningHoursPeriodDetail that = (PlaceOpeningHoursPeriodDetail) o;
        return getDay().equals(that.getDay()) && getTime().equals(that.getTime()) && getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDay(), getTime(), getDate());
    }

    @Override
    public String toString() {
        return "PlaceOpeningHoursPeriodDetail{" +
                "day=" + day +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
