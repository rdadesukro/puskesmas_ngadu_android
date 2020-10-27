package goid.kotajambi.puskesmas_ngadu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.model.laporan_saya.DataItem;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_laporan;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_login;
import maes.tech.intentanim.CustomIntent;


public class adapter_laporan_saya extends PaginatedAdapter<DataItem, adapter_laporan_saya.ViewHolder> {
    public adapter_laporan_saya(Context ctx) {
        this.ctx = ctx;

    }

    private Context ctx;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_laporan_new, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.render(getItem(position));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tgl,judul;
        TextView jenis,detail,nama,komen;
        ImageView foto,foto_profi;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.txt_date);
            nama = itemView.findViewById(R.id.txt_nama);
            jenis = itemView.findViewById(R.id.txt_jenis);
            foto = itemView.findViewById(R.id.img_foto);
            detail = itemView.findViewById(R.id.txt_detail);
            judul = itemView.findViewById(R.id.txt_judul);
            foto_profi = itemView.findViewById(R.id.img_foto_frofil);
            komen = itemView.findViewById(R.id.txt_komen);
            cardView = itemView.findViewById(R.id.cardView);

        }

        public void render(DataItem user) {
            tgl.setText(user.getCreatedAt());
            jenis.setText(user.getIsiLaporan());
            judul.setText(user.getJudul());
            komen.setText(user.getJumlahKoment()+" Comment");

            nama.setBackgroundResource(R.drawable.bg_nama);

            nama.setText(user.getUser().getName());

            Glide.with(ctx)
                    .load("https://ramahpkmhandil.jambikota.go.id/uploads/profil/"+user.getUser().getFoto())
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
                    .into(foto_profi);



            if (user.getFotoLaporan()!=""){
                cardView.setVisibility(View.VISIBLE);
                Glide.with(ctx)
                        .load("https://ramahpkmhandil.jambikota.go.id/uploads/laporan/"+user.getFotoLaporan())
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
                        .into(foto);

            }else {
                cardView.setVisibility(View.GONE);
                Glide.with(ctx)
                        .load("https://ramahpkmhandil.jambikota.go.id/uploads/laporan/"+user.getFotoLaporan())
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
                        .into(foto);



            }





            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, menu_detail_laporan.class);
                    Guru.putString("judul", user.getJudul());
                    Guru.putString("isi", user.getIsiLaporan());
                    Guru.putString("foto_laporan", user.getFotoLaporan());
                    Guru.putString("tgl", user.getCreatedAt());
                    Guru.putString("tgl", user.getCreatedAt());
                    Guru.putString("foto_pelapor", user.getUser().getFoto());
                    Guru.putString("nama", user.getUser().getName());
                    Guru.putString("no_hp", String.valueOf(user.getUser().getNoHp()));
                    Guru.putString("id_lapor", String.valueOf(user.getId()));
                    Guru.putString("jumlah", String.valueOf(user.getJumlahKoment()));

                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "fadein-to-fadeout");


                }
            });


        }
        }


    }

