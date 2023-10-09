package fr.selquicode.go4lunch.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final Gson gson = new GsonBuilder().setLenient().create();
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                                            "json?keyword=cruise&location=-33.8670522%2C151.1957362&radius=1500" +
                                            "&type=restaurant&key=" + "${MAPS_API_KEY}";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static PlacesNearbySearchResponseAPI getPlaceAPI(){ return retrofit.create(PlacesNearbySearchResponseAPI.class);}
}
