package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.github.squti.guru.Guru;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.login;
import maes.tech.intentanim.CustomIntent;

public class menu_register extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.btn_batal)
    Button btnBatal;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @ConfirmPassword(message = "Konfirmasi Password tidak sama")
    @NotEmpty
    @BindView(R.id.edit_confirmasi)
    EditText editConfirmasi;

    @Password(min = 6, message = "Minimal Password 6 Karakter")
    @NotEmpty
    @BindView(R.id.edit_password)
    EditText editPassword;

    @NotEmpty
    @BindView(R.id.edit_alamat)
    AppCompatEditText editAlamat;

    @NotEmpty
    @BindView(R.id.edit_no_hp)
    EditText editNoHp;

    @NotEmpty
    @BindView(R.id.edit_email)
    EditText editEmail;


    @NotEmpty
    @BindView(R.id.edit_nama)
    EditText editNama;
    Validator validator;
    SweetAlertDialog pd_new;
    ProgressDialog progressDialog;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_register);
        ButterKnife.bind(this);
        editNama.setText(Guru.getString("nama_profil", "false"));
        editEmail.setText(Guru.getString("email", "false"));
        validator = new Validator(this);
        validator.setValidationListener(this);
        pd_new = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog = new ProgressDialog(this);
        getSupportActionBar().hide();
        token = FirebaseInstanceId.getInstance().getToken();
        Guru.putString("token",token);
    }

    @OnClick({R.id.btn_batal, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_batal:
                Intent intent = new Intent(menu_register.this, menu_login.class);
                intent.putExtra("Fragmentone", 3);
                startActivity(intent);
                CustomIntent.customType(menu_register.this, "fadein-to-fadeout");
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(menu_register.this,gso);
                googleSignInClient.signOut();
                break;
            case R.id.btn_register:
                validator.validate();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        String nama = editNama.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String alamat = editAlamat.getText().toString().trim();
        String pass_konfirmasi = editConfirmasi.getText().toString().trim();
        String no_hp = editNoHp.getText().toString().trim();
        Guru.putString("password", password);
        login countryPresenter = new login(null,menu_register.this);
        countryPresenter.register(nama,password,email,no_hp,alamat,token,progressDialog);
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
}