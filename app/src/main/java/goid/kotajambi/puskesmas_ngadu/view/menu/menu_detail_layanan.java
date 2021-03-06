package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.squti.guru.Guru;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.adapter.Mydate;

public class menu_detail_layanan extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.txt_nama)
    TextView txtNama;

    @BindView(R.id.txt_tgl)
    TextView txt_tgl;
    String tgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_layanan);
        ButterKnife.bind(this);
        tgl =Guru.getString("tgl_layanan", "false");
        String date =tgl.substring(8,10);
        String month= Mydate.konversi_bulan(tgl.substring(5,7));
        String year =tgl.substring(0,4);
        txt_tgl.setText(date+"-"+month+"-"+year);
        txtNama.setText(Guru.getString("nama_layanan", "false"));
        webview.requestFocus();
        webview.getSettings().setLightTouchEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadDataWithBaseURL("", Guru.getString("isi_layanan", "false"), "text/html", "UTF-8", "");
    }
}