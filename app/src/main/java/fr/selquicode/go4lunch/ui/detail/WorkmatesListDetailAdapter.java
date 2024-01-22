package fr.selquicode.go4lunch.ui.detail;

import static fr.selquicode.go4lunch.ui.list.ListViewAdapter.DIFF_CALLBACK;

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
import fr.selquicode.go4lunch.ui.workmates.WorkmatesListViewAdapter;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewState;

public class WorkmatesListDetailAdapter extends ListAdapter<WorkmatesDetailViewState, WorkmatesListDetailAdapter.ViewHolder> {


   public WorkmatesListDetailAdapter() {
        super(DIFF_CALLBACK);
   }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView workmatePictureProfile;
        private final TextView workmateName, joiningString;

        public ViewHolder(@NonNull WorkmatesItemBinding binding) {
            super(binding.getRoot());
            workmateName = binding.workmateName;
            workmatePictureProfile = binding.workmatePhoto;
            joiningString = binding.restaurantName;
        }

        public void bind(WorkmatesDetailViewState item){
            //display workmates profile picture
            Glide.with(MainApplication.getApplication())
                    .load(item.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(workmatePictureProfile);

            // display workmates firstname
            String displayName = item.getDisplayName();
            String firstName = displayName.contains(" ") ? displayName.split(" ")[0] : displayName;
            workmateName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            workmateName.setTextColor(ContextCompat.getColor(MainApplication.getApplication(), R.color.black));
            workmateName.setText(firstName);

            //display "is joining"
            joiningString.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            joiningString.setTextColor(ContextCompat.getColor(MainApplication.getApplication(), R.color.black));
            joiningString.setText(R.string.is_joining);


        }
    }

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



    public static final DiffUtil.ItemCallback<WorkmatesDetailViewState> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<WorkmatesDetailViewState>() {
                @Override
                public boolean areItemsTheSame(@NonNull WorkmatesDetailViewState oldItem, @NonNull WorkmatesDetailViewState newItem) {
                    return oldItem.getWorkmateId().equals(newItem.getWorkmateId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull WorkmatesDetailViewState oldItem, @NonNull WorkmatesDetailViewState newItem) {
                    return oldItem.equals(newItem);
                }

            };


}
