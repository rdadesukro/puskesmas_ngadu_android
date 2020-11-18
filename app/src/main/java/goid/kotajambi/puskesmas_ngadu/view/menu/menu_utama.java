package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.github.squti.guru.Guru;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.adapter.ViewPagerAdapter;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_notif;
import goid.kotajambi.puskesmas_ngadu.model.jumlah_laporan_saya.Response_jumlah;
import goid.kotajambi.puskesmas_ngadu.model.notif.IsiItem_notif;
import goid.kotajambi.puskesmas_ngadu.model.simpan.Response_simpan;
import goid.kotajambi.puskesmas_ngadu.presenter.login;
import goid.kotajambi.puskesmas_ngadu.presenter.notif;
import goid.kotajambi.puskesmas_ngadu.view.menu_fragment.fragment_home;
import goid.kotajambi.puskesmas_ngadu.view.menu_fragment.fragment_report;
import goid.kotajambi.puskesmas_ngadu.view.menu_fragment.fragment_news;
import goid.kotajambi.puskesmas_ngadu.view.menu_fragment.fragment_profil;
import goid.kotajambi.puskesmas_ngadu.view.view_notif;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_utama extends AppCompatActivity implements view_notif {
    BottomNavigationView bottomNavigationView ;


    fragment_home home;
    fragment_report myreport;
    fragment_profil profil;
    fragment_news news;
    ProgressDialog progressDialog;
    MenuItem prevMenuItem;
    ViewPager vg;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private ActionBar toolbar;
    int value;
    public static BadgeDrawable badge;
    String id_status_notif,id_user;
    int jumlah_notif=0,total_notif;
    notif countryPresenter;
    boolean cek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        ButterKnife.bind(this);
        requestCameraPermission(MEDIA_TYPE_IMAGE);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Home");
        toolbar.setIcon(R.drawable.ic_baseline_add_circle_outline_24);
        value = Integer.parseInt(Guru.getString("Fragmentone", "3"));
//        countryPresenter = new notif(this, menu_utama.this);
//        countryPresenter.get_notif();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        get_status();
        badge = bottomNavigationView.getOrCreateBadge(R.id.Notifikasi);
        badge.setVisible(false);
      //  badge.setNumber(3);
       // badge.setNumber(Integer.parseInt(Guru.getString("badge", "1")));
        vg = (ViewPager) findViewById(R.id.vg);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.data:
                                vg.setCurrentItem(0);
                                getSupportActionBar().setTitle("Home");

                                break;
                            case R.id.lokasi:
                                vg.setCurrentItem(1);
                                getSupportActionBar().setTitle("Report");
                                break;
                            case R.id.Notifikasi:
                                vg.setCurrentItem(2);
                                getSupportActionBar().setTitle("Notifikasi");
                                break;
                            case R.id.profil:
                                vg.setCurrentItem(3);
                                getSupportActionBar().setTitle("Profil");
                                break;
                        }
                        return false;
                    }
                });

        vg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                if (position == 0) {
                    getSupportActionBar().setTitle("Home");
                } else if (position == 1) {
                    getSupportActionBar().setTitle("My Report");
                } else if (position == 2) {
                    getSupportActionBar().setTitle("News");
                } else {
                    getSupportActionBar().setTitle("Profil");
                }
                Log.d("page", "onPageSelected: " + position);

                Log.i("posisi", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(vg);
        vg.setCurrentItem(value);


    }
    public void get_status() {


        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_simpan> call = api.cek_status();

        call.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                try {
                    String kode = response.body().getKode();
                    Log.i("status_akun", "onResponse: "+kode);
                    if (kode.equals("0")){
                        dialog_status("Akun Anda Di Nonaktifkan","");

                    }else {

                    }

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {
                Log.i("cek_cek", "onResponse: " + t);
                t.printStackTrace();
            }
        });
    }
    void dialog_status(String judul,String pesan) {
        SweetAlertDialog pDialog = new SweetAlertDialog(menu_utama.this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setCancelable(false);
        pDialog.setTitleText("");
        pDialog.setContentText(judul);
        pDialog.setConfirmText("Ok");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                login countryPresenter = new login(null, menu_utama.this);
                countryPresenter.hapus_token(Guru.getString("token", "false"));
                countryPresenter.keluar(progressDialog);
                CustomIntent.customType(menu_utama.this, "fadein-to-fadeout");
                Intent intent = new Intent((Activity) menu_utama.this, menu_login.class);
                Guru.putString("Fragmentone", "0");
                intent.putExtra("Fragmentone", 0); //pass zero for Fragmentone.
                startActivity(intent);
            }
        });
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

    }
    private void setupViewPager(ViewPager vg) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        home = new fragment_home();
        myreport = new fragment_report();
        news = new fragment_news();
        profil = new fragment_profil();

        adapter.addFragment(home);
        adapter.addFragment(myreport);
        adapter.addFragment(news);
        adapter.addFragment(profil);
        vg.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(menu_utama.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)

                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {

                            } else {
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void notif(List<IsiItem_notif> notif) {
        Log.i("isi_event", "event: "+notif.size());

        for (int i = 0; i < notif.size(); i++) {
            id_status_notif = notif.get(i).getUserStatusRead();
            Log.i("id_status_notif", "notif: "+id_status_notif);
            cek = id_status_notif.contains(Guru.getString("id_user", "false"));
            if (cek){
                Log.i("jumlah_notif", "notif: "+"0");
            }else {
                jumlah_notif=jumlah_notif+1;
                Log.i("jumlah_notif", "notif: "+jumlah_notif);

            }


        }
        Log.i("hasil_jumlah_notif", "notif: "+jumlah_notif +" "+cek);
        //badge.setNumber(jumlah_notif);
    }
}