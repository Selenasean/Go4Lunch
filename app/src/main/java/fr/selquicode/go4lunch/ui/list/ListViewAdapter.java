package fr.selquicode.go4lunch.ui.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.selquicode.go4lunch.databinding.RestaurantItemBinding;

public class ListViewAdapter extends ListAdapter<ListViewState, ListViewAdapter.ViewHolder> {

    public ListViewAdapter(){
        super(DIFF_CALLBACK);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name, foodType, address, opening;
        private final ImageView restaurantImg;

        public ViewHolder(@NonNull RestaurantItemBinding binding){
            super(binding.getRoot());
            //binding element
            name = binding.nameRestaurant;
            foodType = binding.typeFood;
            address = binding.address;
            opening = binding.opening;
            restaurantImg = binding.imageRestaurant;
        }
        public void bind(ListViewState item){
            //binding with viewState
            //TODO : bind avec le viewState
            Log.i("getAdress", item.getAddress());
            name.setText(item.getNameRestaurant());
            address.setText(item.getAddress());

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
