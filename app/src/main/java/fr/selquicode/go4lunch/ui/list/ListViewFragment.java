package fr.selquicode.go4lunch.ui.list;

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

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.databinding.FragmentListViewBinding;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class ListViewFragment extends Fragment {

    private ListViewViewModel mViewModel;
    private ListViewAdapter adapter = new ListViewAdapter();
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
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ListViewViewModel.class);
        mViewModel.getPlaces().observe(getViewLifecycleOwner(), listViewStates -> adapter.submitList(listViewStates));

        //settings for recyclerView
        setRecyclerView();
    }

    /**
     * Settings for Recycleview
     */
    private void setRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        RecyclerView recyclerView = binding.list;
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }
}