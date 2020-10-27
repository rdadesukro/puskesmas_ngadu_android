package goid.kotajambi.puskesmas_ngadu.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.model.komen.Result_komen;


public class adapter_komen extends RecyclerView.Adapter<adapter_komen.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;
    private int animation_type = 0;
    public adapter_komen(Context ctx, List<Result_komen> mList , int animation_type) {
        this.kriim = kriim;
        this.animation_type = animation_type;
        this.mList = mList;
        this.ctx = ctx;

    }

    private List<Result_komen> mList ;
    private Context ctx;

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_koment,parent, false);
            HolderData holder = new HolderData(layout);
            return holder;
    }


    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        final Result_komen dm = mList.get(position);
        String date =dm.getCreatedAt().substring(8,10);
        String month= Mydate.konversi_bulan(dm.getCreatedAt().substring(5,7));
        String year =dm.getCreatedAt().substring(0,4);
        holder.tanggal.setText(date+"-"+month+"-"+year);

        if (dm.getName().equals("admin")){
            holder.nama.setBackgroundResource(R.drawable.bg_admin);
        }
        holder.nama.setText(dm.getName());
        holder.isi.setText(dm.getKonten());

            Glide.with(ctx)
                    .load("https://ramahpkmhandil.jambikota.go.id/uploads/uploads/profil/"+dm.getFoto())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                           // holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                           // holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.man)
                    .into(holder.img_foto);



        holder.dm = dm;



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder {

        @BindView(R.id.txt_nama)
        TextView nama;

        @BindView(R.id.txt_isi)
        TextView isi;

        @BindView(R.id.txt_tgl)
        TextView tanggal;

        @BindView(R.id.img_foto)
        ImageView img_foto;


        Result_komen dm;


        public HolderData(View v) {
            super(v);
            ButterKnife.bind(this, itemView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


        }


    }}
