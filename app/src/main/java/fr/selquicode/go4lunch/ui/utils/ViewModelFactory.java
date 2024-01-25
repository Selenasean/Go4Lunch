package fr.selquicode.go4lunch.ui.utils;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.data.place.RetrofitService;
import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;
import fr.selquicode.go4lunch.ui.MainViewModel;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;
import fr.selquicode.go4lunch.ui.detail.DetailViewModel;
import fr.selquicode.go4lunch.ui.list.ListViewModel;
import fr.selquicode.go4lunch.ui.login.LogInViewModel;
import fr.selquicode.go4lunch.ui.map.MapViewModel;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewModel;

/**
 * Class who wil create a new ViewModel for every new activity/fragment
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static volatile ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    Application application = MainApplication.getApplication();

                    factory = new ViewModelFactory(
                            new PermissionChecker(application),
                            new LocationRepository(LocationServices.getFusedLocationProviderClient(application)),
                            new FirebaseAuthRepository(FirebaseAuth.getInstance()),
                            new FirestoreRepository(FirebaseFirestore.getInstance())
                    );
                }
            }
        }
        return factory;
    }

    //Injection : reaching PlacesNearbySearchResponseAPI by putting it in repository constructor
    private final PlaceRepository repository = new PlaceRepository(RetrofitService.getPlaceAPI());
    private final LocationRepository locationRepository;
    private final PermissionChecker permissionChecker;
    private final FirebaseAuthRepository firebaseAuthRepository;
    private final FirestoreRepository firestoreRepository;

    /**
     * Constructor
     */
    private ViewModelFactory(
            PermissionChecker permissionChecker,
            LocationRepository locationRepository,
            FirebaseAuthRepository firebaseAuthRepository,
            FirestoreRepository firestoreRepository) {
        this.permissionChecker = permissionChecker;
        this.locationRepository = locationRepository;
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.firestoreRepository = firestoreRepository;
    }

    /**
     * Creation of viewModels
     *
     * @param modelClass model of classes ViewModel
     * @param <T>        type of Class, here of ViewModel
     * @return new viewModel
     */
    @SuppressWarnings("Unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        // if modelClass - model of classes ViewModel - is the same as new ViewModel created
        SavedStateHandle savedStateHandle = createSavedStateHandle(extras);
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //injection of the Repository in the ViewModel constructor
            return (T) new MainViewModel(permissionChecker, locationRepository);
        } else if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(repository, locationRepository, permissionChecker);
        } else if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel(repository, locationRepository);
        } else if (modelClass.isAssignableFrom(WorkmatesViewModel.class)) {
            return (T) new WorkmatesViewModel(firestoreRepository, firebaseAuthRepository);
        } else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            String placeId = savedStateHandle.get(DetailActivity.PLACE_ID);
            return (T) new DetailViewModel(repository, placeId, firestoreRepository, firebaseAuthRepository);
        } else if (modelClass.isAssignableFrom(LogInViewModel.class)) {
            return (T) new LogInViewModel(firebaseAuthRepository, firestoreRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class : " + modelClass);
    }
}
