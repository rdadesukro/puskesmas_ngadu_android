package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_layanan;
import goid.kotajambi.puskesmas_ngadu.model.layanan.IsiItem_layanan;
import goid.kotajambi.puskesmas_ngadu.presenter.layanan;
import goid.kotajambi.puskesmas_ngadu.view.view_layanan;

public class menu_layanan extends AppCompatActivity implements view_layanan {

    @BindView(R.id.img_data2)
    ImageView imgData2;
    @BindView(R.id.txt_data2)
    TextView txtData2;
    @BindView(R.id.rv_aku)
    RecyclerView rvAku;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swifeRefresh)
    SwipeRefreshLayout swifeRefresh;
    private adapter_layanan adapter_layanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layanan);
        ButterKnife.bind(this);
        layanan countryPresenter = new layanan(this, menu_layanan.this);
        countryPresenter.get_layanan();
        swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                countryPresenter.get_layanan();

            }
        });
    }


    @Override
    public void layanan(List<IsiItem_layanan> layanan) {
        Log.i("isi_komen", "komen: " + layanan.size());

        adapter_layanan = new adapter_layanan(this, layanan, 1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAku.setLayoutManager(layoutManager);
        rvAku.setAdapter(adapter_layanan);
        swifeRefresh.setRefreshing(false);
        if (layanan.size() == 0) {
            txtData2.setVisibility(View.VISIBLE);
            imgData2.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            txtData2.setVisibility(View.GONE);
            imgData2.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        }
    }
}