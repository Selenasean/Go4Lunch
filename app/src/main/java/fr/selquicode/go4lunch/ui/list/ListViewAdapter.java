package fr.selquicode.go4lunch.ui.list;

import static android.content.ClipData.newIntent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import fr.selquicode.go4lunch.BuildConfig;
import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.databinding.RestaurantItemBinding;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;

public class ListViewAdapter extends ListAdapter<ListViewState, ListViewAdapter.ViewHolder> {

    public static final String PLACE_ID = "PLACE_ID";

    public ListViewAdapter(){
        super(DIFF_CALLBACK);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name, address, opening;
        private final ImageView restaurantImg;

        public ViewHolder(@NonNull RestaurantItemBinding binding){
            super(binding.getRoot());
            //binding element
            name = binding.nameRestaurant;
            address = binding.address;
            opening = binding.opening;
            restaurantImg = binding.imageRestaurant;
        }
        public void bind(ListViewState item){
            //binding with viewState
            //TODO : bind avec le viewState
            name.setText(item.getNameRestaurant());
            address.setText(item.getVicinity());

            //set the restaurant's image
            if(item.getRestaurantImg() != null && item.getRestaurantImg().getPhoto_reference() != null){
                String imgURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="
                        + item.getRestaurantImg().getPhoto_reference() + "&key=" + BuildConfig.MAPS_API_KEY;
                Glide.with(MainApplication.getApplication()).load(imgURL).into(restaurantImg);
            }else {
                Glide.with(MainApplication.getApplication()).load(R.drawable.no_image).into(restaurantImg);
            }

        }
    }

    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantItemBinding binding = RestaurantItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                                                                        parent,
                                                                        false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));

        //onClickListener on items clicked of he list to go to the detail page
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = holder.itemView.getContext();
                String placeId = getItem(position).getId();
                launchDetailActivity(placeId,  context);
            }
        });
    }

    /**
     * Start DetailActivity
     * @param placeId id of the item clicked = a place
     * @param context the context
     */
    public void launchDetailActivity(String placeId, Context context){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(PLACE_ID, placeId);
        context.startActivity(intent);
    }

    /**
     * method that compare two item and their content to update them if it's necessary
     */
    public static final DiffUtil.ItemCallback<ListViewState> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ListViewState>() {
                @Override
                public boolean areItemsTheSame(@NonNull ListViewState oldItem, @NonNull ListViewState newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ListViewState oldItem, @NonNull ListViewState newItem) {
                    return oldItem.equals(newItem);
                }

            };
}
