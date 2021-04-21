package goid.kotajambi.puskesmas_ngadu.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.model.notif.IsiItem_notif;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_layanan;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_notif;
import maes.tech.intentanim.CustomIntent;


public class adapter_notif extends RecyclerView.Adapter<adapter_notif.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;
    private List<String> array_syarat = new ArrayList<>();
    String sts,tgl;
    private int animation_type = 0;
    public adapter_notif(Context ctx, List<IsiItem_notif> mList , int animation_type) {
        this.kriim = kriim;
        this.animation_type = animation_type;
        this.mList = mList;
        this.ctx = ctx;

    }

    private List<IsiItem_notif> mList ;
    private Context ctx;


    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_notif,parent, false);
            HolderData holder = new HolderData(layout);
            return holder;
    }


    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        final IsiItem_notif dm = mList.get(position);
        String date =dm.getCreatedAt().substring(8,10);
        String month= Mydate.konversi_bulan(dm.getCreatedAt().substring(5,7));
        String year =dm.getCreatedAt().substring(0,4);
        tgl = date+"-"+month+"-"+year;
        holder.tgl.setText(dm.getWaktu());



        holder.judul.setText(dm.getTitle());



       // holder.status.setText(dm.getUserStatusRead());
        array_syarat.add(dm.getUserStatusRead());
        sts = dm.getUserStatusRead();


        boolean cek = sts.contains(Guru.getString("id_user", "false"));
        Log.i("isi_data_status", "onBindViewHolder: "+sts+" "+Guru.getString("id_user", "false"));
        Log.i("data_cek", "onBindViewHolder: "+cek);
        if (cek){
            holder.status.setText("");


        }else {
            holder.status.setBackgroundResource(R.drawable.bg_nama);
            holder.status.setText("News");
        }





        holder.dm = dm;



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder {

        @BindView(R.id.txt_judul)
        TextView judul;

        @BindView(R.id.txt_tgl)
        TextView tgl;

        @BindView(R.id.txt_sts)
        TextView status;

        @BindView(R.id.card)
        CardView cardView;


        IsiItem_notif dm;


        public HolderData(View v) {
            super(v);
            ButterKnife.bind(this, itemView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent goInput = new Intent(ctx, menu_detail_notif.class);
                    Guru.putString("judul_notif", dm.getTitle());
                    Guru.putString("body_notif", dm.getBody());
                    Guru.putString("id_notif", String.valueOf(dm.getId()));
                    Guru.putString("konten_notif", dm.getKonten());
                    Guru.putString("status_notif", dm.getUserStatusRead());
                    Guru.putString("tgl_notif", dm.getCreatedAt());
                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "bottom-to-up");
                }
            });


        }


    }
    private static void check(int[] arr, int toCheckValue)
    {
        boolean test = false;
        for (int element : arr) {
            if (element == toCheckValue) {
                test = true;
                break;
            }
        }

        // Print the result
        System.out.println("Is " + toCheckValue
                + " present in the array: " + test);
    }

}
