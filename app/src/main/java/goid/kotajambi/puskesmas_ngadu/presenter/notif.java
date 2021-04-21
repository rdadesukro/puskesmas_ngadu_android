package goid.kotajambi.puskesmas_ngadu.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.squti.guru.Guru;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.IOException;
import java.util.List;

import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.model.event.IsiItem_events;
import goid.kotajambi.puskesmas_ngadu.model.event.Response_events;
import goid.kotajambi.puskesmas_ngadu.model.notif.IsiItem_notif;
import goid.kotajambi.puskesmas_ngadu.model.notif.Response_notif;
import goid.kotajambi.puskesmas_ngadu.model.simpan.Response_simpan;
import goid.kotajambi.puskesmas_ngadu.view.view_event;
import goid.kotajambi.puskesmas_ngadu.view.view_notif;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class notif {

    private Context ctx;
    private view_notif countryView;
    private Retroserver_server_AUTH countryService;
    public notif(view_notif view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }


    public void get_notif(){
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_notif> call = api.get_notif();
        call.enqueue(new Callback<Response_notif>() {
            @Override
            public void onResponse(Call<Response_notif> call, Response<Response_notif> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_notif data = response.body();
                        Log.i("isi_komen", "onResponse: "+data);
                        if (data != null && data.getIsi() != null) {
                            List<IsiItem_notif> result = data.getIsi();
                           // badge.setNumber(jumlah_notif);
                            countryView.notif(result);
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
                                Toast.makeText(ctx, "Notif error", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<Response_notif> call, Throwable t) {
                t.printStackTrace();
                //  sliderView_bener.setBackgroundResource(R.drawable.bg_no_item_city);
                Log.i("ewkwkwkwkw", "onFailure: " + t);
                if (t instanceof IOException) {

                } else {

                }

            }
        });

    }
    public  void  edit_status(String id,String status){

        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_simpan> sendbio = api.edit_status(id,status);

        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {

                } else {
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

                }

            }
            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }

    }


