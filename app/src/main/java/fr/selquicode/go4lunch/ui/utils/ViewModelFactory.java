package fr.selquicode.go4lunch.ui.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.selquicode.go4lunch.data.PlaceRepository;
import fr.selquicode.go4lunch.data.RetrofitService;
import fr.selquicode.go4lunch.ui.MainViewModel;
import fr.selquicode.go4lunch.ui.list.ListViewFragment;
import fr.selquicode.go4lunch.ui.list.ListViewViewModel;
import fr.selquicode.go4lunch.ui.map.MapViewFragment;
import fr.selquicode.go4lunch.ui.map.MapViewViewModel;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewFragment;
import fr.selquicode.go4lunch.ui.workmates.WorkmatesViewViewModel;

/**
 * Class who wil create a new ViewModel for every new activity/fragment
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static volatile ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if(factory == null){
            synchronized (ViewModelFactory.class){
                if(factory == null){
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    //Injection : reaching PlacesNearbySearchResponseAPI by putting it in repository constructor
    private final PlaceRepository repository = new PlaceRepository(RetrofitService.getPlaceAPI());

    /**
     * Constructor
     */
    private ViewModelFactory(){}

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
            return (T) new MainViewModel(repository);
        } else if(modelClass.isAssignableFrom(MapViewViewModel.class)){
            return (T) new MapViewViewModel(repository);
        } else if(modelClass.isAssignableFrom(ListViewViewModel.class)){
            return (T) new ListViewViewModel(repository);
        } else if(modelClass.isAssignableFrom(WorkmatesViewViewModel.class)){
            return (T) new WorkmatesViewViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}