package goid.kotajambi.puskesmas_ngadu.firebase;//package com.example.e_pelayanan.firebase;
//
//import android.content.Intent;
//
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
///**
// * Created by reale on 04/11/2016.
// */
//
//public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
//
//    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        System.out.println("New Reg ID/Token: "+refreshedToken);
//        // Notify UI that registration has completed, so the progress indicator can be hidden.
//        Intent registrationComplete = new Intent(Configuration.REGISTRATION_COMPLETE);
//        registrationComplete.putExtra("token", refreshedToken);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
//
//
//    }
//
//    public void onTaskRemoved(Intent rootIntent) {
//
//        //unregister listeners
//        //do any other cleanup if required
//
//        //stop service
//        stopSelf();
//    }
//}
