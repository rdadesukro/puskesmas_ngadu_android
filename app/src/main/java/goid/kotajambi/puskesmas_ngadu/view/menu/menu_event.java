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
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_event;
import goid.kotajambi.puskesmas_ngadu.model.event.IsiItem_events;
import goid.kotajambi.puskesmas_ngadu.presenter.event;
import goid.kotajambi.puskesmas_ngadu.view.view_event;

public class menu_event extends AppCompatActivity implements view_event {

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
    private adapter_event adapter_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_event);
        ButterKnife.bind(this);
        event countryPresenter = new event(this, menu_event.this);
        countryPresenter.get_event();
    }

    @Override
    public void event(List<IsiItem_events> events) {
        Log.i("isi_event", "event: "+events.size());
        adapter_event = new adapter_event(this, events, 1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAku.setLayoutManager(layoutManager);
        rvAku.setAdapter(adapter_event);
        if (events.size() == 0) {
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