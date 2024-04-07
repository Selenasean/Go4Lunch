package fr.selquicode.go4lunch.ui.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.model.Message;
import fr.selquicode.go4lunch.databinding.RestaurantItemBinding;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;

public class ListViewAdapter extends ListAdapter<ListViewState, ListViewAdapter.ViewHolder> {

    public ListViewAdapter() {
        super(DIFF_CALLBACK);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, address, opening, distance, workmatesEating;
        private final ImageView restaurantImg;
        private final RatingBar rating;

        public ViewHolder(@NonNull RestaurantItemBinding binding) {
            super(binding.getRoot());
            //binding elements
            name = binding.nameRestaurant;
            address = binding.address;
            opening = binding.opening;
            distance = binding.meters;
            restaurantImg = binding.imageRestaurant;
            rating = binding.ratingBar;
            workmatesEating = binding.workmatesNumber;
        }

        public void bind(ListViewState item) {
            //binding with viewState
            // TODO : bind avec le viewState numbers of workmates
            name.setText(item.getNameRestaurant());
            address.setText(item.getVicinity());

            //set the restaurant's image
            if (item.getRestaurantImg() != null && item.getRestaurantImg().getPhoto_reference() != null) {
                String imgURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="
                        + item.getRestaurantImg().getPhoto_reference() + "&key=" + BuildConfig.MAPS_API_KEY;
                Glide.with(MainApplication.getApplication()).load(imgURL).into(restaurantImg);
            } else {
                Glide.with(MainApplication.getApplication()).load(R.drawable.no_image).into(restaurantImg);
            }

            //set numbers of workmates who eating in restaurant
            if(item.getWorkmateEatingCount()>0){
                String workmatesCount = String.valueOf(item.getWorkmateEatingCount());
                workmatesEating.setText(workmatesCount);
            }else{
                workmatesEating.setText("");
            }

            //set rating
            rating.setRating(item.getRatings());

            //set distance between user's location and restaurant's location
            distance.setText(item.getDistance());

            // set opening
            if(item.getOpening()!= null){
                if(item.getOpening()){
                    opening.setText(R.string.open);
                    opening.setTextColor(ContextCompat.getColor(MainApplication.getApplication().getApplicationContext(), R.color.green));
                }else{
                    opening.setText(R.string.closed);
                    opening.setTextColor(ContextCompat.getColor(MainApplication.getApplication().getApplicationContext(),R.color.red));
                }
            }

        }

    }

    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantItemBinding binding = RestaurantItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));

        //onClickListener on items clicked of he list to go to the detail page
        holder.itemView.setOnClickListener(view -> {
            final Context context = holder.itemView.getContext();
            String placeId = getItem(position).getId();
            DetailActivity.launch(placeId, context);
        });
    }


    /**
     * method that compare two item and their content to update them if it's necessary
     */
    public static final DiffUtil.ItemCallback<ListViewState> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ListViewState>() {
                @Override
                public boolean areItemsTheSame(@NonNull ListViewState oldItem, @NonNull ListViewState newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull ListViewState oldItem, @NonNull ListViewState newItem) {
                    return oldItem.equals(newItem);
                }

            };
}
