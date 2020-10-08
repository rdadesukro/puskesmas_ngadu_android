package goid.kotajambi.puskesmas_ngadu.Server;



import com.github.squti.guru.Guru;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroserver_server_AUTH {
    private  static  final String base_url = "http://192.168.1.71/puskesmas_ngadu/public/api/auth/";
   //private  static  final String base_url =    "http://192.168.56.1/e_pelayanan/";

    public static  final String base_url_image_before = "https://sipaten.jambikota.go.id/android/images/before/";
    public static  final String base_url_image_after = "https://sipaten.jambikota.go.id/android/images/after/";
    public static  final String base_url_image_user = "https://sipaten.jambikota.go.id/android/images/user/";
    public static  final String url_register = "https://sipaten.jambikota.go.id/android/register.php";
    public static  final String url_kirim_laporan_user = "https://sipaten.jambikota.go.id/android/kirim_laporan.php";
    public static  final String url_login = "https://sipaten.jambikota.go.id/android/Login.php";

    private static Retrofit retrofit;
    public static Retrofit getClient()
    {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + Guru.getString("auth", "false"))
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit;
    }


}
