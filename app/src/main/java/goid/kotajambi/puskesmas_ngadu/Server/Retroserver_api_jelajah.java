package goid.kotajambi.puskesmas_ngadu.Server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroserver_api_jelajah {
    private  static  final String base_url = "http://sisamsul.jambikota.go.id/AndroFile/";


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}




