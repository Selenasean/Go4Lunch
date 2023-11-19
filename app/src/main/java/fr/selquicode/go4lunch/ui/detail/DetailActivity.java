package fr.selquicode.go4lunch.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.databinding.ActivityDetailBinding;
import fr.selquicode.go4lunch.databinding.ActivityMainBinding;
import fr.selquicode.go4lunch.ui.MainViewModel;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private DetailViewModel detailViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //settings for ViewModel
        setViewModel();
    }

    private void setViewModel() {
       detailViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(DetailViewModel.class);
    }
}