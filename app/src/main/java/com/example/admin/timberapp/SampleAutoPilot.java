package com.example.admin.timberapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.Autopilot;
import com.urbanairship.UAirship;

import static java.security.AccessController.getContext;

/**
 * Created by Admin on 8/31/2017.
 */

public class SampleAutoPilot extends Autopilot {

    @Override
    public void onAirshipReady(@NonNull UAirship airship) {
        airship.getPushManager().setUserNotificationsEnabled(true);

        // Android O
        if (Build.VERSION.SDK_INT >= 26) {
            Context context = UAirship.getApplicationContext();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel("customChannel", "TestChannel", 1);

            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public AirshipConfigOptions createAirshipConfigOptions(@NonNull Context context) {
        AirshipConfigOptions options = new AirshipConfigOptions.Builder()
                .setDevelopmentAppKey("aMzmmTkSTi-odJZT1El71w")
                .setDevelopmentAppSecret("8eMM2QVLShGS3rs9cbXPHQ")
                .setInProduction(!BuildConfig.DEBUG)
                .setGcmSender("462716945501") // FCM/GCM sender ID
                .setNotificationChannel("customChannel")
                .build();

        return options;
    }
}