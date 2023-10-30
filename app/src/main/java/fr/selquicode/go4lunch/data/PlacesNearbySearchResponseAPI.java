package fr.selquicode.go4lunch.data;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.data.model.PlacesNearbySearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlacesNearbySearchResponseAPI {
    @GET("nearbysearch/json?location=48.834275%2C2.63731&radius=5000&type=restaurant&key=" + BuildConfig.MAPS_API_KEY)
    Call<PlacesNearbySearchResponse> getListOfPlaces();

}
