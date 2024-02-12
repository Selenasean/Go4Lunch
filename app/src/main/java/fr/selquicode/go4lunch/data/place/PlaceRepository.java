package fr.selquicode.go4lunch.data.place;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlaceDetailsResponse;
import fr.selquicode.go4lunch.data.model.PlacesNearbySearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceRepository {

    private final PlacesAPI apiService;
    private static final String TAG= "PlaceRepository";

    public PlaceRepository(PlacesAPI apiService){
        this.apiService = apiService;
    }

    /**
     * To get all places
     * @param location we are searching for
     * @return list of places type LiveData
     */
    public LiveData<List<Place>> getPlaces(Location location){
        MutableLiveData<List<Place>> placesMutableLiveData = new MutableLiveData<>();

        String locationString = location.getLatitude() + "," + location.getLongitude();
        apiService.getListOfPlaces(locationString).enqueue(new Callback<PlacesNearbySearchResponse>() {
            @Override
            public void onResponse(Call<PlacesNearbySearchResponse> call, Response<PlacesNearbySearchResponse> response) {
                Log.i(TAG, "onResponse requete");
                if(response.isSuccessful() && response.body() != null){
                    placesMutableLiveData.setValue(response.body().getResults());
                }else{
                    Log.e(TAG, "else onresponse");
                }
            }

            @Override
            public void onFailure(Call<PlacesNearbySearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure");
                t.printStackTrace();
            }
        });

        return placesMutableLiveData;
    }

    /**
     * To get details of a place
     * @param placeId of the place we are looking for
     * @return details of the place type LiveData
     */
    public LiveData<Place> getPlaceDetails(String placeId){
        MutableLiveData<Place> placeDetailsMutableLiveData = new MutableLiveData<>();

        apiService.getDetailOfPlace(placeId).enqueue(new Callback<PlaceDetailsResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    placeDetailsMutableLiveData.setValue(response.body().getResult());
                }else{
                    Log.e(TAG,"else onresponse place's details");
                }
            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure place's details");
                t.printStackTrace();
            }
        });

        return placeDetailsMutableLiveData;
    }

    //TODO : method to request PlaceAutocomplete with location & string in parameters
    // return list of places type LiveData
    // compare result from request's response and list retrieved by the getPlaces()

}
