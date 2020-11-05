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
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.login;

public class menu_lupa_password extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty
    @Email
    @BindView(R.id.edit_user)
    EditText editUser;

    @BindView(R.id.btn_login)
    Button btnLogin;
    ProgressDialog progressDialog;

    Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lupa_password);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        getSupportActionBar().hide();
    }

    @Override
    public void onValidationSucceeded() {
        login countryPresenter = new login(null, menu_lupa_password.this);
        countryPresenter.send_email(editUser.getText().toString().trim(), progressDialog);
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

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        validator.validate();
    }
}