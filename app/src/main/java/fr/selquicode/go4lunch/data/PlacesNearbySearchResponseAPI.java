package fr.selquicode.go4lunch.data;

import java.util.List;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.model.Place;
import fr.selquicode.go4lunch.model.PlacesNearbySearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesNearbySearchResponseAPI {
    @GET("nearbysearch/json?location=-34%2C151&radius=10000&type=restaurant&key=" + BuildConfig.MAPS_API_KEY)
    Call<PlacesNearbySearchResponse> getListOfPlaces();

}
