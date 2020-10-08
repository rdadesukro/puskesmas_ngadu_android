package goid.kotajambi.puskesmas_ngadu.view.menu_fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.adapter.PaginatedAdapter;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_bener;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_berita_kota;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_laporan_saya;
import goid.kotajambi.puskesmas_ngadu.model.bener.ResultItem_bener;
import goid.kotajambi.puskesmas_ngadu.model.jumlah_laporan_saya.Response_jumlah;
import goid.kotajambi.puskesmas_ngadu.model.model_berita.PostsItem;
import goid.kotajambi.puskesmas_ngadu.model.slider.IsiItem_slider;
import goid.kotajambi.puskesmas_ngadu.presenter.home;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_lapor;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_laporan_saya;
import goid.kotajambi.puskesmas_ngadu.view.view_home;
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
public class fragment_home extends Fragment implements view_home {


    FloatingActionButton btnLapor;
    @BindView(R.id.arrowBtn)
    Button arrowBtn;

    @BindView(R.id.con_berita)
    ConstraintLayout conBerita;
    @BindView(R.id.card_Data)
    CardView cardData;
    adapter_berita_kota adapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.RecyclerView)
    RecyclerView RecyclerView;
    @BindView(R.id.bener)
    SliderView SliderView;
    @BindView(R.id.card_lapor_saya)
    CardView cardLaporSaya;
    @BindView(R.id.card_jadwal)
    CardView cardJadwal;
    @BindView(R.id.txt_jumlah)
    TextView txtJumlah;
    @BindView(R.id.swifeRefresh)
    SwipeRefreshLayout swifeRefresh;
    private RecyclerView.LayoutManager mManager;
    private List<PostsItem> data = new ArrayList<>();
    private adapter_bener adapter_bener;

    public fragment_home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_data, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        btnLapor = view.findViewById(R.id.btn_lapor);
        btnLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInput = new Intent(getActivity(), menu_lapor.class);
                getActivity().startActivity(goInput);
                CustomIntent.customType(getActivity(), "bottom-to-up");

            }
        });


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
//
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mManager = new LinearLayoutManager(getContext());
        RecyclerView.setLayoutManager(mManager);
//        RecyclerView.setHasFixedSize(true);
        home countryPresenter = new home(this, getActivity());
        countryPresenter.baner();
        get_jumlah();

        swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                countryPresenter.baner();
                get_jumlah();


            }
        });

        return view;


    }


    @OnClick(R.id.arrowBtn)
    public void onViewClicked() {
        if (conBerita.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardData, new AutoTransition());
            conBerita.setVisibility(View.VISIBLE);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
            home countryPresenter = new home(this, getContext());
            countryPresenter.berita();
        } else {
            TransitionManager.beginDelayedTransition(cardData, new AutoTransition());
            conBerita.setVisibility(View.GONE);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
        }
    }


    @Override
    public void berita(List<PostsItem> result) {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("datasaadad", "berita: " + result.size());

                    adapter = new adapter_berita_kota(getContext(), result, 1);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    RecyclerView.setLayoutManager(layoutManager);
                    RecyclerView.setAdapter(adapter);
                    if (result.size() == 0) {


                    } else {
                        progressBar.setVisibility(View.GONE);

                    }

                }

            }, 2500);

        } catch (Exception E) {
            Log.i("cek_eror", "berita: " + E);

        }

    }

    @Override
    public void bener(List<IsiItem_slider> bener) {
        adapter_bener = new adapter_bener(getActivity(), bener, "mas_1");
        SliderView.setSliderAdapter(adapter_bener);
        SliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        SliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        SliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        SliderView.setIndicatorSelectedColor(Color.WHITE);
        SliderView.setIndicatorUnselectedColor(Color.RED);
        SliderView.setScrollTimeInSec(4);
        SliderView.setAutoCycle(true);
        SliderView.startAutoCycle();
        //mRecycler.setAdapter(adapter);
        adapter_bener.notifyDataSetChanged();

    }


    @OnClick({R.id.card_lapor_saya, R.id.card_jadwal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_lapor_saya:
                Intent goInput = new Intent(getActivity(), menu_laporan_saya.class);
                getActivity().startActivity(goInput);
                CustomIntent.customType(getActivity(), "bottom-to-up");


                break;
            case R.id.card_jadwal:
                break;
        }
    }

    public void get_jumlah() {


        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_jumlah> call = api.get_jumlah();

        call.enqueue(new Callback<Response_jumlah>() {
            @Override
            public void onResponse(Call<Response_jumlah> call, Response<Response_jumlah> response) {

                try {
                    swifeRefresh.setRefreshing(false);
                    int data = response.body().getJumlah();
                    Log.i("jumlah", "onResponse: " + data);
                    txtJumlah.setText(data + " Laporan");
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Response_jumlah> call, Throwable t) {
                Log.i("cek_cek", "onResponse: " + t);
                t.printStackTrace();
            }
        });
    }


}
