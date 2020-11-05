package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.login;

public class menu_riset_password extends AppCompatActivity implements Validator.ValidationListener {
    ProgressDialog progressDialog;
    Validator validator;

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
    @BindView(R.id.edit_kode)
    EditText editKode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_riset_password);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        getSupportActionBar().hide();
    }

    @Override
    public void onValidationSucceeded() {
        login countryPresenter = new login(null, menu_riset_password.this);
        countryPresenter.edit_password(editKode.getText().toString().trim(),editPassword.getText().toString().trim() ,progressDialog);
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

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        Toast.makeText(this, "ADADADA", Toast.LENGTH_SHORT).show();
        validator.validate();
    }
}