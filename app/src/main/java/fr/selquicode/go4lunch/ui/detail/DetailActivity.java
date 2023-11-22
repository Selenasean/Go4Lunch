package fr.selquicode.go4lunch.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.databinding.ActivityDetailBinding;
import fr.selquicode.go4lunch.databinding.ActivityMainBinding;
import fr.selquicode.go4lunch.ui.MainViewModel;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    public static final String PLACE_ID = "PLACE_ID";
    private DetailViewModel detailViewModel;
    private PlaceRepository placeRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //settings for ViewModel
        setViewModel();



    }

    private void setViewModel() {
       detailViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(DetailViewModel.class);
       detailViewModel.getPlaceDetails().observe(this, placeDetailsViewState -> {
           Log.i("TAG", "ça touche");
       });
    }
}