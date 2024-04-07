package fr.selquicode.go4lunch.data.place;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.data.model.PlaceDetailsResponse;
import fr.selquicode.go4lunch.data.model.PlacesAutocompleteResponse;
import fr.selquicode.go4lunch.data.model.PlacesNearbySearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesAPI {

    //request to get a list of places
    @GET("nearbysearch/json?radius=5000&type=restaurant&key=" + BuildConfig.MAPS_API_KEY)
    Call<PlacesNearbySearchResponse> getListOfPlaces(@Query("location") String location);

    //request to get details of a place
    @GET("details/json?key=" + BuildConfig.MAPS_API_KEY)
    Call<PlaceDetailsResponse> getDetailOfPlace(@Query("place_id") String placeId);

    //request to get list of place according user search
    @GET("autocomplete/json?radius=5000&type=restaurant&key=" + BuildConfig.MAPS_API_KEY)
    Call<PlacesAutocompleteResponse> getSearchedPlaces(@Query("input") String search, @Query("location") String location);
}
