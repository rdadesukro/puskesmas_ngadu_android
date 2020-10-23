package goid.kotajambi.puskesmas_ngadu.Server;


import goid.kotajambi.puskesmas_ngadu.model.bener.Response_bener;
import goid.kotajambi.puskesmas_ngadu.model.event.Response_events;
import goid.kotajambi.puskesmas_ngadu.model.jumlah_laporan_saya.Response_jumlah;
import goid.kotajambi.puskesmas_ngadu.model.komen.Response_komen;
import goid.kotajambi.puskesmas_ngadu.model.laporan_komen.Response_laporan_komen;
import goid.kotajambi.puskesmas_ngadu.model.laporan_saya.Response_laporan_saya;
import goid.kotajambi.puskesmas_ngadu.model.layanan.Response_layanan;
import goid.kotajambi.puskesmas_ngadu.model.login.Response_login;
import goid.kotajambi.puskesmas_ngadu.model.model_berita.Response_berita;
import goid.kotajambi.puskesmas_ngadu.model.simpan.Response_simpan;
import goid.kotajambi.puskesmas_ngadu.model.slider.Response_slider;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ApiRequest {


    @GET("tampil_lapor_semua")
    Call<Response_laporan_saya> pagni(@Query("page") int page);


    @GET("data_lapor_saya")
    Call<Response_laporan_saya> pagni_saya(@Query("page") int page);

    @GET("data_lapor_user")
    Call<Response_laporan_saya> get_laporan_saya();

    @FormUrlEncoded
    @POST("signup")
    Call<Response_simpan> register(
            @Field("name") String name,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("email") String email,
            @Field("no_hp") String no_hp,
            @Field("alamat") String alamat,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("simpan_token")
    Call<Response_simpan> simpan_token(
            @Field("users_id") String users_id,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("login")
    Call<Response_login>login(
            @Field("email") String username,
            @Field("password") String password,
            @Field("token") String token);




    @FormUrlEncoded
    @POST("edit_pass")
    Call<Response_login> edit_pass(
            @Field("password") String password,
            @Field("password_baru") String password_baru);


    @FormUrlEncoded
    @POST("cek_email")
    Call<Response_simpan> cek_email(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("hapus_token")
    Call<Response_simpan> hapus_token(
            @Field("token") String token);

    @FormUrlEncoded
    @POST("simpan_komen")
    Call<Response_simpan> simpan_komen(@Field("lapor_id") String lapor_id,
                                       @Field("user_id") String users_id,
                                       @Field("konten") String konten);



    @GET("new/?json=get_category_posts&category_id=45&page=1")
   // Call<Response_berita> get_berita();
    Call<Response_berita> get_berita();

    @GET("jumlah_laporan_Saya")
    Call<Response_jumlah> get_jumlah();

    @GET("slider")
    Call<Response_slider> get_slider();

    @GET("baner_sikomo.php")
    Call<Response_bener> get_benener();

    @GET("tampil_komen")
    Call<Response_komen> get_komen(@Query("id_lapor") String id_lapor);

    @GET("tampil_event")
    Call<Response_events> get_event();

    @GET("tampil_lapor_komen")
    Call<Response_laporan_komen> tampil_lapor_komen(@Query("lapor_id") String lapor_id);

    @POST("tampil_layanan")
    Call<Response_layanan> get_layanan();


    @Multipart
    @POST("lapor")
    Call<Response_simpan> simpan_laporan(
            @Part("user_id") RequestBody user_id,
            @Part("kode") RequestBody kode,
            @Part("isi_laporan") RequestBody isi_laporan,
            @Part("judul") RequestBody judul,
            @Part MultipartBody.Part foto_laporan);

}


