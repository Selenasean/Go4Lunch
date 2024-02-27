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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

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

    private void settingViewModel() {
        mapViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MapViewModel.class);
    }

    private void render(List<MapViewState> places) {
        Log.i("MainActivity", places.size() + "");
        map.clear();
        if(places.size() < 1){
            Toast.makeText(getContext(), R.string.no_restaurant_found, Toast.LENGTH_SHORT).show();
        }else{
            for (MapViewState place : places) {
                Log.i("MainActivity", place.toString());
                LatLng restaurant = new LatLng(place.getGeometry().getLocation().getLat(), place.getGeometry().getLocation().getLng());
                Marker markerCreated = map.addMarker(new MarkerOptions()
                        .position(restaurant)
                        .title(place.getName())
                );
                if(markerCreated != null){
                    markerCreated.setTag(place.getPlaceId());
                }
                //TODO : change marker's color if there is workmates eating there
            }
            map.setOnMarkerClickListener(this::onMarkerClicked);
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Lognes, France.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        // move the camera to user's location
        //TODO : getLocation synchronously from location repository
        if(mapViewModel.getUserLocation() != null){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapViewModel.getUserLocation(), 16));
        }
        // add a sign on map to see user's localisation
        mapViewModel.locateTheUserOnMap(map);
        // put Observer on the places' list
        mapViewModel.getPlaces().observe(getViewLifecycleOwner(), this::render);

    }

    private boolean onMarkerClicked(Marker marker) {
        //TODO : open detail activity corresponding to the place id
        //TODO : + count clik if click = 1 : show title, if click = 2 : open detail activity
        String placeId = (String) marker.getTag();
        DetailActivity.launch(placeId, getContext());
        return false;
    }

}