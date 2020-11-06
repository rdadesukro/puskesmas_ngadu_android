package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.squti.guru.Guru;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jpegkit.Jpeg;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.login;
import maes.tech.intentanim.CustomIntent;

public class menu_login_with_google extends AppCompatActivity implements Validator.ValidationListener, CameraCapture.OnInputListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 007;


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

    private GoogleApiClient mGoogleApiClient;

    String nama, email;
    SweetAlertDialog pDialog;
    ProgressDialog progressDialog;
    Validator validator;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_login_with_google);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        getSupportActionBar().hide();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        pDialog = new SweetAlertDialog(menu_login_with_google.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog = new ProgressDialog(this);
        editUser.setText(Guru.getString("email", "false"));
        editPass.requestFocus();
        token = FirebaseInstanceId.getInstance().getToken();
        Guru.putString("token", token);

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        try {
            Log.i("status", "handleSignInResult: " + result.getStatus() + " " + result);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                nama = acct.getDisplayName();
                email = acct.getEmail();
                Guru.putString("nama", nama);
                Guru.putString("email", email);
                login countryPresenter = new login(null, menu_login_with_google.this);
                countryPresenter.cek_email(email);


            } else {

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


    }

    @Override
    public void onValidationSucceeded() {
        login countryPresenter = new login(null, menu_login_with_google.this);
        countryPresenter.login(editUser.getText().toString().trim(), editPass.getText().toString().trim(), token, progressDialog);
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

    @Override
    public void onSimpanClick(Jpeg data, File file) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        validator.validate();
    }

    @OnClick(R.id.btn_lupa)
    public void btn_lupa() {
        Intent intent = new Intent(menu_login_with_google.this, menu_lupa_password.class);
        intent.putExtra("Fragmentone", 3); //pass zero for Fragmentone.
        startActivity(intent);
        CustomIntent.customType(menu_login_with_google.this,"fadein-to-fadeout");

    }
}