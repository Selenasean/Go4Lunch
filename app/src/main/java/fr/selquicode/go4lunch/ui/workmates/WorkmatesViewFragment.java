package fr.selquicode.go4lunch.ui.workmates;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.databinding.FragmentWorkmatesViewBinding;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class WorkmatesViewFragment extends Fragment {

    private WorkmatesViewModel viewModel;
    private FragmentWorkmatesViewBinding binding;
    private final WorkmatesListViewAdapter adapter = new WorkmatesListViewAdapter();


    public static WorkmatesViewFragment newInstance() {
        return new WorkmatesViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentWorkmatesViewBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Settings for viewModel and Observer
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(WorkmatesViewModel.class);
        viewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<WorkmatesViewState>>() {
            @Override
            public void onChanged(List<WorkmatesViewState> workmatesViewState) {
                adapter.submitList(workmatesViewState);
            }
        });
        // Settings for RecycleView
        setRecycleView();
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
