package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.adapter.ViewPagerAdapter;
import goid.kotajambi.puskesmas_ngadu.view.menu_fragment.fragment_home;
import goid.kotajambi.puskesmas_ngadu.view.menu_fragment.fragment_report;
import goid.kotajambi.puskesmas_ngadu.view.menu_fragment.fragment_news;
import goid.kotajambi.puskesmas_ngadu.view.menu_fragment.fragment_profil;

public class menu_utama extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;


    fragment_home home;
    fragment_report myreport;
    fragment_profil profil;
    fragment_news news;

    MenuItem prevMenuItem;
    ViewPager vg;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private ActionBar toolbar;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        ButterKnife.bind(this);
        requestCameraPermission(MEDIA_TYPE_IMAGE);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Home");
        toolbar.setIcon(R.drawable.ic_baseline_add_circle_outline_24);
        value = getIntent().getIntExtra("Fragmentone", 3);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
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
                            case R.id.histori:
                                vg.setCurrentItem(2);
                                getSupportActionBar().setTitle("History");
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
}