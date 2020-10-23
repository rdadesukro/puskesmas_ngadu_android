package goid.kotajambi.puskesmas_ngadu.view.menu_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.squti.guru.Guru;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.login;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_login;
import maes.tech.intentanim.CustomIntent;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.TlsVersion;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_profil extends Fragment {


    @BindView(R.id.btn_data)
    ImageView btnData;
    @BindView(R.id.txt_data)
    TextView txtData;
    @BindView(R.id.btn_password)
    ImageView btnPassword;
    @BindView(R.id.txt_password)
    TextView txtPassword;
    @BindView(R.id.btn_no_hp)
    ImageView btnNoHp;
    @BindView(R.id.txt_no_hp)
    TextView txtNoHp;
    @BindView(R.id.btn_email)
    ImageView btnEmail;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.img_foto_profil)
    ImageView imgFotoProfil;
    @BindView(R.id.txt_alamat)
    TextView txtAlamat;
    @BindView(R.id.txt_nama)
    TextView txtNama;
    BottomSheetDialog dialog;
    ProgressDialog progressDialog;
    EditText pass_lama, pass_baru;
    @BindView(R.id.card_keluar)
    CardView cardKeluar;
    @BindView(R.id.progres_foto)
    ProgressBar progresFoto;

    public fragment_profil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_data, container, false);
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        ButterKnife.bind(this, view);
        try {
            ProviderInstaller.installIfNeeded(getContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .allEnabledTlsVersions()
                .supportsTlsExtensions(false)
                .allEnabledCipherSuites()
                .build();

        txtAlamat.setText(Guru.getString("alamat", "false"));
        txtEmail.setText(Guru.getString("email", "false"));
        txtNoHp.setText(Guru.getString("no_hp", "false"));
        txtNama.setText(Guru.getString("nama", "false"));
        txtEmail.setText(Guru.getString("email", "false"));
        Log.i("isi_foto", "onCreateView: " + Guru.getString("foto", "false"));
        Glide.with(this)
                .load("http://192.168.1.71/puskesmas_ngadu/public/uploads/profil/" + Guru.getString("foto_profil", "false"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progresFoto.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progresFoto.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.man)
                .centerCrop()
                .circleCrop()
                .into(imgFotoProfil);
        return view;


    }


    @OnClick({R.id.btn_data, R.id.btn_password, R.id.btn_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_data:
                break;
            case R.id.btn_password:
                dialog = new BottomSheetDialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_edit_password);
                dialog.setCancelable(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.getWindow().setDimAmount(0.5f);
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                pass_lama = (EditText) dialog.findViewById(R.id.edit_pw_lama);
                pass_baru = (EditText) dialog.findViewById(R.id.edit_pw_baru);
                final EditText pass_baru2 = (EditText) dialog.findViewById(R.id.edit_konfirmasi);
                pass_lama.requestFocus();
                Button btn_edit = (Button) dialog.findViewById(R.id.btn_edit_pw);
                ImageView btn_close = (ImageView) dialog.findViewById(R.id.btn_close);

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pass_lama.getText().toString().equals("")) {
                            //  Toast.makeText(menu_profil_pejabat_pejabat.this, "Password lama tidak boleh kosong", Toast.LENGTH_SHORT);

                            new GlideToast.makeToast(getActivity(), "Password lama tidak boleh kosong", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                            pass_lama.requestFocus();
                        } else if (pass_baru.getText().toString().trim().equals("")) {
                            new GlideToast.makeToast(getActivity(), "Password baru tidak boleh kosong", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

                            // Toast.makeText(menu_profil_pejabat_pejabat.this, "Password baru tidak boleh kosong", Toast.LENGTH_SHORT);
                            pass_baru.requestFocus();
                        } else if (pass_baru2.getText().toString().trim().equals("")) {
                            new GlideToast.makeToast(getActivity(), "Password konfirmasi tidak boleh kosong", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

                            //Toast.makeText(menu_profil_pejabat_pejabat.this, "Password konfirmasi tidak boleh kosong", Toast.LENGTH_SHORT);
                            pass_baru2.requestFocus();
                        } else if (!pass_baru.getText().toString().equals(pass_baru2.getText().toString())) {
                            new GlideToast.makeToast(getActivity(), "pastikan password baru dan konfirmasi password sama !", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();


                            // Toast.makeText(menu_profil_pejabat_pejabat.this, "pastikan password baru dan konfirmasi password sama !", Toast.LENGTH_SHORT);
                            pass_baru2.requestFocus();
                        } else if (pass_baru.getText().toString().trim().length() < 6) {
                            //  Toast.makeText(menu_profil_pejabat_pejabat.this, "Minimal Password Baru 6 Karketr", Toast.LENGTH_SHORT);
                            new GlideToast.makeToast(getActivity(), "Minimal Password Baru 6 Karketr", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();


                            pass_baru.requestFocus();
                        } else {
//
                            login countryPresenter = new login(null, getActivity());
                            countryPresenter.update_password(pass_lama.getText().toString().trim(), pass_baru.getText().toString().trim(), progressDialog);


                        }


                    }
                });

                dialog.show();
                break;
            case R.id.btn_email:
                break;
        }
    }

    @OnClick(R.id.card_keluar)
    public void onViewClicked() {
        Intent goInput = new Intent(getActivity(), menu_login.class);
        Guru.putString("status_loign", "false");
        startActivity(goInput);
        CustomIntent.customType(getActivity(), "fadein-to-fadeout");
        login countryPresenter = new login(null, getActivity());
        countryPresenter.hapus_token(Guru.getString("token", "false"));

    }
}
