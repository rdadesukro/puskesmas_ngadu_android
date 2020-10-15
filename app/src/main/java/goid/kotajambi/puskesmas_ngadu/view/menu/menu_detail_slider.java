package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.squti.guru.Guru;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;

public class menu_detail_slider extends AppCompatActivity {

    @BindView(R.id.txt_isi)
    TextView txtIsi;
    @BindView(R.id.img_slider)
    ImageView imgSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_slider);
        ButterKnife.bind(this);

        txtIsi.setText(Guru.getString("isi", "false"));
        this.setTitle(Guru.getString("judul", "false"));
        Glide.with(this)
                .load("http://192.168.1.71/puskesmas_ngadu/public/uploads/sliders/" + Guru.getString("foto", "false"))
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
                .into(imgSlider);
    }
}