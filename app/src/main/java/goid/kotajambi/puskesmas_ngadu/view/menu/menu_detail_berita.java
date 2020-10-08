package goid.kotajambi.puskesmas_ngadu.view.menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.adapter.Mydate;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_utama;

public class menu_detail_berita extends AppCompatActivity {

    String judul,isi,tgl,foto;

    @BindView(R.id.txt_judul)
    WebView txt_judul;
    @BindView(R.id.web_view)
    WebView web_view;

    @BindView(R.id.txt_tgl)
    TextView txt_tgl;

    @BindView(R.id.txt_isi)
    TextView txt_isi;

    @BindView(R.id.img_berita)
    ImageView img_berita;



    @BindView(R.id.progressBar5)
    ProgressBar progressBar;
    String url;
    String jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_berita);
        ButterKnife.bind(this);


        Bundle b = getIntent().getExtras();
        judul= (String) b.getCharSequence("judul");
        isi= (String) b.getCharSequence("isi");
        tgl= (String) b.getCharSequence("tgl");
        jenis= (String) b.getCharSequence("mas");
        foto= (String) b.getCharSequence("foto");
        url= (String) b.getCharSequence("url");

        if (foto.equals("")){


            Glide.with(menu_detail_berita.this)
                    .load("https://sisamsul.jambikota.go.id/AndroFile/image/logo_pemkot_bg.png")
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(img_berita);
        }else {



            Glide.with(menu_detail_berita.this)
                    .load(foto)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(img_berita);

        }



        String date =tgl.substring(8,10);
        String month= Mydate.konversi_bulan(tgl.substring(5,7));
        String year =tgl.substring(0,4);
        //holder.txt_date.setText(date+"-"+month+"-"+year);
        txt_tgl.setText(date+"-"+month+"-"+year);
        ///  txt_judul.setText(judul);
        // txt_judul.loadDataWithBaseURL("", judul, "text/html", "UTF-8", "");
        txt_judul.loadDataWithBaseURL("", judul, "text/html", "UTF-8", "");
        final WebSettings webSettings = txt_judul.getSettings();
        Resources res = getResources();
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        webSettings.setDefaultFontSize(12);
        web_view.requestFocus();
        web_view.getSettings().setLightTouchEnabled(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.loadDataWithBaseURL("", isi, "text/html", "UTF-8", "");

    }
//    @OnClick(R.id.btn_share)
//    void btn_share() {
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Android Development Tutorials");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
//        shareIntent.setType("text/plain");
//        startActivity(shareIntent);
//
//


    private ShareActionProvider mShareActionProvider;
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_share, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(item);

//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Android Development Tutorials");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
//        shareIntent.setType("text/plain");
//        startActivity(shareIntent);


        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "here goes your share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Subject");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        //then set the sharingIntent
        mShareActionProvider.setShareIntent(sharingIntent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_share) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


