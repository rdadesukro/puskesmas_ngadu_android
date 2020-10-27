package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.adapter.PaginatedAdapter;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_laporan_saya;
import goid.kotajambi.puskesmas_ngadu.model.laporan_saya.DataItem;
import goid.kotajambi.puskesmas_ngadu.model.laporan_saya.Response_laporan_saya;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.TlsVersion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_laporan_saya extends AppCompatActivity {

    @BindView(R.id.rv_aku)
    RecyclerView rvAku;
    @BindView(R.id.swifeRefresh)
    SwipeRefreshLayout swifeRefresh;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.relativeLayout)
    ConstraintLayout relativeLayout;
    @BindView(R.id.img_data)
    ImageView imgData;
    @BindView(R.id.txt_data)
    TextView txtData;
    private RecyclerView.LayoutManager mManager, manager;
    List<DataItem> data;
    adapter_laporan_saya adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_laporan_saya);
        ButterKnife.bind(this);
        try {
            ProviderInstaller.installIfNeeded(menu_laporan_saya.this);
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
        rvAku = (RecyclerView) findViewById(R.id.rv_aku);
        swifeRefresh = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        adapter = new adapter_laporan_saya(menu_laporan_saya.this);
        adapter.setDefaultRecyclerView(menu_laporan_saya.this, rvAku);

        adapter.setOnPaginationListener(new PaginatedAdapter.OnPaginationListener() {
            @Override
            public void onCurrentPage(int page) {
                //  Toast.makeText(getActivity(), "Page " + page + " loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNextPage(int page) {
                progressBar.setVisibility(View.VISIBLE);
                getNewItems(page);

            }

            @Override
            public void onFinish() {
                // Toast.makeText(getActivity(), "finish", Toast.LENGTH_SHORT).show();
            }
        });


        getNewItems(adapter.getStartPage());

        swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = new adapter_laporan_saya(menu_laporan_saya.this);
                adapter.setDefaultRecyclerView(menu_laporan_saya.this, rvAku);

                adapter.setOnPaginationListener(new PaginatedAdapter.OnPaginationListener() {
                    @Override
                    public void onCurrentPage(int page) {
                        // Toast.makeText(getActivity(), "Page " + page + " loaded!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNextPage(int page) {
                        progressBar.setVisibility(View.VISIBLE);
                        getNewItems(page);

                    }

                    @Override
                    public void onFinish() {
                        //Toast.makeText(getActivity(), "finish", Toast.LENGTH_SHORT).show();
                    }
                });


                getNewItems(adapter.getStartPage());

            }
        });


    }

    public void onGetDate(List<DataItem> users) {

        adapter.submitItems(users);
    }

    private void getNewItems(final int page) {


                ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
                Call<Response_laporan_saya> call = api.pagni_saya(page);

                call.enqueue(new Callback<Response_laporan_saya>() {
                    @Override
                    public void onResponse(Call<Response_laporan_saya> call, Response<Response_laporan_saya> response) {

                        try {

                            if (response.isSuccessful()) {
                                progressBar.setVisibility(View.VISIBLE);
                                // Pd.hide();
                                data = response.body().getResult().getData();
                                int page1 = response.body().getResult().getTotal();
                                Log.i("isi_data", "onResponse: " + data);
                                //Toast.makeText(getActivity(), ""+response.body().getResult().getTo(), Toast.LENGTH_SHORT).show();
                                int jumlah = response.body().getResult().getPerPage();
                                adapter.setPageSize(response.body().getResult().getPerPage());

                                onGetDate(data);
                                swifeRefresh.setRefreshing(false);


                                if (data.size() == 0 && page1==0 ) {
                                    txtData.setVisibility(View.VISIBLE);
                                    imgData.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);

                                } else {
                                    txtData.setVisibility(View.GONE);
                                    imgData.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);

                                    // mShimmerViewContainer.setVisibility(View.GONE);
                                }
                            } else {
                                // error case
                                switch (response.code()) {
                                    case 404:
                                        //Toast.makeText(getActivity(), "not found!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        //Toast.makeText(getActivity(), "server broken", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        //Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            Log.i("cekekek", "onResponse: " + e);
                            Log.e("onResponse", "There is an error" + e);
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<Response_laporan_saya> call, Throwable t) {
                        t.printStackTrace();
                        Log.i("cek_error", "onFailure: " + t);
                        if (t instanceof IOException) {
                            // Toast.makeText(getActivity(), "Jaringan Anda Bermasalah", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(menu_berita_kota_jambi.this, "Jaringan Anda Bermasalah", Toast.LENGTH_SHORT).show();

                        } else {
                            // Toast.makeText(getActivity(), "Server Donwn", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}