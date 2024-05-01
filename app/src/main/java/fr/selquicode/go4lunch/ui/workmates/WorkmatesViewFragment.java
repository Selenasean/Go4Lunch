package fr.selquicode.go4lunch.ui.workmates;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import java.util.List;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.databinding.FragmentWorkmatesViewBinding;
import fr.selquicode.go4lunch.ui.chat.ChatActivity;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;
import fr.selquicode.go4lunch.ui.ViewModelFactory;

public class WorkmatesViewFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private WorkmatesViewModel viewModel;
    private FragmentWorkmatesViewBinding binding;
    private WorkmatesListViewAdapter adapter;
    private Context context;

    public static WorkmatesViewFragment newInstance() {
        return new WorkmatesViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        assert container != null;
        this.context = container.getContext();
        binding = FragmentWorkmatesViewBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //settings for adapter
        setAdapter();
        // Settings for RecycleView
        setRecycleView();

        //Settings for viewModel and Observer
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(WorkmatesViewModel.class);
        viewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<WorkmatesViewState>>() {
            @Override
            public void onChanged(List<WorkmatesViewState> workmatesViewState) {
                adapter.submitList(workmatesViewState);
            }
        });

    }

    /**
     * Settings for adapter
     */
    private void setAdapter() {
        adapter = new WorkmatesListViewAdapter(new OnWorkmateClickedListener() {
            @Override
            public void onWorkmateClick(boolean hasChosen, String placeId) {
                if(hasChosen){
                    DetailActivity.launch(placeId, context);
                }
            }

            @Override
            public void onChatClicked(String workmateId) {
                ChatActivity.launch(workmateId, context);
            }

        });
    }

    /**
     * Settings for RecycleView
     */
    private void setRecycleView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL);
        RecyclerView recyclerView = binding.workmatesList;
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private String restaurantId;
    private String workmateId;
    /**
     * Choose a workmate to chat with or see where he gonna eat
     * @param menuItem - a workmate on the list
     * @return true - launch DetailActivity or ChatActivity
     */
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case(R.id.restaurant) :
                DetailActivity.launch(restaurantId, context);
                break;
            case(R.id.chat) :
                ChatActivity.launch(workmateId, context);
        }
        return true;
    }
}
