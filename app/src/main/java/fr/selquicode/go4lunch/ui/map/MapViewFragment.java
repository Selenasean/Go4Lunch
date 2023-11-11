package fr.selquicode.go4lunch.ui.map;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mapViewModel;
    private GoogleMap mMap;

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
        // TODO: Use the ViewModel
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_container_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }

    private void settingViewModel() {
        mapViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MapViewModel.class);
    }

    private void render(List<Place> places) {
        Log.i("MainActivity", places.size() + "");
        for (Place place : places) {
            Log.i("MainActivity", place.toString());
            LatLng restaurant = new LatLng(place.getGeometry().getLocation().getLat(),place.getGeometry().getLocation().getLng());
            mMap.addMarker(new MarkerOptions()
                    .position(restaurant)
                    .title("restaurant"));
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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Lognes and move the camera
        LatLng lognes = new LatLng(48.834275, 2.63731);
        mMap.addMarker(new MarkerOptions()
                .position(lognes)
                .title("Marker in Lognes"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lognes, 16));
        mapViewModel.getPlaces().observe(getViewLifecycleOwner(), this::render);
    }

}