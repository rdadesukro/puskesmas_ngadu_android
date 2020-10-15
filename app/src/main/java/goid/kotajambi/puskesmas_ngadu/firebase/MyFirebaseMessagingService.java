package goid.kotajambi.puskesmas_ngadu.firebase;//package com.example.e_pelayanan.firebase;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//import com.example.e_pelayanan.menu.menu_utama;
//import com.example.e_pelayanan.menu.menu_utama_pejabat;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import goid.kotajambi.sipaten.R;
//
///**
// * Created by reale on 04/11/2016.
// */
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//
//
//
//    private static final String TAG = "MyFirebaseMsgService";
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Intent intent = null;
//        PendingIntent pendingIntent=null;
//        if (remoteMessage.getData() != null){
//            sendBroadcast(remoteMessage.getData().get("message"));
//        }
//
//
//        // TODO(developer): Handle FCM messages here.
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Data Payload" + remoteMessage.getData().toString());
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                sendPushNotification(json);
//            } catch (Exception e) {
//                Log.e(TAG, "Error" + e.getMessage());
//            }
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            //sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                sendPushNotification(json);
//            } catch (Exception e) {
//                Log.e(TAG, "Error" + e.getMessage());
//            }
//        }
//        if (remoteMessage.getNotification() !=null){
//
//            if (remoteMessage.getNotification().getClickAction().equals("admin")) {
//                intent = new Intent(getApplicationContext(), menu_utama_pejabat.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                 pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                        PendingIntent.FLAG_ONE_SHOT);
//            }
//
//            else if (remoteMessage.getNotification().getClickAction().equals("warga")) {
//                intent = new Intent(getApplicationContext(), menu_utama.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                 pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                        PendingIntent.FLAG_ONE_SHOT);
//                MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
//            }
//            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    + "://" + this.getPackageName() + "/raw/allert");
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            // pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//            String channelId = "Default";
//            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
//                    .setSmallIcon(R.drawable.logo_sipaten)
//                    .setSound(defaultSoundUri)
//                    .setContentTitle(remoteMessage.getNotification().getTitle())
//                    .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent)
//                    .setContentIntent(pendingIntent);
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            builder.setSound(sound);
//
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel(channelId, "Default", NotificationManager.IMPORTANCE_DEFAULT);
//                manager.createNotificationChannel(channel);
//            }
//            manager.notify(0, builder.build());
//        }
//
//        if (remoteMessage.getNotification().getClickAction().equals("admin")) {
//            intent = new Intent(getApplicationContext(), menu_utama_pejabat.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//        }
//
//        else if (remoteMessage.getNotification().getClickAction().equals("warga")) {
//            intent = new Intent(getApplicationContext(), menu_utama.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
//        }
//    }
//
//
//    private void sendBroadcast(String message){
//        Intent intent = new Intent();
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//    }
//
//    private void sendNotification(String messageBody,String title) {
//        try {
//
//
//
//            Intent intent = new Intent(this, menu_utama_pejabat.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//
//            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                    .setSmallIcon(R.drawable.logo_sipaten)
//                    .setContentTitle(title)
//                    .setContentText(messageBody)
//                    .setAutoCancel(true)
//                    .setSound(defaultSoundUri)
//                    .setContentIntent(pendingIntent);
//
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            notificationManager.notify(0, notificationBuilder.build());
//        }catch (Exception e){
//
//            Log.e(TAG, "error" + e.getMessage());
//        }
//
//    }
//
//
//    private void sendPushNotification(JSONObject json) {
//        //optionally we can display the json into log
//        Log.e(TAG, "Notification JSON " + json.toString());
//        try {
//            //getting the json data
//            JSONObject data = json.getJSONObject("data");
//
//            //parsing json data
//            String title = data.getString("title");
//            String message = data.getString("message");
//            String imageUrl = data.getString("image");
//
//            //creating MyNotificationManager object
//            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
//
//            //creating an intent for the notification
//            Intent intent = new Intent(this, menu_utama_pejabat.class);
//           // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            //if there is no image
//            if(imageUrl.equals("null")){
//                //displaying small notification
//                mNotificationManager.showSmallNotification(title, message, intent);
//            }else{
//                //if there is an image
//                //displaying a big notification
//                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }
//    }
//
//}
