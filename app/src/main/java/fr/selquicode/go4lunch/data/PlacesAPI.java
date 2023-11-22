package fr.selquicode.go4lunch.data;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.data.model.PlaceDetailsResponse;
import fr.selquicode.go4lunch.data.model.PlacesNearbySearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesAPI {
    //request to get a list of places
    @GET("nearbysearch/json?radius=5000&type=restaurant&key=" + BuildConfig.MAPS_API_KEY)
    Call<PlacesNearbySearchResponse> getListOfPlaces(@Query("location") String location);

    @GET("details/json?key=" + BuildConfig.MAPS_API_KEY)
    Call<PlaceDetailsResponse> getDetailOfPlace(@Query("place_id") String placeId);


}
