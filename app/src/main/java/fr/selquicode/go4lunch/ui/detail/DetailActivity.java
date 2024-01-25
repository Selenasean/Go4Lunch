package fr.selquicode.go4lunch.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.databinding.ActivityDetailBinding;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    public static final String PLACE_ID = "PLACE_ID";
    private String restaurantName;
    private DetailViewModel detailViewModel;
    private final WorkmatesListDetailAdapter adapter = new WorkmatesListDetailAdapter();


    /**
     * Start DetailActivity
     *
     * @param placeId id of the item clicked = a restaurant
     * @param context the context
     */
    public static void launch(String placeId, Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.PLACE_ID, placeId);
        context.startActivity(intent);
    }


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

        //settings for RecycleView
        setRecycleView();

        //btn to choose or not a restaurant
        binding.fabAddRestaurant.setOnClickListener( listener -> detailViewModel.onRestaurantChoice(restaurantName));
    }

    /**
     * Settings for the RecycleView
     */
    private void setRecycleView() {
        RecyclerView recyclerView = binding.workmatesListInDetail;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Settings to bind viewModel-View & Observer-LiveData
     */
    private void setViewModel() {
       detailViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(DetailViewModel.class);
       detailViewModel.getPlaceDetails().observe(this, this::render);
       detailViewModel.getWorkmatesWhoChose().observe(
               this,
               adapter::submitList
       );
       detailViewModel.isRestaurantChosenByUserLogged().observe(this, this::refreshFab);
    }

    /**
     * Method to refreshes the style of the fab
     * @param aBoolean
     */
    private void refreshFab(Boolean aBoolean) {
        if(aBoolean){
            binding.fabAddRestaurant.setImageResource(R.drawable.baseline_check_circle_24);
        }else{
            binding.fabAddRestaurant.setImageResource(R.drawable.baseline_add_circle_24);
        }
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

        // set restaurant's img
        setRestaurantImg(placeDetailsViewState, restaurantImg);

        // name and address
        restaurantName = placeDetailsViewState.getNameRestaurant();
        name.setText(placeDetailsViewState.getNameRestaurant());
        address.setText(placeDetailsViewState.getVicinity());

        // rating bar
        rating.setRating(placeDetailsViewState.getRatings());

        // call phone number
        goToPhoneNumber(placeDetailsViewState, callBtn, callTV);

        // go to website
        goToWebsite(placeDetailsViewState, website, websiteTV);

    }

    /**
     * Method to set restaurant's image
     * @param placeDetailsViewState details of place type ViewState
     * @param restaurantImg ImageView in layout
     */
    private void setRestaurantImg(PlaceDetailsViewState placeDetailsViewState, ImageView restaurantImg) {
        if(placeDetailsViewState.getRestaurantImg() != null || placeDetailsViewState.getRestaurantImg().getPhoto_reference() != null){
            String imgURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="
                    + placeDetailsViewState.getRestaurantImg().getPhoto_reference() + "&key=" + BuildConfig.MAPS_API_KEY;
            Glide.with(MainApplication.getApplication()).load(imgURL).centerCrop().into(restaurantImg);
        }else{
            Glide.with(MainApplication.getApplication()).load(R.drawable.no_img_available).into(restaurantImg);
        }
    }

    /**
     * Method to open dialog with restaurant's phone number, when phone icon is clicked
     * @param placeDetailsViewState details of place type viewState
     * @param callBtn phone icon in layout
     * @param callTV phone TextView in layout
     */
    private void goToPhoneNumber(PlaceDetailsViewState placeDetailsViewState, ImageView callBtn, TextView callTV) {
        if(placeDetailsViewState.getPhoneNumber() != null){
            //if there is a phone to call open Dialer to call the phone number
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
            callBtn.setColorFilter(getResources().getColor(R.color.grey, null));
            callTV.setTextColor(getResources().getColor(R.color.grey, null));
        }
    }

    /**
     * Method to open the website in browser's window
     * @param placeDetailsViewState details of place type ViewState
     * @param website icon website in layout
     * @param websiteTV website TextView in layout
     */
    private void goToWebsite(PlaceDetailsViewState placeDetailsViewState, ImageView website, TextView websiteTV) {
        if(placeDetailsViewState.getWebsite() != null){
            //if there is a website open it in browser's window
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
            website.setColorFilter(getResources().getColor(R.color.grey,null));
            websiteTV.setTextColor(getResources().getColor(R.color.grey, null));
        }
    }

}