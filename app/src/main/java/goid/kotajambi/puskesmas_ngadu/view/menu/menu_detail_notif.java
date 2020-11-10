package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.squti.guru.Guru;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.login;
import goid.kotajambi.puskesmas_ngadu.presenter.notif;

public class menu_detail_notif extends AppCompatActivity {

    @BindView(R.id.txt_body)
    TextView txtBody;
    @BindView(R.id.txt_tgl)
    TextView txtTgl;
    @BindView(R.id.txt_judul)
    TextView txtJudul;
    @BindView(R.id.webview)
    WebView webview;
    String status_baca;
    String data_satus;
    String hilangkan;
    String tambah_status;
    private List<String> array_syarat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_notif);
        ButterKnife.bind(this);

        status_baca =Guru.getString("status_notif", "false");
        data_satus = Guru.getString("status_notif", "false");
        boolean cek = status_baca.contains(Guru.getString("id_user", "false"));
        if (cek){
            Toast.makeText(this, "udah di baca", Toast.LENGTH_SHORT).show();

        }else {
            if (data_satus!=""){
                hilangkan = data_satus.substring(1, data_satus.length() - 1);
                tambah_status= hilangkan+","+Guru.getString("id_user", "false");
                array_syarat.add(tambah_status);
                notif countryPresenter = new notif(null, menu_detail_notif.this);
                countryPresenter.edit_status(Guru.getString("id_notif", "false"), String.valueOf(array_syarat));

            }else {
                array_syarat.add(Guru.getString("id_user", "false"));
                notif countryPresenter = new notif(null, menu_detail_notif.this);
                countryPresenter.edit_status(Guru.getString("id_notif", "false"), String.valueOf(array_syarat));
            }

        }

        txtJudul.setText(Guru.getString("judul_notif", "false"));
        txtTgl.setText(Guru.getString("tgl_notif", "false"));
        txtBody.setText(Guru.getString("body_notif", "false"));



        webview.requestFocus();
        webview.getSettings().setLightTouchEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadDataWithBaseURL("", Guru.getString("konten_notif", "false"), "text/html", "UTF-8", "");
    }
}