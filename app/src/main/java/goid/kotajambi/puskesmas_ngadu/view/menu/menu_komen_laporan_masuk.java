package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.squti.guru.Guru;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.model.komen.Result_komen;
import goid.kotajambi.puskesmas_ngadu.model.laporan_komen.ResultItem_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.presenter.komen;
import goid.kotajambi.puskesmas_ngadu.view.view_komen;

public class menu_komen_laporan_masuk extends AppCompatActivity implements view_komen {

    @BindView(R.id.img_data)
    ImageView imgData;
    @BindView(R.id.txt_data)
    TextView txtData;
    @BindView(R.id.rv_aku)
    RecyclerView rvAku;
    @BindView(R.id.swifeRefresh)
    SwipeRefreshLayout swifeRefresh;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    adapter_laporan_komen adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_komen_laporan_masuk);
        ButterKnife.bind(this);
        komen countryPresenter = new komen(this, menu_komen_laporan_masuk.this);
        countryPresenter.get_laporan_komen(Guru.getString("id_lapor_komen", "false"));
        Log.i("id_lapor_komen", "onCreate: "+Guru.getString("id_lapor_komen", "false"));


    }

    @Override
    public void komen(List<Result_komen> bener) {

    }

    @Override
    public void jumlah(String jumlah) {

    }

    @Override
    public void status(String status) {

    }

    @Override
    public void laporan_komen(List<ResultItem_laporan_komen> laporan_komen) {
        Log.i("laporan_komen", "komen: " + laporan_komen.size());
        adapter = new adapter_laporan_komen(this,laporan_komen,1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAku.setLayoutManager(layoutManager);
        rvAku.setAdapter(adapter);
        if (laporan_komen.size() == 0) {
            txtData.setVisibility(View.VISIBLE);
            imgData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            txtData.setVisibility(View.GONE);
            imgData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(new Intent(menu_komen_laporan_masuk.this, menu_utama.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}