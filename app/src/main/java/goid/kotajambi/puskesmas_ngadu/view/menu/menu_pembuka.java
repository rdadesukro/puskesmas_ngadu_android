package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.github.squti.guru.Guru;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.trncic.library.DottedProgressBar;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.model.jumlah_laporan_saya.Response_jumlah;
import goid.kotajambi.puskesmas_ngadu.model.layanan.IsiItem_layanan;
import goid.kotajambi.puskesmas_ngadu.model.layanan.Response_layanan;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_pembuka extends AppCompatActivity {

    @BindView(R.id.progress2)
    DottedProgressBar progress2;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pembuka);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        progress2.setVisibility(View.VISIBLE);
        progress2.startProgress();
        checkInternet();

    }
    void cek_update(){
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        if (isUpdateAvailable){
                            new AppUpdater(menu_pembuka.this)
                                    .setTitleOnUpdateAvailable("Pembaruan apikasi tersedia")
                                    .setContentOnUpdateAvailable("Silahkan perbarui Aplikasi PakarKasih melalui playstore ")
                                    .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                                    .setDisplay(Display.DIALOG)
                                    .setCancelable(false)
                                    .setButtonDismiss("Nanti Saja")
                                    .setButtonDoNotShowAgain(null)
                                    .setButtonUpdate("Update Sekarang?")
                                    .showAppUpdated(true)
                                    .setButtonDismissClickListener(new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    startActivity(new Intent(getApplicationContext(),menu_login.class));
                                                    finish();
                                                }
                                            }, 3000L); //3000 L = 3 detik
                                        }
                                    })
                                    .start();
                        }else {

                            // Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                            //  Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
                            //   imageView39.startAnimation(slidedown);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getApplicationContext(),menu_login.class));
                                    finish();
                                }
                            }, 3000L); //3000 L = 3 detik
                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });
        appUpdaterUtils.start();
    }
    public boolean checkInternet(){
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            connectStatus = true;
            get_server();

        }
        else {
            connectStatus = false;
            dioalog("Anda Membutuhkan Koneksi Untuk Membuka Aplikasi Ini");
            // Display message in dialog box if you have internet connection

        }
        return connectStatus;
    }
    void get_server() {
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_layanan> call = api.cek_server();
        call.enqueue(new Callback<Response_layanan>() {
            @Override
            public void onResponse(Call<Response_layanan> call, Response<Response_layanan> response) {

                try {

                    if (response.isSuccessful()) {
                        cek_update();




                    } else {
                        // error case
                        switch (response.code()) {
                            case 404:
                                dioalog("Not Found");
                                //Toast.makeText(ctx, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                dioalog("server Broken");
                               //Toast.makeText(ctx,"server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                dioalog("Unknown Error");
                                //Toast.makeText(ctx, "unknown error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                } catch (Exception e) {
                    Log.e("onResponse", "There is an error" + e);
                    //data();
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<Response_layanan> call, Throwable t) {
                t.printStackTrace();
                //  sliderView_bener.setBackgroundResource(R.drawable.bg_no_item_city);
                Log.i("ewkwkwkwkw", "onFailure: " + t);
                if (t instanceof IOException) {

                } else {

                }

            }
        });



    }
    void dioalog(String Pesan){
        SweetAlertDialog pDialog = new SweetAlertDialog(menu_pembuka.this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Oppsss....!!");
        pDialog.setContentText(Pesan);
        pDialog.setCancelable(false);
        pDialog.setConfirmText("OK");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                // init_get_laporan_baru_saya();
                finish();

            }
        });
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    }