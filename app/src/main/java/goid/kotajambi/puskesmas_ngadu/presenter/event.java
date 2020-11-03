package goid.kotajambi.puskesmas_ngadu.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.model.event.IsiItem_events;
import goid.kotajambi.puskesmas_ngadu.model.event.Response_events;
import goid.kotajambi.puskesmas_ngadu.view.view_event;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class event {

    private Context ctx;
    private view_event countryView;
    private Retroserver_server_AUTH countryService;
    public event(view_event view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }


    public void get_event(){
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_events> call = api.get_event();
        call.enqueue(new Callback<Response_events>() {
            @Override
            public void onResponse(Call<Response_events> call, Response<Response_events> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_events data = response.body();
                        Log.i("isi_komen", "onResponse: "+data);
                        if (data != null && data.getIsi() != null) {
                            List<IsiItem_events> result = data.getIsi();
                            countryView.event(result);
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
            public void onFailure(Call<Response_events> call, Throwable t) {
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


