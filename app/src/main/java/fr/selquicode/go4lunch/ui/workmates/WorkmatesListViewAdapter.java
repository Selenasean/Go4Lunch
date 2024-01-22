package fr.selquicode.go4lunch.ui.workmates;





import static android.provider.Settings.System.getString;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.databinding.WorkmatesItemBinding;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;


public class WorkmatesListViewAdapter extends ListAdapter<WorkmatesViewState, WorkmatesListViewAdapter.ViewHolder> {


    public WorkmatesListViewAdapter() {
        super(DIFF_CALLBACK);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView workmatePictureProfile;
        private final TextView workmateName, restaurantName;

        public ViewHolder(@NonNull WorkmatesItemBinding binding) {
            super(binding.getRoot());
            //bind with layout's elements
            workmateName = binding.workmateName;
            restaurantName = binding.restaurantName;
            workmatePictureProfile = binding.workmatePhoto;

        }

        public void bind(WorkmatesViewState item){
            // set workmates name
            String displayName = item.getDisplayName();
            String firstName = displayName.contains(" ") ? displayName.split(" ")[0] : displayName;
            workmateName.setText(firstName);

            //set workmates profile picture
            Glide.with(MainApplication.getApplication())
                    .load(item.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(workmatePictureProfile);

            //set place where workmate is gonna eat if it exist or set "hasn't decided yet"
            if(item.hasChosenRestaurant()){
                //styled text
                workmateName.setTextColor(ContextCompat.getColor(MainApplication.getApplication(), R.color.black));
                workmateName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                restaurantName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                restaurantName.setTextColor(ContextCompat.getColor(MainApplication.getApplication(), R.color.black));
                String string = MainApplication.getApplication().getString(R.string.is_eating);
                String concatString = string + item.getRestaurantName();
                restaurantName.setText(concatString);
            }else{
                restaurantName.setText(R.string.no_restaurant_chosen);
            }



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WorkmatesItemBinding binding = WorkmatesItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
        // listener only on workmate who has chosen a restaurant to eat
        if(getItem(position).hasChosenRestaurant()){
            holder.itemView.setOnClickListener(v ->{
            final Context context = holder.itemView.getContext();
            String placeId = getItem(position).getRestaurantId();
            DetailActivity.launch(placeId, context);
            });
        }
    }

    /**
     * method that compare two item and their content to update them if it's necessary
     */
    public static final DiffUtil.ItemCallback<WorkmatesViewState> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<WorkmatesViewState>() {
                @Override
                public boolean areItemsTheSame(@NonNull WorkmatesViewState oldItem, @NonNull WorkmatesViewState newItem) {
                    return oldItem.getWorkmateId().equals(newItem.getWorkmateId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull WorkmatesViewState oldItem, @NonNull WorkmatesViewState newItem) {
                    return oldItem.equals(newItem);
                }

            };
    
}
