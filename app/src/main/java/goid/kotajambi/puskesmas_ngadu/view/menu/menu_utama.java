package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    private ActionBar toolbar;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        ButterKnife.bind(this);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Home");
        toolbar.setIcon(R.drawable.ic_baseline_add_circle_outline_24);


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
}