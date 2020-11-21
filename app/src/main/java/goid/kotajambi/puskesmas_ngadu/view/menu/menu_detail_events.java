package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.squti.guru.Guru;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.adapter.GeocodingLocation;
import goid.kotajambi.puskesmas_ngadu.adapter.Mydate;

public class menu_detail_events extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.txt_jam)
    TextView txtJam;
    @BindView(R.id.txt_tgl)
    TextView txtTgl;
    @BindView(R.id.txt_judul)
    TextView txtJudul;
    @BindView(R.id.img_foto)
    ImageView imgFoto;
    @BindView(R.id.wvview)
    WebView webview;
    @BindView(R.id.txt_lokasi)
    TextView txtLokasi;
    private GoogleMap gMap;
    String goolgeMap = "com.google.android.apps.maps";
    double lat1, lng1;
    double new_lokasi;
    double lat_new, lng_new;
    MapFragment mapFragment;
    GeocodingLocation locationAddress;
    String tgl_mulai,tgl_akhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_events);
        ButterKnife.bind(this);
        locationAddress = new GeocodingLocation();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtJudul.setText(Guru.getString("jd_event", "false"));
        txtLokasi.setText(Guru.getString("lokasi", "false"));
        tgl_mulai = (Guru.getString("tgl_mulai", "false"));
        String date =tgl_mulai.substring(0,2);
        String month= Mydate.konversi_bulan(tgl_mulai.substring(3,5));
        String year =tgl_mulai.substring(6,10);

        txtTgl.setText(date+"-"+month+"-"+year);
        Log.i("monthhhh", "onCreate: "+year);
        txtJam.setText(Guru.getString("waktu_event", "false")+" WIB");
        webview.requestFocus();
        webview.getSettings().setLightTouchEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        lat1 = Double.parseDouble(Guru.getString("lat", "false"));
        lng1 = Double.parseDouble(Guru.getString("lng", "false"));
        webview.loadDataWithBaseURL("", Guru.getString("isi_event", "false"), "text/html", "UTF-8", "");
        Glide.with(this)
                .load("https://ramahpkmhandil.jambikota.go.id/uploads/events/" + Guru.getString("foto_event", "false"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgFoto);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (lat1==""){
//
//        }


        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng SYDNEY = null;
        LatLng MOUNTAIN_VIEW = null;
        SYDNEY = new LatLng(lat1, lng1);
        MOUNTAIN_VIEW = new LatLng(lat1, lng1);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));
        gMap.animateCamera(CameraUpdateFactory.zoomIn());
        gMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LatLng demak = new LatLng(lat1, lng1);
        gMap.addMarker(new MarkerOptions().position(demak).title(Guru.getString("lokasi", "false")));

    }
}