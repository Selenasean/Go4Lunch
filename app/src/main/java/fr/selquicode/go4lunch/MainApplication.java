package fr.selquicode.go4lunch;

import static com.google.gson.internal.$Gson$Types.arrayOf;

import android.Manifest;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.Arrays;

import fr.selquicode.go4lunch.ui.MainActivity;

public class MainApplication extends Application {
    private static Application sApplication;
    public static final String CHANNEL_ID = "MAIN_CHANNEL";

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


    }

    public static Application getApplication() {
        return sApplication;
    }

}
