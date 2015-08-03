package com.beatbit.analytics;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Daniel on 8/2/2015.
 */
public class PatientDangerDetected {
    private Patient patient;

    public PatientDangerDetected(Patient patient) {
        this.patient = patient;
    }

    private void pushNotification(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.heart)
                .setContentTitle("Cardiac Arrest Detected for " + patient.getName() + "!")
                .setContentText(patient.getName() + " may be in danger");

        int mNotificationId = 001;

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
