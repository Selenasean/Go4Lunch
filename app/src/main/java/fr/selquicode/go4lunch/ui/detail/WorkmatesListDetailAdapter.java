package fr.selquicode.go4lunch.ui.detail;


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
import fr.selquicode.go4lunch.data.model.Workmate;
import fr.selquicode.go4lunch.databinding.WorkmatesItemBinding;

public class WorkmatesListDetailAdapter extends ListAdapter<Workmate, WorkmatesListDetailAdapter.ViewHolder> {

   public WorkmatesListDetailAdapter() {
        super(DIFF_CALLBACK);
   }

   //START OF CLASS VIEW HOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView workmatePictureProfile, chatBtn;
        private final TextView workmateName, joiningString;

        public ViewHolder(@NonNull WorkmatesItemBinding binding) {
            super(binding.getRoot());
            workmateName = binding.workmateName;
            workmatePictureProfile = binding.workmatePhoto;
            joiningString = binding.restaurantName;
            chatBtn = binding.btnChat;
        }

        public void bind(Workmate item){
            //hide btn to the chat
            chatBtn.setVisibility(View.GONE);
            //display workmates profile picture
            Glide.with(MainApplication.getApplication())
                    .load(item.getWorkmatePhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(workmatePictureProfile);

            // display workmates firstname
            workmateName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            workmateName.setTextColor(ContextCompat.getColor(MainApplication.getApplication(), R.color.black));
            workmateName.setText(item.getWorkmateDisplayName());

            //display "is joining"
            joiningString.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            joiningString.setTextColor(ContextCompat.getColor(MainApplication.getApplication(), R.color.black));
            joiningString.setText(R.string.is_joining);

        }
    }
    //END OF CLASS VIEW HOLDER

    @NonNull
    @Override
    public WorkmatesListDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WorkmatesItemBinding binding = WorkmatesItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesListDetailAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    /**
     * method that compare two item and their content to update them if it's necessary
     */
    public static final DiffUtil.ItemCallback<Workmate> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Workmate>() {
                @Override
                public boolean areItemsTheSame(@NonNull Workmate oldItem, @NonNull Workmate newItem) {
                    return oldItem.getWorkmateId().equals(newItem.getWorkmateId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Workmate oldItem, @NonNull Workmate newItem) {
                    return oldItem.equals(newItem);
                }

            };

}
