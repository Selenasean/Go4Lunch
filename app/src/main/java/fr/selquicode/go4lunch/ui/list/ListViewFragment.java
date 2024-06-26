package fr.selquicode.go4lunch.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.databinding.FragmentListViewBinding;
import fr.selquicode.go4lunch.ui.ViewModelFactory;

public class ListViewFragment extends Fragment {

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
        ListViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ListViewModel.class);
        viewModel.getPlaces().observe(getViewLifecycleOwner(), listViewStates -> {
            if(listViewStates.isEmpty()){
                Toast.makeText(getContext(), R.string.no_restaurant_found, Toast.LENGTH_SHORT).show();
            }else{
                adapter.submitList(listViewStates);
            }
        });

        //settings for recyclerView
        setRecyclerView();
    }

    /**
     * Settings for Recyclerview
     */
    private void setRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL);
        RecyclerView recyclerView = binding.list;
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }
}