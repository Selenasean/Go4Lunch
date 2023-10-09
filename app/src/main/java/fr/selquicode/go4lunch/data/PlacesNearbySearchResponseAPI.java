package fr.selquicode.go4lunch.data;

import java.util.List;

import fr.selquicode.go4lunch.model.Place;
import fr.selquicode.go4lunch.model.PlacesNearbySearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesNearbySearchResponseAPI {
    @GET("results")
    Call<PlacesNearbySearchResponse> getListOfPlaces(@Query("results")List<Place> placesList);

}
