package fr.selquicode.go4lunch;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.location.LocationRepository;
import fr.selquicode.go4lunch.data.model.Place;
import fr.selquicode.go4lunch.data.place.PlaceRepository;
import fr.selquicode.go4lunch.data.place.RetrofitService;

public class MainApplication extends Application {
    private static Application sApplication;
    public static final String CHANNEL_ID = "MAIN_CHANNEL";
    private LocationRepository locationRepository;
    private FirebaseAuthRepository firebaseAuthRepository;
    private FirestoreRepository firestoreRepository;
    private PlaceRepository placeRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channel description text";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system;
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        locationRepository = new LocationRepository(LocationServices.getFusedLocationProviderClient(this));
        firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());
        firestoreRepository = new FirestoreRepository(FirebaseFirestore.getInstance());
        placeRepository = new PlaceRepository(RetrofitService.getPlaceAPI());

    }

    public static MainApplication getApplication() {
        return (MainApplication) sApplication;
    }

    public LocationRepository getLocationRepository() {
        return locationRepository;
    }

    public FirebaseAuthRepository getFirebaseAuthRepository() {
        return firebaseAuthRepository;
    }

    public FirestoreRepository getFirestoreRepository() {
        return firestoreRepository;
    }

    public PlaceRepository getPlaceRepository() {
        return placeRepository;
    }
}
