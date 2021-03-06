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

import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.github.squti.guru.Guru;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.Util.ItemAnimation;
import goid.kotajambi.puskesmas_ngadu.model.komen.Result_komen;
import goid.kotajambi.puskesmas_ngadu.model.layanan.IsiItem_layanan;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_laporan;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_layanan;
import maes.tech.intentanim.CustomIntent;


public class adapter_layanan extends RecyclerView.Adapter<adapter_layanan.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;
    private int animation_type = 0;
    public adapter_layanan(Context ctx, List<IsiItem_layanan> mList , int animation_type) {
        this.kriim = kriim;
        this.animation_type = animation_type;
        this.mList = mList;
        this.ctx = ctx;

    }

    private List<IsiItem_layanan> mList ;
    private Context ctx;

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layanan,parent, false);
            HolderData holder = new HolderData(layout);
            return holder;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        final IsiItem_layanan dm = mList.get(position);
        String date =dm.getCreatedAt().substring(8,10);
        String month= Mydate.konversi_bulan(dm.getCreatedAt().substring(5,7));
        String year =dm.getCreatedAt().substring(0,4);
        holder.txt_tgl.setText(date+"-"+month+"-"+year);
        holder.nama.setText(dm.getNama());



        holder.dm = dm;
        setAnimation(holder.itemView,position);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder {

        @BindView(R.id.txt_nama)
        TextView nama;

        @BindView(R.id.txt_tgl)
        TextView txt_tgl;

        @BindView(R.id.card)
        CardView cardView;


        IsiItem_layanan dm;

        public HolderData(View v) {
            super(v);
            ButterKnife.bind(this, itemView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, menu_detail_layanan.class);
                    Guru.putString("nama_layanan", dm.getNama());
                    Guru.putString("isi_layanan", dm.getData());
                    Guru.putString("tgl_layanan", dm.getCreatedAt());
                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "fadein-to-fadeout");


                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, menu_detail_layanan.class);
                    Guru.putString("nama_layanan", dm.getNama());
                    Guru.putString("isi_layanan", dm.getData());
                    Guru.putString("tgl_layanan", dm.getCreatedAt());
                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "fadein-to-fadeout");


                }
            });
        }


    }
    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }
}
