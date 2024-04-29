package fr.selquicode.go4lunch.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PlaceOpeningHoursPeriodDetail {

    @SerializedName("day")
    @Expose
    private final Double day;

    @SerializedName("time")
    @Expose
    private final String time;

    @SerializedName("date")
    @Expose
    private final String date;


    public PlaceOpeningHoursPeriodDetail(double day, @NonNull String time, @Nullable String date){
        this.day = day;
        this.date = date;
        this.time = time;
    }

    //GETTERS
    public double getDay() {
        return day;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    @Nullable
    public String getDate() {
        return date;
    }

    // METHODS UTILS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceOpeningHoursPeriodDetail)) return false;
        PlaceOpeningHoursPeriodDetail that = (PlaceOpeningHoursPeriodDetail) o;
        return Objects.equals(getDay(), that.getDay()) && Objects.equals(getTime(), that.getTime()) && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDay(), getTime(), getDate());
    }

    @NonNull
    @Override
    public String toString() {
        return "PlaceOpeningHoursPeriodDetail{" +
                "day=" + day +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
