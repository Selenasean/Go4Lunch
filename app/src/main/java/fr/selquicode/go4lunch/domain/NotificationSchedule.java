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

    private final String REMIND_TO_EAT = "notification_id";
    private final WorkManager workManager;

    public NotificationSchedule(WorkManager workManager) {
        this.workManager = workManager;
    }

    /**
     * To schedule the notification at 12h
     * Enqueue the notification in the WorkManager flow
     */
    public void scheduleNotification(){
        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime dueTime = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.NOON), ZoneId.systemDefault());
        ZonedDateTime dueTimeFinal;
        if(dueTime.isBefore(currentTime)){
            dueTimeFinal = dueTime.plusDays(1);
        }else {
            dueTimeFinal = dueTime;
        }
        long initialDelay = dueTimeFinal.toEpochSecond() - currentTime.toEpochSecond();

        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(initialDelay, TimeUnit.SECONDS)
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
