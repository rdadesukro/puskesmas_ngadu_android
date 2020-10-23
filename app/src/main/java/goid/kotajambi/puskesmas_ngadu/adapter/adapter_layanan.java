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

        holder.nama.setText(dm.getNama());


        holder.dm = dm;



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder {

        @BindView(R.id.txt_nama)
        TextView nama;

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
                    Guru.putString("nama", dm.getNama());
                    Guru.putString("isi", dm.getData());
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
                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "fadein-to-fadeout");


                }
            });
        }


    }}
