package fr.selquicode.go4lunch.data.place;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.model.PlaceAutocompletePrediction;
import fr.selquicode.go4lunch.data.model.PlaceDetailsResponse;
import fr.selquicode.go4lunch.data.model.PlacesAutocompleteResponse;
import fr.selquicode.go4lunch.data.model.PlacesNearbySearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceRepository {

    private final PlacesAPI apiService;
    private static final String TAG= "PlaceRepository";
    private final Map<String, List<Place>> cachedList = new HashMap<>();
    private final Map<String, Place> cachedPlaceDetail = new HashMap<>();
    MutableLiveData<List<String>> searchedPlacesMutableLiveData = new MutableLiveData<>();

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

        //get data from the cache, if = null means that there no list in memory
        //if cache != null means that there is already a list in memory
        List<Place> response = cachedList.get(locationString);
        if(response != null ){
            Log.i(TAG, "returning cached list of places");
            placesMutableLiveData.setValue(response);
        }else{
            apiService.getListOfPlaces(locationString).enqueue(new Callback<PlacesNearbySearchResponse>() {
                @Override
                public void onResponse(@NonNull Call<PlacesNearbySearchResponse> call,@NonNull Response<PlacesNearbySearchResponse> response) {
                    Log.i(TAG, "onResponse requete");
                    if(response.isSuccessful() && response.body() != null){
                        List<Place> responseRequest = response.body().getResults();
                        cachedList.put(locationString, responseRequest);
                        placesMutableLiveData.setValue(responseRequest);
                    }else{
                        Log.e(TAG, "else onresponse");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PlacesNearbySearchResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure");
                    t.printStackTrace();
                }
            });
        }
        return placesMutableLiveData;
    }

    /**
     * To get details of a place
     * @param placeId of the place we are looking for
     * @return details of the place type LiveData
     */
    public LiveData<Place> getPlaceDetails(String placeId){
        MutableLiveData<Place> placeDetailsMutableLiveData = new MutableLiveData<>();

        Place response = cachedPlaceDetail.get(placeId);
        if(response != null) {
            Log.i(TAG, "returning cached detail");
            placeDetailsMutableLiveData.setValue(response);
        }else{
            apiService.getDetailOfPlace(placeId).enqueue(new Callback<PlaceDetailsResponse>() {
                @Override
                public void onResponse(@NonNull Call<PlaceDetailsResponse> call,@NonNull Response<PlaceDetailsResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Place responseRequest = response.body().getResult();
                        cachedPlaceDetail.put(placeId, responseRequest);
                        placeDetailsMutableLiveData.setValue(responseRequest);
                    }else{
                        Log.e(TAG,"else onresponse place's details");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PlaceDetailsResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure place's details");
                    t.printStackTrace();
                }
            });
        }

        return placeDetailsMutableLiveData;
    }

    /**
     * To get a list of places corresponding to the user's search
     * @param search a string of the user's search
     * @param location of the user currently logged
     */
    public void searchedPlaces(String search, Location location){
        String locationString = location.getLatitude() + "%2C" + location.getLatitude();
        apiService.getSearchedPlaces(search, locationString).enqueue(new Callback<PlacesAutocompleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlacesAutocompleteResponse> call, @NonNull Response<PlacesAutocompleteResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<String> placeIdList = new ArrayList<>();
                    for(PlaceAutocompletePrediction place : response.body().getPlacesPredictions()){
                        placeIdList.add(place.getPlaceId());
                        Log.i("placeRepo", "place.getDescription = " + place.getDescription());
                    }
                    searchedPlacesMutableLiveData.setValue(placeIdList);
                }else{
                    Log.e(TAG,"else onresponse placeAutoCompletePrediction details");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlacesAutocompleteResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<String>> getSearchedPlaces(){
        return searchedPlacesMutableLiveData;
    }
}
