package fr.selquicode.go4lunch.domain;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;

import androidx.work.WorkManager;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.TimeUnit;


public class NotificationSchedule {

    //TODO : schedule notification here + add notificationWorker to WorManager
    // declencher une notif a 12H tous les jours
    WorkManager workManager;

    public NotificationSchedule(WorkManager workManager) {
        this.workManager = workManager;
    }

    public void scheduleNotification(){
//        ZoneId z = ZoneId.of("Europe/Paris");
        LocalTime currentTime = LocalTime.now();
        LocalTime dueTime = LocalTime.NOON;

        if(dueTime.isBefore(currentTime)){
            dueTime.plusHours(24);
        }
        long initialDelay = dueTime.getSecond() - currentTime.getSecond();
//        .setInitialDelay( initialDelay, TimeUnit.SECONDS)

        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .build();
        workManager.enqueueUniqueWork("notification_id", ExistingWorkPolicy.REPLACE, uploadWorkRequest);
    }

}
