package com.leanplum.android_mparticle;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.LeanplumPushNotificationCustomizer;
import com.leanplum.LeanplumPushService;
import com.leanplum.annotations.Parser;
import com.leanplum.annotations.Variable;
import com.leanplum.callbacks.StartCallback;
import com.leanplum.callbacks.VariablesChangedCallback;
import com.mparticle.MParticle;

/**
 * Created by fede on 9/19/16.
 */

public class ApplicationClass extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // Parsing for Variable to be registered in Leanplum Dashboard
        // This has to be done BEFORE starting Leanplum
        // Variables are defined in this case into LPvariables class
        Parser.parseVariables(this);
        Parser.parseVariablesForClasses(LPvariables.class);

        LeanplumPushService.setGcmSenderId(LeanplumPushService.LEANPLUM_SENDER_ID);

        LeanplumPushService.setCustomizer(new LeanplumPushNotificationCustomizer() {
            //
            @Override
            public void customize(NotificationCompat.Builder builder, Bundle notificationPayload) {

                // Setting a custom smallIcon included in the Drawable folder
                builder.setSmallIcon(R.drawable.atest);

                // Setting a custom largeIcon included in the Drawable folder
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.androidorange);
                builder.setLargeIcon(largeIcon);

                // Setting a custom Big Picture included in the Drawable folder, beneath the notification
                Bitmap androidBanner = BitmapFactory.decodeResource(getResources(), R.drawable.androidappsdev);
                builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidBanner));
            }
        });


        Leanplum.addStartResponseHandler(new StartCallback() {
            @Override
            public void onResponse(boolean b) {
                Log.i("### ", "Leanplum started -- application class");
            }
        });

        // Starting MParticle - this will also start Leanplum
//        MParticle.start(this, MParticle.InstallType.AutoDetect, MParticle.Environment.Production);

//        MParticle.start(this, MParticle.InstallType.AutoDetect, MParticle.Environment.Production);
        MParticle.start(this);

    }
}