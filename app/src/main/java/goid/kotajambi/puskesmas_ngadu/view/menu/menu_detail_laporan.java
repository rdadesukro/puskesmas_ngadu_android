package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.squti.guru.Guru;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.Util.PhotoFullPopupWindow;
import goid.kotajambi.puskesmas_ngadu.adapter.adapter_komen;
import goid.kotajambi.puskesmas_ngadu.model.komen.Result_komen;
import goid.kotajambi.puskesmas_ngadu.model.laporan_komen.ResultItem_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.presenter.komen;
import goid.kotajambi.puskesmas_ngadu.view.view_komen;

public class menu_detail_laporan extends AppCompatActivity implements view_komen {

    @BindView(R.id.img_foto)
    ImageView imgFoto;
    @BindView(R.id.txt_isi)
    TextView txtIsi;
    @BindView(R.id.txt_tgl)
    TextView txtTgl;
    @BindView(R.id.txt_judul)
    TextView txtJudul;
    @BindView(R.id.arrowBtn)
    Button arrowBtn;
    @BindView(R.id.RecyclerView)
    RecyclerView RecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.con_berita)
    ConstraintLayout conBerita;
    @BindView(R.id.card_Data)
    CardView cardData;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.btn_send)
    FloatingActionButton btnSend;
    @BindView(R.id.txt_komen)
    TextView txtKomen;
    @BindView(R.id.text_content)
    EditText textContent;
    @BindView(R.id.btn_pgl)
    ImageView btnPgl;
    @BindView(R.id.txt_nama)
    TextView txtNama;
    @BindView(R.id.txt_no)
    TextView txtNo;
    @BindView(R.id.img_foto_pelapor)
    ImageView imgFotoPelapor;
    @BindView(R.id.card_foto)
    CardView cardFoto;
    @BindView(R.id.txt_jumlah)
    TextView txtJumlah;
    @BindView(R.id.txt_kode2)
    TextView txtKode2;


    private adapter_komen adapter_komen;
    String foto, no_hp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_laporan);
        ButterKnife.bind(this);


        txtJudul.setText(Guru.getString("judul", "false"));
        txtTgl.setText(Guru.getString("tgl", "false"));
        txtIsi.setText(Guru.getString("isi", "false"));

        txtNama.setText(Guru.getString("nama", "false"));
        txtKode2.setText(Guru.getString("kode_lp", "false"));
        no_hp = Guru.getString("no_hp", "false");
        txtJumlah.setText("( " + Guru.getString("jumlah", "false") + " Comment )");

        if (no_hp.equals("false")) {
            txtNo.setText("No Hp : -");
        } else {
            txtNo.setText("No Hp : " + no_hp);
        }


        foto = Guru.getString("foto_laporan", "false");
        Log.i("foto_laporan", "onCreate: " + foto);
        if (foto.equals("false") || foto.equals("")) {
            cardFoto.setVisibility(View.GONE);
        } else {
            cardFoto.setVisibility(View.VISIBLE);
        }

        Glide.with(this)
                .load("https://ramahpkmhandil.jambikota.go.id/uploads/laporan/" + Guru.getString("foto_laporan", "false"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgFoto);

        Glide.with(this)
                .load("https://ramahpkmhandil.jambikota.go.id/uploads/profil/" + Guru.getString("foto_pelapor", "false"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .error(R.drawable.man)
                .into(imgFotoPelapor);


    }

    @OnClick(R.id.arrowBtn)
    public void onViewClicked() {
        if (conBerita.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardData, new AutoTransition());
            conBerita.setVisibility(View.VISIBLE);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
            komen countryPresenter = new komen(this, menu_detail_laporan.this);
            countryPresenter.get_komen(Guru.getString("id_lapor", "false"));

        } else {
            TransitionManager.beginDelayedTransition(cardData, new AutoTransition());
            conBerita.setVisibility(View.GONE);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
        }
    }

    @Override
    public void komen(List<Result_komen> kom) {
        Log.i("isi_komen", "komen: " + kom.size());
        for (int i = 0; i < kom.size(); i++) {
            Log.i("id_user", "komen: " + kom.get(i).getUsersId());
        }


        adapter_komen = new adapter_komen(this, kom, 1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.setAdapter(adapter_komen);
        if (kom.size() == 0) {
            txtKomen.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            txtKomen.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void laporan_komen(List<ResultItem_laporan_komen> laporan_komen) {

    }


    @OnClick(R.id.btn_send)
    public void btn_send() {

        komen countryPresenter = new komen(this, menu_detail_laporan.this);
        countryPresenter.simpan_komen(Guru.getString("id_lapor", "false"), Guru.getString("id_user", "false"), textContent.getText().toString());
        textContent.setText("");


    }

    @OnClick(R.id.btn_pgl)
    public void btn_pgl() {
        if (no_hp.equals("false")) {
            Toast.makeText(this, "No Hp Tidak Ada", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + no_hp));
            startActivity(intent);
        }
    }

    @OnClick({R.id.img_foto_pelapor, R.id.img_foto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_foto_pelapor:
                new PhotoFullPopupWindow(this, R.layout.popup_photo_full, imgFotoPelapor, "https://ramahpkmhandil.jambikota.go.id/uploads/profil/" + Guru.getString("foto_pelapor", "false"), null);


                break;
            case R.id.img_foto:
                new PhotoFullPopupWindow(this, R.layout.popup_photo_full, imgFotoPelapor, "https://ramahpkmhandil.jambikota.go.id/uploads/laporan/" + Guru.getString("foto_laporan", "false"), null);

                break;
        }
    }
}