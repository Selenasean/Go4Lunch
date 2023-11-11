package fr.selquicode.go4lunch.ui.workmates;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class WorkmatesViewFragment extends Fragment {

    private WorkmatesViewModel mViewModel;

    public static WorkmatesViewFragment newInstance() {
        return new WorkmatesViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workmates_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(WorkmatesViewModel.class);
        // TODO: Use the ViewModel
    }
} 