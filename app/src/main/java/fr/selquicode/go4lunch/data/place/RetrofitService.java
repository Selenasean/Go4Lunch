package fr.selquicode.go4lunch.data.place;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.selquicode.go4lunch.data.place.PlacesAPI;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final Gson gson = new GsonBuilder().setLenient().create();
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    //Allows us to get the request from PlacesNearbySearchResponseAPI using retrofit
    public static PlacesAPI getPlaceAPI(){
        return retrofit.create(PlacesAPI.class);
    }
}
