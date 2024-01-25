package fr.selquicode.go4lunch;

import android.app.Application;
import android.util.Log;

public class MainApplication extends Application {
    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static Application getApplication() {
        return sApplication;
    }

}
