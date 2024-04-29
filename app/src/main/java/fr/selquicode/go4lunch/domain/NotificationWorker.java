package fr.selquicode.go4lunch.domain;

import static fr.selquicode.go4lunch.R.string.notification_reminder;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;
import java.util.stream.Collectors;

import fr.selquicode.go4lunch.MainApplication;
import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.ui.detail.DetailActivity;


public class NotificationWorker extends Worker {

    private final FirestoreRepository firestoreRepository;
    private final FirebaseAuthRepository firebaseAuthRepository;
    private final String userLoggedId;
    public static int NOTIFICATION_ID = 1;
    private final Context context;


    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
        this.context = context;
        firestoreRepository = MainApplication.getApplication().getFirestoreRepository();
        firebaseAuthRepository = MainApplication.getApplication().getFirebaseAuthRepository();
        userLoggedId = firebaseAuthRepository.getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            if (firebaseAuthRepository.isUserLogged()) {
                //get user logged data synchronously
                User userLogged = firestoreRepository.getUserLoggedSynchronously(userLoggedId);
                String placeId = userLogged.getRestaurantId();

                if (placeId == null) {
                    return Result.success();
                }
                List<User> workmatesEatingTogether = firestoreRepository.getUsersWhoChooseSynchronously(placeId);
                String restaurantName = userLogged.getRestaurantName();
                String restaurantAddress = userLogged.getRestaurantAddress();
                //build text for notification
                String notificationText = context.getString(R.string.first_part_notif, restaurantName, restaurantAddress);

                // create a string with workmates who has chosen the same restaurant to eat as user logged
                String workmatesNames = workmatesEatingTogether.stream()
                        .filter(user -> !user.getId().equals(firebaseAuthRepository.getCurrentUser().getUid()))
                        .map(user -> user.getDisplayName().contains(" ")?
                                user.getDisplayName().split(" ")[0] : user.getDisplayName())
                        .collect(Collectors.joining(", "));
                if (!workmatesNames.isEmpty()) {
                    notificationText += " " +context.getString(R.string.second_part_notif, workmatesNames) ;
                }
                //create the notification
                createNotification(notificationText, placeId);
            }else {
                return Result.failure();
            }
        } catch (Exception e) {
            return Result.failure();
        }
        return Result.success();
    }


    /**
     * Method to build the notification we want to send
     * @param text type String - the body of the notification
     * @param placeId type String - id of the place we want to redirect the user when the notification is clicked
     */
    private void createNotification(String text, String placeId) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            //create an pendingIntent to open te DetailsActivity when user tap the notification
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.PLACE_ID, placeId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), MainApplication.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_food)
                    .setContentTitle(context.getString(notification_reminder))
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat.from(getApplicationContext()).notify(NOTIFICATION_ID, builder.build());
        } else {
            Toast.makeText(getApplicationContext(), context.getString(R.string.give_notif_permission), Toast.LENGTH_LONG).show();
        }
    }
}
