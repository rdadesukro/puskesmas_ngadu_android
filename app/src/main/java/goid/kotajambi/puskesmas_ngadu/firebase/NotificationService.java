package goid.kotajambi.puskesmas_ngadu.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_utama;


public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = "";
        String body = "";
        String click_action="";

        Intent intent;
        PendingIntent pendingIntent = null;
        title = remoteMessage.getNotification().getTitle();
        body = remoteMessage.getNotification().getBody();
        click_action=remoteMessage.getNotification().getClickAction();
//        if (remoteMessage.getNotification() != null) {
//
//            Log.i("ckckckck", "onMessageReceived: "+click_action);
//
//
//            if (click_action.equals("admin")) {
//                intent = new Intent(this, menu_utama_pejabat.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//            }
//
//            else if (click_action.equals("warga")) {
//                intent = new Intent(this, menu_utama.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//            }
//
//        }

        int notificationColor = getResources().getColor(R.color.amber_100);
        int notificationIcon = getNotificationIcon();
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String channelId = "ini";
        int notification_id = 123;

        createNotificationChannel();
        long[] pattern = {500, 500, 500, 500, 500};
        Log.i("isi_klik", "onMessageReceived: "+click_action);

        if (click_action==null){

//            Toast.makeText(this, "Kosong", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, menu_utama.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }else {
            if (click_action.equals("admin")) {
                intent = new Intent(this, menu_utama.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            }

            else if (click_action.equals("warga")) {
                intent = new Intent(this, menu_utama.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            }
        }



        //notif click foreground
//        intent = new Intent(this, menu_utama.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_baseline_add_24)
                .setColor(notificationColor)
                .setShowWhen(true)
                .setNumber(2)
                .setVibrate(pattern)
                .setSound(notificationSound)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent).build();


        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());

        manager.notify(notification_id, notification);
    }

    //khusus android versi Oreo
    private void createNotificationChannel() {
        String channelId = "ini";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ini";
            String description = "tes";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            channel.setVibrationPattern(new long[]{0L});
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }

    private int getNotificationIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return R.mipmap.ic_launcher;
        }
        return R.mipmap.ic_launcher;
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN", s);

    }

}