package fr.selquicode.go4lunch.ui.workmates;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.selquicode.go4lunch.databinding.FragmentWorkmatesViewBinding;
import fr.selquicode.go4lunch.ui.ViewModelFactory;
import fr.selquicode.go4lunch.ui.chat.ChatActivity;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;

public class WorkmatesViewFragment extends Fragment {

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

}
