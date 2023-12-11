package fr.selquicode.go4lunch.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;

public class MainViewModel extends ViewModel {


    @NonNull
    private final PermissionChecker permissionChecker;

    @NonNull
    private final LocationRepository locationRepository;


    public MainViewModel(@NonNull PermissionChecker permissionChecker, @NonNull LocationRepository locationRepository) {
        this.permissionChecker = permissionChecker;
        this.locationRepository = locationRepository;


    }

    @SuppressLint("MissingPermission")
    public void refresh(){
        boolean hasGpsPermission = permissionChecker.hasLocationPermission();
        if(hasGpsPermission){
            locationRepository.startLocationRequest();
        }else {
            locationRepository.stopLocationRequest();
        }
    }



}
