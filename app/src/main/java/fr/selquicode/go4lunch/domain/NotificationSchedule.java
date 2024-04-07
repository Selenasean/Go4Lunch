package fr.selquicode.go4lunch.domain;

import android.util.Log;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;

import androidx.work.WorkManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.TimeUnit;


public class NotificationSchedule {

    //TODO : schedule notification here + add notificationWorker to WorManager
    // declencher une notif a 12H tous les jours
    private String REMIND_TO_EAT = "notification_id";
    WorkManager workManager;

    public NotificationSchedule(WorkManager workManager) {
        this.workManager = workManager;
    }

    public void scheduleNotification(){
        ZonedDateTime currentTime = ZonedDateTime.now();
        Log.i("localtime", String.valueOf(currentTime));
        ZonedDateTime dueTime = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.NOON), ZoneId.systemDefault());
        Log.i("initialdelay", "duetime = " + dueTime + ", currentTime = " + currentTime  );
        ZonedDateTime dueTimeFinal;
        if(dueTime.isBefore(currentTime)){
            dueTimeFinal = dueTime.plusDays(1);
        }else {
            dueTimeFinal = dueTime;
        }
        long initialDelay = dueTimeFinal.toEpochSecond() - currentTime.toEpochSecond();
        Log.i("initialdelay", "initialdelay =" + initialDelay+ "dueTimeFinale = "+ dueTimeFinal);

        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay( initialDelay, TimeUnit.SECONDS)
                .build();
        workManager.enqueueUniqueWork(REMIND_TO_EAT, ExistingWorkPolicy.REPLACE, uploadWorkRequest);
    }

    /**
     * To cancelled work into WorkManager
     */
    public void notificationToCancelled(){
        workManager.cancelUniqueWork(REMIND_TO_EAT);
    }

}
