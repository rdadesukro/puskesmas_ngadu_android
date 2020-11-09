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
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server;
import goid.kotajambi.puskesmas_ngadu.Server.Retroserver_server_AUTH;
import goid.kotajambi.puskesmas_ngadu.model.login.Response_login;
import goid.kotajambi.puskesmas_ngadu.model.simpan.Response_simpan;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_login;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_login_with_google;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_register;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_riset_password;
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
    public void login(String email, String password,String token, ProgressDialog pDialog) {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Logging In...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_login> sendbio = api.login(email,password,token);

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
                    Guru.putString("nama_profil", response.body().getNama());
                    Guru.putString("alamat", response.body().getAlamat());
                    Guru.putString("no_hp", response.body().getNoHp());
                    Guru.putString("foto_profil", response.body().getFoto());
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
                Log.i("cek_login", "onFailure: "+t);

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

    public  void  register(String nama,String password,String email, String no_hp, String alamaat,String token, ProgressDialog pDialog ){
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Register...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


        ProgressDialog finalPDialog = pDialog;
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);

        Call<Response_simpan> sendbio = api.register(nama,password,password,email,no_hp,alamaat,token);
        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    finalPDialog.dismiss();
                    login_new(email,password,token);
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

    public void login_new(String email, String password,String token) {
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_login> sendbio = api.login(email,password,token);

        sendbio.enqueue(new Callback<Response_login>() {
            @Override
            public void onResponse(Call<Response_login> call, Response<Response_login> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    Guru.putString("status_loign", "true");
                    Guru.putString("auth", response.body().getAccessToken());
                    Guru.putString("nama_profil", response.body().getNama());
                    Guru.putString("alamat", response.body().getAlamat());
                    Guru.putString("no_hp", response.body().getNoHp());
                    Guru.putString("foto_profil", response.body().getFoto());
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


    public  void  simpan_token(String id_uses,String token){
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_simpan> sendbio = api.simpan_token(id_uses,token);
        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {

                    new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

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
    public  void  hapus_token(String token){

        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_simpan> sendbio = api.hapus_token(token);

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
    public  void  send_email(String email,ProgressDialog pDialog){

        pDialog = new ProgressDialog(ctx);
        pDialog.setTitle("Mohon Tunggu!!!");
        pDialog.setMessage("Cek Email...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ProgressDialog finalPDialog = pDialog;

        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_simpan> sendbio = api.send_email(email);

        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                String pesan = response.body().getMessage();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    dialog_berhasil("Berhasil Kirim Kode",pesan);
                    finalPDialog.dismiss();


                } else {
                    dialog_gagal("OPPS!!!",pesan);
                    finalPDialog.dismiss();

                }

            }
            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });


    }
    public  void edit_password(String kode,String password,ProgressDialog pDialog){

        pDialog = new ProgressDialog(ctx);
        pDialog.setTitle("Mohon Tunggu!!!");
        pDialog.setMessage("Riset Password...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ProgressDialog finalPDialog = pDialog;

        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_simpan> sendbio = api.edit_password(kode,password);

        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                String pesan = response.body().getMessage();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    dialog_berhasil_riset("Berhasil",pesan);
                    finalPDialog.dismiss();


                } else {
                    dialog_gagal("OPPS!!!",pesan);
                    finalPDialog.dismiss();

                }

            }
            @Override
            public void onFailure(Call<Response_simpan> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });


    }
    void dialog_berhasil(String judul,String pesan) {
        SweetAlertDialog pDialog = new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setCancelable(false);
        pDialog.setTitleText(judul);
        pDialog.setContentText(pesan);
        pDialog.setConfirmText("Ok");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                CustomIntent.customType(ctx, "fadein-to-fadeout");
                Intent intent = new Intent((Activity) ctx, menu_riset_password.class);
                intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
                ctx.startActivity(intent);
            }
        });
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

    }
    void dialog_berhasil_riset(String judul,String pesan) {
        SweetAlertDialog pDialog = new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setCancelable(false);
        pDialog.setTitleText(judul);
        pDialog.setContentText(pesan);
        pDialog.setConfirmText("Ok");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                CustomIntent.customType(ctx, "fadein-to-fadeout");
                Intent intent = new Intent((Activity) ctx, menu_login.class);
                intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
                ctx.startActivity(intent);
            }
        });
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

    }
    void dialog_gagal(String judul,String pesan) {
        SweetAlertDialog pDialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);
        pDialog.setCancelable(false);
        pDialog.setTitleText(judul);
        pDialog.setContentText(pesan);
        pDialog.setConfirmText("Ok");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

    }
    public  void  keluar(ProgressDialog pDialog ){
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Logout...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


        ProgressDialog finalPDialog = pDialog;
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);

        Call<Response_simpan> sendbio = api.logout();
        sendbio.enqueue(new Callback<Response_simpan>() {
            @Override
            public void onResponse(Call<Response_simpan> call, Response<Response_simpan> response) {

                String kode = response.body().getKode();
                Log.i("kode", "onResponse: " + kode);

                if (kode.equals("1")) {
                    finalPDialog.dismiss();
                    Intent goInput = new Intent(ctx, menu_login.class);
                    Guru.putString("status_loign", "false");
                    ctx.startActivity(goInput);
                    CustomIntent.customType(ctx, "fadein-to-fadeout");
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

}


