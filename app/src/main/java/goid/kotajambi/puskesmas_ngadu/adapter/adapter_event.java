package goid.kotajambi.puskesmas_ngadu.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import goid.kotajambi.puskesmas_ngadu.model.event.IsiItem_events;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_events;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_layanan;
import maes.tech.intentanim.CustomIntent;


public class adapter_event extends RecyclerView.Adapter<adapter_event.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;
    String lat_new,lng_new;
    String lat,lng;
    private int animation_type = 0;
    GeocodingLocation locationAddress = new GeocodingLocation();
    public adapter_event(Context ctx, List<IsiItem_events> mList , int animation_type) {
        this.kriim = kriim;
        this.animation_type = animation_type;
        this.mList = mList;
        this.ctx = ctx;

    }

    private List<IsiItem_events> mList ;
    private Context ctx;

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_event,parent, false);
            HolderData holder = new HolderData(layout);
            return holder;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        final IsiItem_events dm = mList.get(position);

        holder.txt_judul.setText(dm.getNama());
        String date =dm.getCreatedAt().substring(8,10);
        String month= Mydate.konversi_bulan(dm.getCreatedAt().substring(5,7));
        String year =dm.getCreatedAt().substring(0,4);
        holder.txt_date.setText(date+"-"+month+"-"+year);
        Glide.with(ctx)
                .load("https://ramahpkmhandil.jambikota.go.id/uploads/events/"+dm.getThumbnail())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.img_foto);

        holder.dm = dm;



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder {

        @BindView(R.id.txt_judul)
        TextView txt_judul;

        @BindView(R.id.txt_date)
        TextView txt_date;

        @BindView(R.id.img_foto)
        ImageView img_foto;

        @BindView(R.id.txt_status)
        TextView txt_status;

        @BindView(R.id.progressBar)
        ProgressBar progress;

        @BindView(R.id.card)
        CardView cardView;


        IsiItem_events dm;

        public HolderData(View v) {
            super(v);
            ButterKnife.bind(this, itemView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, menu_detail_layanan.class);
                    Guru.putString("jd_event", dm.getNama());
                    Guru.putString("isi_event", dm.getKonten());
                    Guru.putString("tgl_mulai", dm.getTglMulai());
                    Guru.putString("tgl_akhir", dm.getTglAkhir());
                    Guru.putString("lokasi", dm.getLokasi());
                    Guru.putString("waktu_event", dm.getWaktu());
                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "fadein-to-fadeout");


                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    locationAddress.getAddressFromLocation(dm.getLokasi(),
                            ctx, new GeocoderHandler());

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent goInput = new Intent(ctx, menu_detail_events.class);
                            Guru.putString("jd_event", dm.getNama());
                            Guru.putString("isi_event", dm.getKonten());
                            Guru.putString("tgl_mulai", dm.getTglMulai());
                            Guru.putString("tgl_akhir", dm.getTglAkhir());
                            Guru.putString("lat", lat);
                            Guru.putString("lng", lng);
                            Guru.putString("foto_event", dm.getThumbnail());
                            Guru.putString("lokasi", dm.getLokasi());
                    Guru.putString("waktu_event", dm.getWaktu());
                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "fadein-to-fadeout");


                        } }, 2000L); //3000 L = 3 detik

                    Log.i("isiisisisi", "onClick: "+lat+" "+lng);
//

                }
            });
        }


    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    lat =bundle.getString("lat");
                    lng = bundle.getString("lng");
                    Log.i("isi2", "handleMessage: "+lat+" "+lng);
                    break;
                default:
                    lat = "-1.642717";
                    lng= "103.573723";
            }

            lat_new = String.valueOf(lat);
            lng_new = String.valueOf(lng);

        }
    }
}
