package fr.selquicode.go4lunch.ui;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.work.WorkManager;

import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.permission_checker.PermissionChecker;
import fr.selquicode.go4lunch.domain.NotificationSchedule;
import fr.selquicode.go4lunch.ui.chat.ChatActivity;
import fr.selquicode.go4lunch.ui.chat.ChatViewModel;
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
                    MainApplication application = MainApplication.getApplication();

                    factory = new ViewModelFactory(
                            new PermissionChecker(application),
                            application.getLocationRepository(),
                            application.getFirebaseAuthRepository(),
                            application.getFirestoreRepository(),
                            new NotificationSchedule(WorkManager.getInstance(MainApplication.getApplication().getApplicationContext())));
                }
            }
        }
        return factory;
    }

    //Injection : reaching PlacesNearbySearchResponseAPI by putting it in repository constructor
    private final PlaceRepository placeRepository = MainApplication.getApplication().getPlaceRepository();
    private final LocationRepository locationRepository;
    private final PermissionChecker permissionChecker;
    private final FirebaseAuthRepository firebaseAuthRepository;
    private final FirestoreRepository firestoreRepository;
    private final NotificationSchedule notificationSchedule;

    /**
     * Constructor
     */
    private ViewModelFactory(
            PermissionChecker permissionChecker,
            LocationRepository locationRepository,
            FirebaseAuthRepository firebaseAuthRepository,
            FirestoreRepository firestoreRepository,
            NotificationSchedule notificationSchedule) {
        this.permissionChecker = permissionChecker;
        this.locationRepository = locationRepository;
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.firestoreRepository = firestoreRepository;
        this.notificationSchedule = notificationSchedule;
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
            return (T) new MainViewModel(permissionChecker, locationRepository, firestoreRepository, firebaseAuthRepository, placeRepository);
        } else if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(placeRepository, locationRepository, permissionChecker, firestoreRepository, firebaseAuthRepository);
        } else if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel(placeRepository, locationRepository, firestoreRepository, firebaseAuthRepository);
        } else if (modelClass.isAssignableFrom(WorkmatesViewModel.class)) {
            return (T) new WorkmatesViewModel(firestoreRepository, firebaseAuthRepository);
        } else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            String placeId = savedStateHandle.get(DetailActivity.PLACE_ID);
            return (T) new DetailViewModel(placeRepository, placeId, firestoreRepository, firebaseAuthRepository, notificationSchedule);
        } else if (modelClass.isAssignableFrom(LogInViewModel.class)) {
            return (T) new LogInViewModel(firebaseAuthRepository, firestoreRepository);
        } else if (modelClass.isAssignableFrom(ChatViewModel.class)){
            String workmateId = savedStateHandle.get(ChatActivity.WORKMATE_ID);
            return (T) new ChatViewModel(firestoreRepository, firebaseAuthRepository, workmateId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class : " + modelClass);
    }
}