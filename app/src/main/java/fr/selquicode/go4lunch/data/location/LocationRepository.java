package fr.selquicode.go4lunch.data.location;

import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;

public class LocationRepository {

    private static final int LOCATION_REQUEST_INTERVAL_MS = 10_000;
    private static final float SMALLEST_DISPLACEMENT_THRESHOLD_METER = 25;

    @NonNull
    private final FusedLocationProviderClient fusedLocationProviderClient;

    @NonNull
    private final MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>();

    private LocationCallback callback;

    /**
     * Constructor
     * @param fusedLocationProviderClient allows to access user's position
     */
    public LocationRepository(@NonNull FusedLocationProviderClient fusedLocationProviderClient){
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    /**
     * To get user's position
     * @return user's position type LiveData
     */
    public LiveData<Location> getLocationLiveData(){
        return locationMutableLiveData;
    }

    /**
     * To start requesting user's localisation
     */
    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public void startLocationRequest() {
        //if LocationCallback is null
        if (callback == null) {
            callback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    //store latest version of the user's position
                    locationMutableLiveData.setValue(location);
                }
            };
        }
        //put the callback in FusedLocationProviderClient
        fusedLocationProviderClient.removeLocationUpdates(callback);

        fusedLocationProviderClient.requestLocationUpdates(
                new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_REQUEST_INTERVAL_MS)
                        .setMinUpdateDistanceMeters(SMALLEST_DISPLACEMENT_THRESHOLD_METER)
                        .build(),
                callback,
                //call callback on the mainThread
                Looper.getMainLooper()
        );
    }

    /**
     * To stop requesting user's localisation
     */
    public void stopLocationRequest(){
        if (callback != null) {
            fusedLocationProviderClient.removeLocationUpdates(callback);
        }
    }

}

