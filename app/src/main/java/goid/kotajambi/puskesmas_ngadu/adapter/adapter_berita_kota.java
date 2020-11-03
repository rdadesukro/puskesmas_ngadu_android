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
import android.webkit.WebView;
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
import goid.kotajambi.puskesmas_ngadu.model.model_berita.PostsItem;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_berita;


public class adapter_berita_kota extends RecyclerView.Adapter<adapter_berita_kota.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;
    private int animation_type = 0;
    public adapter_berita_kota(Context ctx, List<PostsItem> mList , int animation_type) {
        this.kriim = kriim;
        this.animation_type = animation_type;
        this.mList = mList;
        this.ctx = ctx;

    }

    private List<PostsItem> mList ;
    private Context ctx;

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_berita,parent, false);
            HolderData holder = new HolderData(layout);
            return holder;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        //  Toast.makeText(ctx, waktu, Toast.LENGTH_SHORT);
        final PostsItem dm = mList.get(position);
        String waktu;
      //  holder.judul.setText(dm.getUrl());
//        holder.judul.requestFocus();
//        holder.judul.getSettings().setLightTouchEnabled(true);
//        holder.judul.getSettings().setJavaScriptEnabled(true);
//
//        holder.judul.loadDataWithBaseURL("", dm.getTitle(), "text/html", "UTF-8", "");

        holder.judul.setText(dm.getTitle());


        String date =dm.getDate().substring(8,10);
        String month= Mydate.konversi_bulan(dm.getDate().substring(5,7));
        String year =dm.getDate().substring(0,4);
        holder.tanggal.setText(date+"-"+month+"-"+year);
        if (!dm.getAttachments().isEmpty()){
            Glide.with(ctx)
                    .load(dm.getAttachments().get(0).getImages().getMedium().getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.img_berita);

        }else {
            Glide.with(ctx)
                    .load("https://sisamsul.jambikota.go.id/AndroFile/image/tugukeris.jpg")
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.img_berita);

            Toast.makeText(ctx, "kirim"+ kriim, Toast.LENGTH_SHORT);

        }

        holder.dm = dm;



    }

    @Override
    public int getItemCount() {

        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder{

        @BindView(R.id.txt_judul)
        TextView judul;

        @BindView(R.id.txt_tgl)
        TextView tanggal;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.img_berita)
        ImageView img_berita ;


        PostsItem dm;




        public HolderData (View v)
        {
            super(v);
            ButterKnife.bind(this, itemView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent goInput = new Intent(ctx, menu_detail_berita.class);
                    goInput.putExtra("judul", dm.getTitle());
                    goInput.putExtra("isi", dm.getContent());
                    goInput.putExtra("tgl", dm.getDate());
                    goInput.putExtra("mas","mas");
                    goInput.putExtra("url", dm.getUrl());
                    if (!dm.getAttachments().isEmpty()){
                        goInput.putExtra("foto", dm.getAttachments().get(0).getUrl());
                    }else {
                        goInput.putExtra("foto", "https://sisamsul.jambikota.go.id/AndroFile/image/logo_pemkot_bg.png");
                    }
                    ctx.startActivity(goInput);

                }
            });


        }

        public void detail_profil(){
//            Intent i=new Intent(ctx, menu_detail_profil.class);
//           // i.putExtra("isi",dm.getIsi());
//            ctx.startActivity(i);
        }

        public void click_desc_data(){
//            Intent goInput = new Intent(ctx, tes_tracking.class);
//            goInput.putExtra("status", dm.getStatus());
//            goInput.putExtra("produk", dm.getProduk());
//            goInput.putExtra("nama", dm.getNama());
//            goInput.putExtra("tgl_reg", dm.getTgl_reg());
//            ctx.startActivity(goInput);
        }

        public void click_desc_data_Saya(){

//
//            Intent intent = new Intent(ctx, menu_detail_laporan_pelayanan.class);
//            Bundle b = new Bundle();
//
//            //Menyisipkan tipe data String ke dalam obyek bundle
//            b.putString("id_registrasi", dm.getIdRegis());
//            b.putString("status", dm.getStatus());
//            b.putString("nama", dm.getNama());
//            // b.putString("vidio", txtvidio.getText().toString());
//            intent.putExtras(b);
//            ctx.startActivity(intent);
        }


    }

    private int lastPosition = -1;
    private boolean on_attach = true;

//    private void setAnimation(View view, int position) {
//        if (position > lastPosition) {
//            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
//            lastPosition = position;
//        }
//    }

}
