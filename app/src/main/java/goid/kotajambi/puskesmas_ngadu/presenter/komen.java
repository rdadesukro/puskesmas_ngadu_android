package goid.kotajambi.puskesmas_ngadu.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.IOException;
import java.util.List;

import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.model.komen.Response_komen;
import goid.kotajambi.puskesmas_ngadu.model.komen.Result_komen;
import goid.kotajambi.puskesmas_ngadu.model.laporan_komen.Response_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.model.laporan_komen.ResultItem_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.model.simpan.Response_simpan;
import goid.kotajambi.puskesmas_ngadu.view.view_komen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class komen {

    private Context ctx;
    private view_komen countryView;
    private Retroserver_server_AUTH countryService;
    public komen(view_komen view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }

    public void get_komen(String id_lapor){
        Log.i("id_lapor", "get_komen: "+id_lapor);
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_komen> call = api.get_komen(id_lapor);
        call.enqueue(new Callback<Response_komen>() {
            @Override
            public void onResponse(Call<Response_komen> call, Response<Response_komen> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_komen data = response.body();
                        Log.i("isi_komen", "onResponse: "+data);
                        if (data != null && data.getResult() != null) {
                            List<Result_komen> result = data.getResult();
                            countryView.komen(result);
                        }



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
            public void onFailure(Call<Response_komen> call, Throwable t) {
                t.printStackTrace();
                //  sliderView_bener.setBackgroundResource(R.drawable.bg_no_item_city);
                Log.i("ewkwkwkwkw", "onFailure: " + t);
                if (t instanceof IOException) {

                } else {

                }

            }
        });

    }
    public void simpan_komen(String id_lapor,String id_user,String isi, String nama){
        Log.i("data_komen", "simpan_komen: "+id_lapor+" "+id_user+" "+isi);
        ProgressDialog   pDialog = new ProgressDialog(ctx);
        pDialog.setTitle("Mohon Tunggu!!!");
        pDialog.setMessage("Simpan Komen..");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ProgressDialog finalPDialog = pDialog;
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_simpan> sendbio = api.simpan_komen(id_lapor,id_user,isi,nama);
        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, retrofit2.Response<Response_simpan> response) {

                String kode = response.body().getKode();
                String pesan = response.body().getMessage();
                Log.i("kode", "onResponse: "+kode);

                if (kode.equals("1")){
                    pDialog.dismiss();
                    new GlideToast.makeToast((Activity) ctx, ""+pesan, GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.BOTTOM).show();

                }
                else if (kode.equals("0")){
                    pDialog.dismiss();
                    new GlideToast.makeToast((Activity) ctx, ""+pesan, GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.BOTTOM).show();
                }


            }

            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof IOException) {
                    // pDialog.dismiss();
                    //Toast.makeText(ErrorHandlingActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                    // Toast.makeText(menu_lupa_password.this, "Jaringan Anda Bermasalah", Toast.LENGTH_SHORT).show();

                }
                else {
                    // pDialog.dismiss();
                    //  Toast.makeText(ErrorHandlingActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                    // Toast.makeText(menu_lupa_password.this, "Jaringan Anda Bermasalah", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
    public void get_laporan_komen(String id_lapor){
        Log.i("id_lapor", "get_komen: "+id_lapor);
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_laporan_komen> call = api.tampil_lapor_komen(id_lapor);
        call.enqueue(new Callback<Response_laporan_komen>() {
            @Override
            public void onResponse(Call<Response_laporan_komen> call, Response<Response_laporan_komen> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_laporan_komen data = response.body();
                        Log.i("isi_komen", "onResponse: "+data);
                        if (data != null && data.getResult() != null) {
                            List<ResultItem_laporan_komen> result = data.getResult();
                            countryView.laporan_komen(result);
                        }



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
            public void onFailure(Call<Response_laporan_komen> call, Throwable t) {
                t.printStackTrace();
                //  sliderView_bener.setBackgroundResource(R.drawable.bg_no_item_city);
                Log.i("ewkwkwkwkw", "onFailure: " + t);
                if (t instanceof IOException) {

                } else {

                }

            }
        });

    }

    public void cari_laporan(String kode){
        Log.i("id_lapor", "get_komen: "+kode);
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_laporan_komen> call = api.cari_laporan(kode);
        call.enqueue(new Callback<Response_laporan_komen>() {
            @Override
            public void onResponse(Call<Response_laporan_komen> call, Response<Response_laporan_komen> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_laporan_komen data = response.body();
                        Log.i("isi_komen", "onResponse: "+data);
                        if (data != null && data.getResult() != null) {
                            List<ResultItem_laporan_komen> result = data.getResult();
                            countryView.laporan_komen(result);
                        }



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
            public void onFailure(Call<Response_laporan_komen> call, Throwable t) {
                t.printStackTrace();
                //  sliderView_bener.setBackgroundResource(R.drawable.bg_no_item_city);
                Log.i("ewkwkwkwkw", "onFailure: " + t);
                if (t instanceof IOException) {

                } else {

                }

            }
        });

    }


    }


