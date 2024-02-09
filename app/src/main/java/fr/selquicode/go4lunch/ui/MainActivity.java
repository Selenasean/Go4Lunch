package fr.selquicode.go4lunch.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.data.place.RetrofitService;
import fr.selquicode.go4lunch.databinding.ActivityMainBinding;

import fr.selquicode.go4lunch.ui.detail.DetailActivity;
import fr.selquicode.go4lunch.ui.list.ListViewFragment;
import fr.selquicode.go4lunch.ui.login.LogInActivity;
import fr.selquicode.go4lunch.ui.map.MapViewFragment;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewFragment;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;

    private final PlaceRepository repository = new PlaceRepository(RetrofitService.getPlaceAPI());
    private MainViewModel mainViewModel;
    private String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTopAppBar();
        setDrawer();

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

    /**
     * Setting for drawer
     */
    private void setDrawer() {
        binding.topAppBar.setNavigationOnClickListener(listener -> binding.drawerLayout.open());
        binding.navigationView.setNavigationItemSelectedListener(this::navigationDrawer);
    }

    /**
     * To chose a item in the drawer
     * @param menuItem item of the drawer : lunch, settings & logout
     * @return a boolean
     */
    private boolean navigationDrawer(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.lunch:
                if(placeId != null){
                    DetailActivity.launch(placeId,this);
                }else{
                    Snackbar.make(binding.mainContent, R.string.no_lunch_chosen, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.settings:
                // TODO : open settings actvity or dialog ?
                Log.i("navDrawer", "settings");
                break;
            case R.id.logout:
                mainViewModel.signOut(this).addOnSuccessListener(listener -> startLogInActivity());
                break;
        }
        return true;
    }

    /**
     * Settings for the top app bar
     */
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
        mainViewModel.getUserLogged().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                placeId = user.getRestaurantId();
                setUserLoggedData(user.getPhotoUserUrl(), user.getDisplayName(), user.getEmail());
            }
        });
    }

    private void setUserLoggedData(String photoUserUrl, String displayName, String email) {
        //display user profile picture
        Glide.with(MainApplication.getApplication())
                .load(photoUserUrl)
                .apply(RequestOptions.circleCropTransform())
                .into((ImageView) findViewById(R.id.profile_picture));
        //display email and firstName
        ((TextView)findViewById(R.id.user_name)).setText((displayName.contains(" ") ?
                displayName.split(" ")[0] : displayName));
        ((TextView)findViewById(R.id.user_email)).setText(email);
    }

    /**
     * To start LogInActivity
     */
    private void startLogInActivity(){
        Intent intent = new Intent(this, LogInActivity.class);
        this.startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.refresh();
    }

}