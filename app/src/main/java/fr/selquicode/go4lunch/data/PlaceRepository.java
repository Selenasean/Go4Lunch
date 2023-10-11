package fr.selquicode.go4lunch.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.selquicode.go4lunch.model.Place;
import fr.selquicode.go4lunch.model.PlacesNearbySearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceRepository {

    private PlacesNearbySearchResponseAPI apiService;
    private static String TAG= "PlaceRepository";

    public PlaceRepository(PlacesNearbySearchResponseAPI apiService){
        this.apiService = apiService;
    }

    public LiveData<List<Place>> getPlaces(){
        MutableLiveData<List<Place>> placesMutableLiveData = new MutableLiveData<>();
        Log.i(TAG, "avant le enqueue");
        apiService.getListOfPlaces().enqueue(new Callback<PlacesNearbySearchResponse>() {
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
}
