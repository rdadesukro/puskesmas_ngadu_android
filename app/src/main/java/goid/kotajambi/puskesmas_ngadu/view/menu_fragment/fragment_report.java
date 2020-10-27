package goid.kotajambi.puskesmas_ngadu.view.menu_fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
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
import goid.kotajambi.puskesmas_ngadu.model.laporan_saya.Result_laporan_saya;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_utama;
import maes.tech.intentanim.CustomIntent;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.TlsVersion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_report extends Fragment {


    RecyclerView rvAku;
    SwipeRefreshLayout swifeRefresh;
    @BindView(R.id.relativeLayout)
    ConstraintLayout relativeLayout;
    @BindView(R.id.txt_data2)
    TextView txtData2;
    @BindView(R.id.img_data2)
    ImageView imgData2;

    private RecyclerView.LayoutManager mManager, manager;
    List<DataItem> data;
    adapter_laporan_saya adapter;
    ProgressBar progressBar;

    int new_page;
    int sama;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public fragment_report() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_data, container, false);
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);
        try {
            ProviderInstaller.installIfNeeded(getContext());
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
        rvAku = (RecyclerView) view.findViewById(R.id.rv_aku);
        swifeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swifeRefresh);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        adapter = new adapter_laporan_saya(getActivity());
        adapter.setDefaultRecyclerView(getActivity(), rvAku);

        adapter.setOnPaginationListener(new PaginatedAdapter.OnPaginationListener() {
            @Override
            public void onCurrentPage(int page) {
                //  Toast.makeText(getActivity(), "Page " + page + " loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNextPage(int page) {
                new_page=page;
                imgData2.setVisibility(View.GONE);
                txtData2.setVisibility(View.GONE);
                if (sama!=new_page){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
                // progressBar.setVisibility(View.VISIBLE);
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
                adapter.clear();
                adapter = new adapter_laporan_saya(getActivity());
                adapter.setDefaultRecyclerView(getActivity(), rvAku);

                adapter.setOnPaginationListener(new PaginatedAdapter.OnPaginationListener() {
                    @Override
                    public void onCurrentPage(int page) {
                        //  Toast.makeText(getActivity(), "Page " + page + " loaded!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNextPage(int page) {
                        new_page=page;
                        imgData2.setVisibility(View.GONE);
                        txtData2.setVisibility(View.GONE);
                        if (sama!=new_page){
                            progressBar.setVisibility(View.VISIBLE);
                        }else {
                            progressBar.setVisibility(View.GONE);
                        }
                        progressBar.setVisibility(View.VISIBLE);
                        getNewItems(page);

                    }

                    @Override
                    public void onFinish() {
                        // Toast.makeText(getActivity(), "finish", Toast.LENGTH_SHORT).show();
                    }
                });


                getNewItems(1);


            }
        });

        return view;


    }

    public void onGetDate(List<DataItem> users) {

        adapter.submitItems(users);
    }

    private void getNewItems(final int page) {


                ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
                Call<Response_laporan_saya> call = api.pagni(page);

                call.enqueue(new Callback<Response_laporan_saya>() {
                    @Override
                    public void onResponse(Call<Response_laporan_saya> call, Response<Response_laporan_saya> response) {

                        try {

                            if (response.isSuccessful()) {

                                progressBar.setVisibility(View.VISIBLE);
                                swifeRefresh.setRefreshing(false);
                                // Pd.hide();
                                data = response.body().getResult().getData();
                                int page1 = response.body().getResult().getTotal();
                                Log.i("isi_data", "onResponse: " + data);
                                Log.i("data_size", "onResponse: " + data.size());
                               // Toast.makeText(getActivity(), "" + response.body().getResult().getTo(), Toast.LENGTH_SHORT).show();
                                int jumlah = response.body().getResult().getPerPage();
                                adapter.setPageSize(response.body().getResult().getPerPage());
                                sama = response.body().getResult().getLastPage();

                                onGetDate(data);


                                if (response.body().getResult().getLastPage()!=new_page){
                                    progressBar.setVisibility(View.VISIBLE);
                                }else {
                                    progressBar.setVisibility(View.GONE);
                                }


                                if (data.size() == 0 && page1==0 ) {
                                    txtData2.setVisibility(View.VISIBLE);
                                    imgData2.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    txtData2.setVisibility(View.GONE);
                                    imgData2.setVisibility(View.GONE);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tes, menu);
        MenuItem refres = menu.findItem(R.id.refres);
        refres.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent((Activity) getActivity(), menu_utama.class);
                intent.putExtra("Fragmentone", 1); //pass zero for Fragmentone.
                startActivity(intent);
                CustomIntent.customType((Activity) getActivity(),"fadein-to-fadeout");

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
