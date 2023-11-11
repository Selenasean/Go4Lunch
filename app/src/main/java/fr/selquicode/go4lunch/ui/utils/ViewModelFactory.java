package fr.selquicode.go4lunch.ui.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.LocationServices;

import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.RetrofitService;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;
import fr.selquicode.go4lunch.ui.MainViewModel;
import fr.selquicode.go4lunch.ui.list.ListViewModel;
import fr.selquicode.go4lunch.ui.map.MapViewModel;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewModel;

/**
 * Class who wil create a new ViewModel for every new activity/fragment
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static volatile ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if(factory == null){
            synchronized (ViewModelFactory.class){
                if(factory == null){
                    Application application = MainApplication.getApplication();

                    factory = new ViewModelFactory(
                            new PermissionChecker(application),
                            new LocationRepository(LocationServices.getFusedLocationProviderClient(application))
                    );
                }
            }
        }
        return factory;
    }

    //Injection : reaching PlacesNearbySearchResponseAPI by putting it in repository constructor
    private final PlaceRepository repository = new PlaceRepository(RetrofitService.getPlaceAPI());
    private final LocationRepository locationRepository;
    private PermissionChecker permissionChecker;

    /**
     * Constructor
     */
    private ViewModelFactory(PermissionChecker permissionChecker, LocationRepository locationRepository){
        this.permissionChecker = permissionChecker;
        this.locationRepository = locationRepository;
    }

    /**
     * Creation of viewModels
     *
     * @param modelClass model of classes ViewModel
     * @param <T>        type of Class, here of ViewModel
     * @return new viewModel
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        // if modelClass - model of classes ViewModel - is the same as new ViewModel created
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //injection of the Repository in the ViewModel constructor
            return (T) new MainViewModel(permissionChecker, locationRepository);
        } else if(modelClass.isAssignableFrom(MapViewModel.class)){
            return (T) new MapViewModel(repository, locationRepository);
        } else if(modelClass.isAssignableFrom(ListViewModel.class)){
            return (T) new ListViewModel(repository);
        } else if(modelClass.isAssignableFrom(WorkmatesViewModel.class)){
            return (T) new WorkmatesViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class : " + modelClass);
    }
}
