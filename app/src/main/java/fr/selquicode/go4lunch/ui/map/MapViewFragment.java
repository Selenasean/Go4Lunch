package fr.selquicode.go4lunch.ui.map;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;
import fr.selquicode.go4lunch.ui.ViewModelFactory;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mapViewModel;
    private GoogleMap map;


    public static MapViewFragment newInstance() {
        return new MapViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_view, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingViewModel();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_container_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Settings for viewModel
     */
    private void settingViewModel() {
        mapViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MapViewModel.class);
    }

    /**
     * Method to display information on Map
     * @param places - type List of MapViewState
     */
    private void render(List<MapViewState> places) {
        map.clear();
        if(places.size() < 1){
            Toast.makeText(getContext(), R.string.no_restaurant_found, Toast.LENGTH_SHORT).show();
        }else{
            for (MapViewState place : places) {
                LatLng restaurant = new LatLng(place.getGeometry().getLocation().getLat(), place.getGeometry().getLocation().getLng());
                //create a marker on the map
                Marker markerCreated = map.addMarker(new MarkerOptions()
                            .position(restaurant)
                            .title(place.getName())
                );
                if(markerCreated != null){
                    markerCreated.setTag(place.getPlaceId());
                    if(place.isWorkmatesEatingThere){
                        //change the color of the marker on map when there is workmates who'll eat there
                        markerCreated.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    }
                }
            }
            map.setOnMarkerClickListener(this::onMarkerClicked);
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        // move the camera to user's location
        if(mapViewModel.getUserLocation() != null){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapViewModel.getUserLocation(), 16));
        }
        // add a sign on map to see user's localisation
        mapViewModel.locateTheUserOnMap(map);
        // put Observer on the places' list
        mapViewModel.getPlaces().observe(getViewLifecycleOwner(), this::render);
    }

    /**
     * To open the DetailActivity linked to the corresponding place
     * @param marker clicked by user
     * @return - boolean - open DetailActivity
     */
    private boolean onMarkerClicked(Marker marker) {
        String placeId = (String) marker.getTag();
        DetailActivity.launch(placeId, getContext());
        return false;
    }

}