package goid.kotajambi.puskesmas_ngadu.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;


import com.github.squti.guru.Guru;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_api_berita;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.model.jumlah_laporan_saya.Response_jumlah;
import goid.kotajambi.puskesmas_ngadu.model.laporan_saya.Response_laporan_saya;
import goid.kotajambi.puskesmas_ngadu.model.laporan_saya.Result_laporan_saya;
import goid.kotajambi.puskesmas_ngadu.model.model_berita.Response_berita;
import goid.kotajambi.puskesmas_ngadu.model.simpan.Response_simpan;
import goid.kotajambi.puskesmas_ngadu.model.slider.Response_slider;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_riset_password;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_utama;
import goid.kotajambi.puskesmas_ngadu.view.view_home;
import maes.tech.intentanim.CustomIntent;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static goid.kotajambi.puskesmas_ngadu.view.menu.menu_lapor.file;
import static java.util.Collections.singletonList;

public class home {

    private Context ctx;
    private view_home countryView;
    private Retroserver_server_AUTH countryService;
    public home(view_home view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }


    public void get_lporan_saya() {
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_laporan_saya> call = api.get_laporan_saya();
        call.enqueue(new Callback<Response_laporan_saya>() {
            @Override
            public void onResponse(Call<Response_laporan_saya> call, Response<Response_laporan_saya> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_laporan_saya data = response.body();
                        Toast.makeText(ctx, ""+ response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("isi_data", "onResponse: "+data);
                        if (data != null && data.getResult() != null) {
                            List<Result_laporan_saya> result = singletonList(data.getResult());
                           // countryView.countriesReady(result);
                        }
                    }
                } catch (Exception e) {
                    Log.e("onResponse", "There is an error" + e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Response_laporan_saya> call, Throwable t) {
                t.printStackTrace();
                Log.i("cek_error", "onFailure: " + t);
                if (t instanceof IOException) {


                } else {


                }
            }
        });
    }

    public  void  lapor(RequestBody id_user, RequestBody kode, RequestBody judul, RequestBody isi, MultipartBody.Part foto, ProgressDialog pDialog ){
        pDialog = new ProgressDialog(ctx);
        pDialog.setTitle("Mohon Tunggu!!!");
        pDialog.setMessage("Simpan Laporan...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ProgressDialog finalPDialog = pDialog;
//        pDialog = new SweetAlertDialog((Activity) ctx, SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3395ff"));
//        pDialog.setTitleText("Simpan Laporan");
//        pDialog.setContentText("Mohon tunggu sedang memproses...");
//        pDialog.setCancelable(false);
//        pDialog.show();
//        SweetAlertDialog finalPDialog = pDialog;
        Log.i("isi_data", "lapor: "+id_user+" "+kode + " "+judul+" "+isi+" "+foto);
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);

        Call<Response_simpan> sendbio = api.simpan_laporan(id_user,kode,isi,judul,foto);


        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    file=null;
                   // Toast.makeText(ctx, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   finalPDialog.dismiss();
                    dialog_berhasil("Berhasil Lapor","");
                    //new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

                } else {
                   // Toast.makeText(ctx, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finalPDialog.dismiss();
                    gagal(response.body().getMessage());
                    //new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                }

            }
            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {
                Log.i("cek_error", "onFailure: "+t);

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }

    public  void berita(){
        ApiRequest api = Retroserver_api_berita.getClient().create(ApiRequest.class);
        Call<Response_berita> call = api.get_berita();
        call.enqueue(new Callback<Response_berita>() {
            @Override
            public void onResponse(Call<Response_berita> call, Response<Response_berita> response) {

                try {
                    if (response.isSuccessful()) {
                        Response_berita data = response.body();
                        Log.i("isi_data", "onResponse: "+data.getPosts());
                        Log.i("data_size", "onResponse: "+data.getPosts().size());
                        countryView.berita(data.getPosts());
//                        if (data != null && response.body().getPosts() != null) {
//                            List<PostsItem> result = data.getPosts();
//                            countryView.berita(result);
//                        }
                    }
                } catch (Exception e) {
                    Log.e("onResponse", "There is an error" + e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Response_berita> call, Throwable t) {
                t.printStackTrace();
                Log.i("cek_error", "onFailure: " + t);
                if (t instanceof IOException) {


                } else {


                }
            }
        });

    }
    public void baner(){
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_slider> call = api.get_slider();
        call.enqueue(new Callback<Response_slider>() {
            @Override
            public void onResponse(Call<Response_slider> call, Response<Response_slider> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_slider data = response.body();
                        Log.i("isi_slider", "onResponse: "+data.getIsi());
                        Log.i("data_size", "onResponse: "+data.getIsi().size());
                        countryView.bener(data.getIsi());
//                        if (data != null && response.body().getPosts() != null) {
//                            List<PostsItem> result = data.getPosts();
//                            countryView.berita(result);
//                        }


                    } else {
                        // error case
                        switch (response.code()) {
                            case 404:
                                Toast.makeText(ctx, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(ctx,"server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(ctx, "unknown error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                } catch (Exception e) {
                    Log.e("onResponse", "There is an error" + e);
                    //data();
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<Response_slider> call, Throwable t) {
                t.printStackTrace();
                //  sliderView_bener.setBackgroundResource(R.drawable.bg_no_item_city);
                Log.i("ewkwkwkwkw", "onFailure: " + t);
                if (t instanceof IOException) {

                } else {

                }

            }
        });

    }
    public void get_jumlah(String jumlah) {



        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_jumlah> call = api.get_jumlah();

        call.enqueue(new Callback<Response_jumlah>() {
            @Override
            public void onResponse(Call<Response_jumlah> call, Response<Response_jumlah> response) {

                try {
                    int data = response.body().getJumlah();
                    Log.i("jumlah", "onResponse: "+data);
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Response_jumlah> call, Throwable t) {
                Log.i("cek_cek", "onResponse: "+t);
                t.printStackTrace();
            }
        });
    }
    void dialog_berhasil(String judul,String pesan) {
        SweetAlertDialog pDialog = new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setCancelable(false);
        pDialog.setTitleText(judul);
        pDialog.setConfirmText("Ok");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                CustomIntent.customType(ctx, "fadein-to-fadeout");
                Intent intent = new Intent((Activity) ctx, menu_utama.class);
                Guru.putString("Fragmentone", "0");
                intent.putExtra("Fragmentone", 0); //pass zero for Fragmentone.
                ctx.startActivity(intent);
            }
        });
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

    }

    void  sukses(){
        SweetAlertDialog  pDialog = new SweetAlertDialog((Activity) ctx, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3395ff"));
        pDialog.setTitleText("Berhasil Lapor");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    void  gagal(String pesan){
        SweetAlertDialog  pDialog = new SweetAlertDialog((Activity) ctx, SweetAlertDialog.WARNING_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3395ff"));
        pDialog.setTitleText(pesan);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public  void  edit_foto(MultipartBody.Part foto, ProgressDialog pDialog ){
        pDialog = new ProgressDialog(ctx);
        pDialog.setTitle("Mohon Tunggu!!!");
        pDialog.setMessage("Edit Foto Profil");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ProgressDialog finalPDialog = pDialog;
//        pDialog = new SweetAlertDialog((Activity) ctx, SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3395ff"));
//        pDialog.setTitleText("Simpan Laporan");
//        pDialog.setContentText("Mohon tunggu sedang memproses...");
//        pDialog.setCancelable(false);
//        pDialog.show();
//        SweetAlertDialog finalPDialog = pDialog;
       // Log.i("isi_data", "lapor: "+id_user+" "+kode + " "+judul+" "+isi+" "+foto);
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);

        Call<Response_simpan> sendbio = api.edit_foto(foto);


        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    Guru.putString("foto_profil", response.body().getNama());
                    // Toast.makeText(ctx, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finalPDialog.dismiss();
                   // sukses();
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

                } else {
                    // Toast.makeText(ctx, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finalPDialog.dismiss();
                    //gagal();
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                }

            }
            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {
                Log.i("cek_error", "onFailure: "+t);

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }

    public  void  edit_no_hp(String no_hp, ProgressDialog pDialog ){
        pDialog = new ProgressDialog(ctx);
        pDialog.setTitle("Mohon Tunggu!!!");
        pDialog.setMessage("Edit No Handphone");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ProgressDialog finalPDialog = pDialog;
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);

        Call<Response_simpan> sendbio = api.edit_no_hp(no_hp);


        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    Guru.putString("no_hp", response.body().getNama());
                    // Toast.makeText(ctx, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finalPDialog.dismiss();
                    // sukses();
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

                } else {
                    // Toast.makeText(ctx, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finalPDialog.dismiss();
                    //gagal();
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                }

            }
            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {
                Log.i("cek_error", "onFailure: "+t);

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }


    }


