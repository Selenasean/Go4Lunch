package fr.selquicode.go4lunch.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.Objects;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.MainApplication;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //hiding toolbar
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        //settings for ViewModel
        setViewModel();
    }

    /**
     * Settings to bind viewModel-View & Observer-LiveData
     */
    private void setViewModel() {
       detailViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(DetailViewModel.class);
       detailViewModel.getPlaceDetails().observe(this, this::render);
    }

    /**
     * Method to display elements on screen
     * @param placeDetailsViewState a Place with attributes type ViewState
     */
    private void render(PlaceDetailsViewState placeDetailsViewState) {
        final ImageView restaurantImg = binding.imageRestaurant;
        final TextView name = binding.nameRestaurant;
        final TextView address = binding.addressRestaurant;
        final RatingBar rating = binding.rating;
        final ImageView callBtn = binding.callBtn;
        final ImageView website = binding.websiteBtn;
        final TextView callTV = binding.callTextview;
        final TextView websiteTV = binding.websiteTextview;
        final int colorGrey = R.color.grey;

        // set restaurant's img
        if(placeDetailsViewState.getRestaurantImg() != null || placeDetailsViewState.getRestaurantImg().getPhoto_reference() != null){
            String imgURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="
                    + placeDetailsViewState.getRestaurantImg().getPhoto_reference() + "&key=" + BuildConfig.MAPS_API_KEY;
            Glide.with(MainApplication.getApplication()).load(imgURL).centerCrop().into(restaurantImg);
        }else{
            Glide.with(MainApplication.getApplication()).load(R.drawable.no_img_available).into(restaurantImg);
        }

        // name and address
        name.setText(placeDetailsViewState.getNameRestaurant());
        address.setText(placeDetailsViewState.getVicinity());

        // rating bar
        rating.setRating(placeDetailsViewState.getRatings());

        // call phone number
        if(placeDetailsViewState.getPhoneNumber() != null && !placeDetailsViewState.getPhoneNumber().equals("")){
            //then open Dialer to call the phone number
            callBtn.setOnClickListener(view -> {
                Uri phoneNumber = Uri.parse("tel:" + placeDetailsViewState.getPhoneNumber());
                Intent callPhoneNumber = new Intent(Intent.ACTION_DIAL, phoneNumber);
                try{
                    startActivity(callPhoneNumber);
                }catch(ActivityNotFoundException e){
                    Log.e("phoneNumber","phone number call failed");
                }
            });
        }else{
            // make phone icon not clickable and grey
            callBtn.setColorFilter(getResources().getColor(colorGrey, null));
            callTV.setTextColor(getResources().getColor(colorGrey, null));
        }

        // go to website
        if(placeDetailsViewState.getWebsite() != null && !placeDetailsViewState.getWebsite().equals("")){
            website.setOnClickListener(view -> {
                Uri websiteURI = Uri.parse(placeDetailsViewState.getWebsite());
                Intent goToWebsite = new Intent(Intent.ACTION_VIEW, websiteURI);
                try{
                    startActivity(goToWebsite);
                }catch(ActivityNotFoundException e){
                    Log.e("website call", "website call failed");
                }
            });
        }else {
            website.setColorFilter(getResources().getColor(colorGrey,null));
            websiteTV.setTextColor(getResources().getColor(colorGrey, null));
        }
    }
}