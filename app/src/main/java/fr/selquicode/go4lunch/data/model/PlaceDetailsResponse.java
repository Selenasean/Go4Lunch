package fr.selquicode.go4lunch.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class PlaceDetailsResponse {

    @SerializedName("result")
    @Expose
    private Place result;

    public PlaceDetailsResponse(Place result) {
        this.result = result;
    }

    //GETTER
    public Place getResult() { return result; }

    //METHOD UTILS
    @Override
    public String toString() {
        return "PlaceDetailsResponse{" +
                "result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceDetailsResponse)) return false;
        PlaceDetailsResponse that = (PlaceDetailsResponse) o;
        return Objects.equals(getResult(), that.getResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResult());
    }
}
