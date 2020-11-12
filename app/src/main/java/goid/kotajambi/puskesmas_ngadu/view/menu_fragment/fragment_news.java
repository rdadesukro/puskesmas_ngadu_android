package goid.kotajambi.puskesmas_ngadu.view.menu_fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.squti.guru.Guru;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_event;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_notif;
import goid.kotajambi.puskesmas_ngadu.model.notif.IsiItem_notif;
import goid.kotajambi.puskesmas_ngadu.presenter.event;
import goid.kotajambi.puskesmas_ngadu.presenter.notif;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_event;
import goid.kotajambi.puskesmas_ngadu.view.view_notif;

import static goid.kotajambi.puskesmas_ngadu.view.menu.menu_utama.badge;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_news extends Fragment implements view_notif {


    @BindView(R.id.img_data2)
    ImageView imgData2;
    @BindView(R.id.txt_data2)
    TextView txtData2;
    @BindView(R.id.rv_aku)
    RecyclerView rvAku;
    @BindView(R.id.swifeRefresh)
    SwipeRefreshLayout swifeRefresh;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    notif countryPresenter;
    int jumlah_notif=0;
    String id_status_notif;
    Boolean cek;
    private adapter_notif adapter_notif;
    public fragment_news() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_data, container, false);
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

         countryPresenter = new notif(this, getActivity());
        countryPresenter.get_notif();
        swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                countryPresenter.get_notif();

            }
        });
        return view;


    }

    @Override
    public void notif(List<IsiItem_notif> notif) {
        Log.i("isi_event", "event: "+notif.size());
        adapter_notif = new adapter_notif(getActivity(), notif, 1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvAku.setLayoutManager(layoutManager);
        rvAku.setAdapter(adapter_notif);
        swifeRefresh.setRefreshing(false);
        if (notif.size() == 0) {
            txtData2.setVisibility(View.VISIBLE);
            imgData2.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            txtData2.setVisibility(View.GONE);
            imgData2.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        }
        jumlah_notif=0;
        for (int i = 0; i < notif.size(); i++) {
            id_status_notif = notif.get(i).getUserStatusRead();
            cek = id_status_notif.contains(Guru.getString("id_user", "false"));
            if (cek){

            }else {
                jumlah_notif=jumlah_notif+1;

            }

        }
        badge.setNumber(jumlah_notif);
        Log.i("jumlah_notif_baru", "notif: "+jumlah_notif);
    }
    @Override
    public void onStart() {
        super.onStart();
        countryPresenter.get_notif();
    }

    @Override
    public void onResume() {
        super.onResume();
        countryPresenter.get_notif();
    }

}
