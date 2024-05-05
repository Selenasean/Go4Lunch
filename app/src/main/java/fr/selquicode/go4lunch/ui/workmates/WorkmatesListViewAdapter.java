package fr.selquicode.go4lunch.ui.workmates;

import android.graphics.Typeface;
import android.view.LayoutInflater;

import android.view.View;
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


public class WorkmatesListViewAdapter extends ListAdapter<WorkmatesViewState, WorkmatesListViewAdapter.ViewHolder> {

    private final OnWorkmateClickedListener listener;


    public WorkmatesListViewAdapter(OnWorkmateClickedListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    //START PART OF THE VIEW HOLDER
    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView workmatePictureProfile;
        private final TextView workmateName, restaurantName;
        private final ImageView chatBtn;

        public ViewHolder(@NonNull WorkmatesItemBinding binding) {
            super(binding.getRoot());
            //bind with layout's elements
            workmateName = binding.workmateName;
            restaurantName = binding.restaurantName;
            workmatePictureProfile = binding.workmatePhoto;
            chatBtn = binding.btnChat;
        }

        public void bind(WorkmatesViewState item){
            // set workmates name
            workmateName.setText(item.getDisplayName());

            //set workmates profile picture
            if(item.getPhotoUrl() == null || item.getPhotoUrl().isEmpty()){
                workmatePictureProfile.setImageResource(R.drawable.ic_user_profile);
            }else{
            Glide.with(MainApplication.getApplication())
                    .load(item.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(workmatePictureProfile);
            }

            //set place where workmate is gonna eat if it exist or set "hasn't decided yet"
            if(item.hasChosenRestaurant()){
                //styled text
                workmateName.setTextColor(ContextCompat.getColor(MainApplication.getApplication(), R.color.black));
                workmateName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                restaurantName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                restaurantName.setTextColor(ContextCompat.getColor(MainApplication.getApplication(), R.color.black));
                String string = MainApplication.getApplication().getString(R.string.is_eating);
                String concatString = string + " "+  item.getRestaurantName();
                restaurantName.setText(concatString);
            }else{
                restaurantName.setText(R.string.no_restaurant_chosen);
            }


        }
    }
    //END PART OF THE VIEW HOLDER

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
        boolean hasChosen = getItem(position).hasChosenRestaurant();
        String placeId = getItem(position).getRestaurantId();
        String workmateId = getItem(position).getWorkmateId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onWorkmateClick(hasChosen, placeId);
            }
        });

        holder.chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onChatClicked(workmateId);
            }
        });

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
