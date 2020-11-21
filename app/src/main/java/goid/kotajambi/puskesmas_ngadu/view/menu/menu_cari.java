package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.squti.guru.Guru;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.model.komen.Result_komen;
import goid.kotajambi.puskesmas_ngadu.model.laporan_komen.ResultItem_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.presenter.komen;
import goid.kotajambi.puskesmas_ngadu.view.view_komen;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.TlsVersion;

public class menu_cari extends AppCompatActivity implements view_komen {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.cari)
    SearchView cari;
    ProgressDialog pd;

    adapter_laporan_komen adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_cari);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .allEnabledTlsVersions()
                .supportsTlsExtensions(false)
                .allEnabledCipherSuites()
                .build();
        pd = new ProgressDialog(this);
        cari.setQueryHint("Cari Bersasarkan Kode Laporan");
        cari.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                cari.clearFocus();
                komen countryPresenter = new komen(menu_cari.this, menu_cari.this);
                countryPresenter.cari_laporan(String.valueOf(cari.getQuery()));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                komen countryPresenter = new komen(menu_cari.this, menu_cari.this);
                countryPresenter.cari_laporan(String.valueOf(cari.getQuery()));
                return false;
            }
        });
    }

    @Override
    public void komen(List<Result_komen> bener) {

    }

    @Override
    public void jumlah(String jumlah) {

    }

    @Override
    public void laporan_komen(List<ResultItem_laporan_komen> laporan_komen) {
        Log.i("laporan_komen", "komen: " + laporan_komen.size());
        adapter = new adapter_laporan_komen(this,laporan_komen,1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        if (laporan_komen.size() == 0) {
            new GlideToast.makeToast(menu_cari.this, "Data Yang Anda Cari Tidak Ada", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
        } else {

        }
    }
}