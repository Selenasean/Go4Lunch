package fr.selquicode.go4lunch.ui;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.data.place.RetrofitService;
import fr.selquicode.go4lunch.databinding.ActivityMainBinding;

import fr.selquicode.go4lunch.ui.list.ListViewFragment;
import fr.selquicode.go4lunch.ui.map.MapViewFragment;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewFragment;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;

    private final PlaceRepository repository = new PlaceRepository(RetrofitService.getPlaceAPI());
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTopAppBar();

        binding.topAppBar.setNavigationOnClickListener(listener -> binding.drawerLayout.open());
        binding.navigationView.setNavigationItemSelectedListener(this::navigationDrawer);



        //ask permission to localise the user
        this.requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                0
        );

        //the fragment that will be displayed by default
        replaceFragment(new MapViewFragment());
        //let user choose the fragment
        binding.bottomNavigation.setOnItemSelectedListener(this::fragmentChoice);

        //settings for ViewModel
        setViewModel();

    }

    private boolean navigationDrawer(MenuItem menuItem) {
        Log.i("navDrawer", "avant switch");
        switch(menuItem.getItemId()){
            case R.id.lunch:
                //open DetailActvity on the right restaurant chosen by user logged
                Log.i("navDrawer", "lunch");
                return true;
            case R.id.settings:
                //open settings actvity or dialog ?
                Log.i("navDrawer", "settings");
                return true;
            case R.id.logout:
                //logout user, redirecting to LoginActivity
                Log.i("navDrawer", "logout");
                return true;
            default :
                Log.i("navDrawer", "default");
//                binding.drawerLayout.close();

        }
        return true;
    }

    private void setTopAppBar() {
        setSupportActionBar(binding.topAppBar);
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
     * Settings to link ViewModel
     */
    private void setViewModel() {
        mainViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.topAppBar:
//                binding.drawerLayout.openD
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.refresh();
    }

}