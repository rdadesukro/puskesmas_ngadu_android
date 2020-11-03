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
import goid.kotajambi.puskesmas_ngadu.model.event.IsiItem_events;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_layanan;
import maes.tech.intentanim.CustomIntent;


public class adapter_event extends RecyclerView.Adapter<adapter_event.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;
    private int animation_type = 0;
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
        holder.txt_date.setText(dm.getTglMulai());
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



                }
            });
        }


    }}
