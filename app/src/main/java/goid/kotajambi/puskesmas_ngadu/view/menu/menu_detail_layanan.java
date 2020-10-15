package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.squti.guru.Guru;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;

public class menu_detail_layanan extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.txt_nama)
    TextView txtNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_layanan);
        ButterKnife.bind(this);

        txtNama.setText(Guru.getString("nama", "false"));
        webview.requestFocus();
        webview.getSettings().setLightTouchEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadDataWithBaseURL("", Guru.getString("isi", "false"), "text/html", "UTF-8", "");
    }
}