package goid.kotajambi.puskesmas_ngadu.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.github.squti.guru.Guru;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import goid.kotajambi.puskesmas_ngadu.Server.ApiRequest;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.model.login.Response_login;
import goid.kotajambi.puskesmas_ngadu.model.simpan.Response_simpan;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_login;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_login_with_google;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_register;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_utama;
import goid.kotajambi.puskesmas_ngadu.view.view_komen;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login {

    private Context ctx;
    private view_komen countryView;
    private Retroserver_server_AUTH countryService;
    public login(view_komen view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }
    public void login(String email, String password, ProgressDialog pDialog) {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Logging In...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_login> sendbio = api.login(email,password);

        ProgressDialog finalPDialog = pDialog;
        sendbio.enqueue(new Callback<Response_login>() {
            @Override
            public void onResponse(Call<Response_login> call, Response<Response_login> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    finalPDialog.dismiss();
                    Guru.putString("status_loign", "true");
                    Guru.putString("auth", response.body().getAccessToken());
                    Guru.putString("nama", response.body().getNama());
                    Guru.putString("alamat", response.body().getAlamat());
                    Guru.putString("no_hp", response.body().getNoHp());
                    Guru.putString("foto", response.body().getFoto());
                    Guru.putString("email", response.body().getEmail());
                    Guru.putString("id_user", String.valueOf(response.body().getIdUser()));
                    Intent intent = new Intent((Activity) ctx, menu_utama.class);
                    intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
                    ctx.startActivity(intent);
                    CustomIntent.customType((Activity) ctx,"fadein-to-fadeout");
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

                } else {
                    finalPDialog.dismiss();
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                }

            }
            @Override
            public void onFailure(Call<Response_login> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }

    public  void  cek_email(String email){

        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_simpan> sendbio = api.cek_email(email);

        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    CustomIntent.customType(ctx, "fadein-to-fadeout");
                    GoogleSignInOptions gso = new GoogleSignInOptions.
                            Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                            build();

                    GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(ctx,gso);
                    googleSignInClient.signOut();
                    Intent intent = new Intent((Activity) ctx, menu_login_with_google.class);
                    intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
                    ctx.startActivity(intent);
                    Guru.putString("email", email);

                } else {
                    CustomIntent.customType(ctx, "fadein-to-fadeout");
                    GoogleSignInOptions gso = new GoogleSignInOptions.
                            Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                            build();

                    GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(ctx,gso);
                    googleSignInClient.signOut();
                    Intent intent = new Intent((Activity) ctx, menu_register.class);
                    intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
                    ctx.startActivity(intent);
                }

            }
            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });


    }

    public  void  register(String nama,String password,String email, String no_hp, String alamaat, ProgressDialog pDialog ){
//        pDialog = new SweetAlertDialog((Activity) ctx, SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3395ff"));
//        pDialog.setTitleText("Simpan Data");
//        pDialog.setContentText("Mohon tunggu sedang memproses...");
//        pDialog.setCancelable(false);
//        pDialog.show();
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Register...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


        ProgressDialog finalPDialog = pDialog;
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);

        Call<Response_simpan> sendbio = api.register(nama,password,password,email,no_hp,alamaat);
        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    finalPDialog.dismiss();
                    login_new(email,password);
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

                } else {
                    finalPDialog.dismiss();
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                }

            }
            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }

    public void login_new(String email, String password) {
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_login> sendbio = api.login(email,password);

        sendbio.enqueue(new Callback<Response_login>() {
            @Override
            public void onResponse(Call<Response_login> call, Response<Response_login> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    Guru.putString("status_loign", "true");
                    Guru.putString("auth", response.body().getAccessToken());
                    Guru.putString("nama", response.body().getNama());
                    Guru.putString("alamat", response.body().getAlamat());
                    Guru.putString("no_hp", response.body().getNoHp());
                    Guru.putString("foto", response.body().getFoto());
                    Guru.putString("id_user", String.valueOf(response.body().getIdUser()));
                    Intent intent = new Intent((Activity) ctx, menu_utama.class);
                    intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
                    ctx.startActivity(intent);
                    CustomIntent.customType((Activity) ctx,"fadein-to-fadeout");

                } else {
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                }

            }
            @Override
            public void onFailure(Call<Response_login> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }

    public void update_password(String password,String password_baru,ProgressDialog pDialog) {

        pDialog = new ProgressDialog(ctx);
        pDialog.setTitle("Mohon Tunggu!!!");
        pDialog.setMessage("Update Password...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_login> sendbio = api.edit_pass(password,password_baru);

        ProgressDialog finalPDialog = pDialog;
        sendbio.enqueue(new Callback<Response_login>() {
            @Override
            public void onResponse(Call<Response_login> call, Response<Response_login> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    finalPDialog.dismiss();
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

                } else if (kode.equals("3")){
                    finalPDialog.dismiss();
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                }else {
                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

                }

            }
            @Override
            public void onFailure(Call<Response_login> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }

}


