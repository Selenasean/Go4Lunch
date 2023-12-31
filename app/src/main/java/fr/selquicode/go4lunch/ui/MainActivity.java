package fr.selquicode.go4lunch.ui;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.RetrofitService;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.databinding.ActivityMainBinding;
import fr.selquicode.go4lunch.ui.list.ListViewFragment;
import fr.selquicode.go4lunch.ui.map.MapViewFragment;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewFragment;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private PlaceRepository repository = new PlaceRepository(RetrofitService.getPlaceAPI());
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ask permission to localise the user
        this.requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                0
        );

        //the fragment that will be displayed by default
        replaceFragment(new MapViewFragment());
        //let user choose the fragment
        binding.bottomNavigation.setOnItemSelectedListener(item -> fragmentChoice(item));

        //settings for ViewModel
        setViewModel();
    }

    /**
     * Method that allows us to replace fragment according to the item selected by user
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentMg = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentMg.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    /**
     * Choice of fragments
     */
    private boolean fragmentChoice(MenuItem item){
        switch (item.getItemId()) {
            case R.id.mapView:
                MainActivity.this.replaceFragment(new MapViewFragment());
                break;
            case R.id.listMapView:
                MainActivity.this.replaceFragment(new ListViewFragment());
                break;
            case R.id.workmates:
                MainActivity.this.replaceFragment(new WorkmatesViewFragment());
                break;
        }
        return true;
    }

    /**
     * Settings to link ViewModel, Observer and UI
     */
    private void setViewModel() {
        mainViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.refresh();
    }
}