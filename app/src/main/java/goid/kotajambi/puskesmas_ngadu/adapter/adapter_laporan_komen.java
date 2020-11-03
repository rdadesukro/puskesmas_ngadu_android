package goid.kotajambi.puskesmas_ngadu.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.squti.guru.Guru;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;

import goid.kotajambi.puskesmas_ngadu.model.laporan_komen.ResultItem_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_laporan;
import maes.tech.intentanim.CustomIntent;


public class adapter_laporan_komen extends RecyclerView.Adapter<adapter_laporan_komen.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;
    private int animation_type = 0;
    public adapter_laporan_komen(Context ctx, List<ResultItem_laporan_komen> mList , int animation_type) {
        this.kriim = kriim;
        this.animation_type = animation_type;
        this.mList = mList;
        this.ctx = ctx;

    }

    private List<ResultItem_laporan_komen> mList ;
    private Context ctx;

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_laporan_new,parent, false);
            HolderData holder = new HolderData(layout);
            return holder;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        final ResultItem_laporan_komen dm = mList.get(position);

        holder.nama.setText(dm.getUser().getName());
        holder.txt_date.setText(dm.getCreatedAt());
        holder.txt_jenis.setText(dm.getIsiLaporan());
        holder.txt_judul.setText(dm.getJudul());
        holder.txt_komen.setText(""+dm.getJumlahKoment());

        Glide.with(ctx)
                .load("https://ramahpkmhandil.jambikota.go.id/uploads/profil/"+dm.getUser().getFoto())
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
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.img_foto_frofil);



        if (dm.getFotoLaporan()!=null){
            holder.cardView.setVisibility(View.VISIBLE);
            Glide.with(ctx)
                    .load("https://ramahpkmhandil.jambikota.go.id/uploads/laporan/"+dm.getFotoLaporan())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.ProgressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.ProgressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(holder.img_foto);

        }else {
           holder.cardView.setVisibility(View.GONE);


        }
        holder.dm = dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder {



        @BindView(R.id.txt_nama)
        TextView nama;

        @BindView(R.id.txt_date)
        TextView txt_date;

        @BindView(R.id.txt_jenis)
        TextView txt_jenis;

        @BindView(R.id.progressBar)
        ProgressBar ProgressBar;

        @BindView(R.id.txt_detail)
        TextView txt_detail;

        @BindView(R.id.txt_judul)
        TextView txt_judul;

        @BindView(R.id.txt_komen)
        TextView txt_komen;

        @BindView(R.id.img_foto)
        ImageView img_foto;

        @BindView(R.id.img_foto_frofil)
        ImageView img_foto_frofil;

        @BindView(R.id.cardView)
        CardView cardView;


        ResultItem_laporan_komen dm;

        public HolderData(View v) {
            super(v);
            ButterKnife.bind(this, itemView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });

            txt_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, menu_detail_laporan.class);
                    Guru.putString("judul", dm.getJudul());
                    Guru.putString("isi", dm.getIsiLaporan());
                    Guru.putString("foto_laporan", dm.getFotoLaporan());
                    Guru.putString("tgl", dm.getCreatedAt());
                    Guru.putString("tgl", dm.getCreatedAt());
                    Guru.putString("foto_pelapor", dm.getUser().getFoto());
                    Guru.putString("nama", dm.getUser().getName());
                    Guru.putString("no_hp", String.valueOf(dm.getUser().getNoHp()));
                    Guru.putString("id_lapor", String.valueOf(dm.getId()));
                    Guru.putString("jumlah", String.valueOf(dm.getJumlahKoment()));

                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "fadein-to-fadeout");



                }
            });
        }


    }}
