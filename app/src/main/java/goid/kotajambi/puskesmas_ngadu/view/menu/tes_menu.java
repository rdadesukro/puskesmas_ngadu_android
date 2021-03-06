package goid.kotajambi.puskesmas_ngadu.view.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.squti.guru.Guru;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.login;
import maes.tech.intentanim.CustomIntent;

public class tes_menu extends AppCompatActivity implements Validator.ValidationListener {
    private static final int RC_SIGN_IN = 007;


    @BindView(R.id.btn_email)
    CardView btnEmail;

    @NotEmpty
    @Email
    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_lupa)
    TextView btnLupa;

    @NotEmpty
    @BindView(R.id.edit_pass)
    EditText editPass;
//    @BindView(R.id.progress)
//    DottedProgressBar progressBar2;

    private GoogleApiClient mGoogleApiClient;

    String nama, email;
    SweetAlertDialog pDialog;
    ProgressDialog progressDialog;

    Validator validator;
    String status_login;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes_menu);
        ButterKnife.bind(this);
//        try {
//            ProviderInstaller.installIfNeeded(this);
//            SSLContext sslContext;
//            sslContext = SSLContext.getInstance("TLSv1.2");
//            sslContext.init(null, null, null);
//            sslContext.createSSLEngine();
//        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
//                | NoSuchAlgorithmException | KeyManagementException e) {
//            e.printStackTrace();
//        }
        validator = new Validator(this);
        validator.setValidationListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //getSupportActionBar().hide();
        //token = FirebaseInstanceId.getInstance().getToken();
        Guru.putString("token",token);

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        pDialog = new SweetAlertDialog(tes_menu.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog = new ProgressDialog(this);

        status_login = Guru.getString("status_loign", "false");

        if (status_login.equals("true")){
            Intent intent  = new Intent(tes_menu.this, menu_utama.class);
            intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
            startActivity(intent);
        }else {

        }
    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    void akitf(){
//        progressBar2.setVisibility(View.VISIBLE);
//        progressBar2.startProgress();
        btnLogin.setClickable(false);
        btnEmail.setClickable(false);

    }

    void mati(){
//        progressBar2.stopProgress();
//        progressBar2.setVisibility(View.GONE);
        btnLogin.setClickable(true);
        btnEmail.setClickable(true);
    }


    private void handleSignInResult(GoogleSignInResult result) {
        try {
            Log.i("status", "handleSignInResult: " + result.getStatus() + " " + result);
            if (result.isSuccess()) {
                akitf();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GoogleSignInAccount acct = result.getSignInAccount();
                        nama = acct.getDisplayName();
                        email = acct.getEmail();
                        Guru.putString("nama", nama);
                        Guru.putString("email", email);
                        login countryPresenter = new login(null, tes_menu.this);
                        countryPresenter.cek_email(email);
                    }
                }, 3000);

            } else {
                Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

            Log.i("informasi", "handleSignInResult:" + e);

        }
        Log.i("informasi", "handleSignInResult:" + result.isSuccess() + " " + result);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.i("satus_result", "onActivityResult: " + result.getStatus());
            if (result.isSuccess()) {

            } else {
                //new GlideToast.makeToast(this, "Akun Ini Belum Login", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

            }
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mati();


    }

    @Override
    public void onValidationSucceeded() {
        //progressBar2.setVisibility(View.VISIBLE);
        login countryPresenter = new login(null, tes_menu.this);
        countryPresenter.login(editUser.getText().toString().trim(), editPass.getText().toString().trim(),token, progressDialog);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }

    @OnClick({R.id.btn_email, R.id.btn_register, R.id.btn_login, R.id.btn_lupa})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_email:
                OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

                if (opr.isDone()) {

                    GoogleSignInResult result = opr.get();
                    handleSignInResult(result);

                } else {


                    opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                        @Override
                        public void onResult(GoogleSignInResult googleSignInResult) {
                            //hideProgressDialog();
                            handleSignInResult(googleSignInResult);
                        }
                    });


                }

                signIn();
                break;
            case R.id.btn_lupa:
                Intent intent = new Intent(tes_menu.this, menu_lupa_password.class);
                intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
                startActivity(intent);
                CustomIntent.customType(tes_menu.this,"fadein-to-fadeout");

                break;

        }
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        validator.validate();
    }
}