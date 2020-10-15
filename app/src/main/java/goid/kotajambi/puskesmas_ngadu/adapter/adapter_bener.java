package goid.kotajambi.puskesmas_ngadu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.squti.guru.Guru;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.model.slider.IsiItem_slider;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_detail_slider;
import maes.tech.intentanim.CustomIntent;


public class adapter_bener extends SliderViewAdapter<adapter_bener.SliderAdapterVH> {

        private Context context;
        private String jenis;
        private List<IsiItem_slider> mSliderItems = new ArrayList<>();

    public adapter_bener(Context context, List<IsiItem_slider> mList, String jenis) {
        this.mSliderItems = mList;
        this.jenis = jenis;
        this.context = context;
    }

        public void renewItems (List<IsiItem_slider> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

        public void deleteItem ( int position){
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

        public void addItem (IsiItem_slider sliderItem){
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

        @Override
        public adapter_bener.SliderAdapterVH onCreateViewHolder (ViewGroup parent){
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new adapter_bener.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        IsiItem_slider sliderItem = mSliderItems.get(position);


        Log.i("cek_gambar", "onBindViewHolder: "+sliderItem.getImage());
        viewHolder.textViewDescription.setText(sliderItem.getJudul());

        Glide.with(viewHolder.itemView)
                .load("http://192.168.1.71/puskesmas_ngadu/public/uploads/sliders/"+sliderItem.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(viewHolder.imageViewBackground);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+sliderItem.getId(), Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(context, menu_detail_slider.class);
                Guru.putString("judul", sliderItem.getJudul());
                Guru.putString("isi", sliderItem.getKonten());
                Guru.putString("foto", sliderItem.getImage());
                Guru.putString("tgl", sliderItem.getCreatedAt());
                context.startActivity(goInput);
                CustomIntent.customType(context, "fadein-to-fadeout");

            }
        });


    }



        @Override
        public int getCount () {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

        class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

            View itemView;
            ImageView imageViewBackground;
            ImageView imageGifContainer;
            TextView textViewDescription;
            ProgressBar progressBar;

            public SliderAdapterVH(View itemView) {
                super(itemView);
                try {
                    textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
                } catch (Exception e) {

                    Log.i("cek_webview2", "SliderAdapterVH: " + e);

                }
                imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
                progressBar = itemView.findViewById(R.id.progressBar);
                imageGifContainer = itemView.findViewById(R.id.iv_gif_container);

                this.itemView = itemView;
            }
        }



}
