package fr.selquicode.go4lunch.ui.list;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import fr.selquicode.go4lunch.databinding.FragmentListViewBinding;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class ListViewFragment extends Fragment {

    private ListViewModel viewModel;
    private final ListViewAdapter adapter = new ListViewAdapter();
    private FragmentListViewBinding binding;

    public static ListViewFragment newInstance() {
        return new ListViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListViewBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //settings for ViewModel and Observer
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ListViewModel.class);
        viewModel.getPlaces().observe(getViewLifecycleOwner(), new Observer<List<ListViewState>>() {
            @Override
            public void onChanged(List<ListViewState> listViewStates) {
                adapter.submitList(listViewStates);
                Log.i("listVS", listViewStates.toString());
            }
        });

        //settings for recyclerView
        setRecyclerView();
    }

    /**
     * Settings for Recycleview
     */
    private void setRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL);
        RecyclerView recyclerView = binding.list;
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }
}