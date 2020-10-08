package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.github.squti.guru.Guru;
import com.jpegkit.Jpeg;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.home;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class menu_lapor extends AppCompatActivity implements Validator.ValidationListener, CameraCapture.OnInputListener {

    @BindView(R.id.btn_lapor)
    Button btnLapor;

    @NotEmpty
    @BindView(R.id.edit_judul)
    EditText editJudul;

    @NotEmpty
    @BindView(R.id.edit_jenis)
    EditText editJenis;

    @BindView(R.id.img_lapor)
    ImageView imgLapor;

    Validator validator;

    @NotEmpty
    @BindView(R.id.edit_isi)
    AppCompatEditText editIsi;
    SweetAlertDialog pd_new;

    File file;
    @BindView(R.id.btn_foto)
    ImageView btnFoto;
    Bitmap decoded;
    public final int SELECT_FILE = 1;
    int bitmap_size = 40; // image quality 1 - 100;
    int max_resolution_image = 800;
    Uri tempUri;
    String imageTempName;
    @BindView(R.id.arrowBtn)
    Button arrowBtn;
    @BindView(R.id.con_foto)
    ConstraintLayout conFoto;
    @BindView(R.id.card_foto)
    CardView cardFoto;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lapor);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        pd_new = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pd_new.getProgressHelper().setBarColor(Color.parseColor("#3395ff"));
        progressDialog = new ProgressDialog(this);
    }


    @Override
    public void onValidationSucceeded() {
        RequestBody judul = createPartFromString("" + editJudul.getText().toString());
        RequestBody id_user = createPartFromString(Guru.getString("id_user", "false"));
        RequestBody kode = createPartFromString("1");
        RequestBody isi = createPartFromString("" + editIsi.getText().toString());
        MultipartBody.Part body = null;
        RequestBody requestFile;
        if (file == null) {
            Toast.makeText(this, "File Kosong", Toast.LENGTH_SHORT).show();
        } else {
            requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("foto_laporan", file.getName(), requestFile);
        }

        home countryPresenter = new home(null, menu_lapor.this);
        countryPresenter.lapor(id_user, kode, judul, isi, body, progressDialog);
        editIsi.setText("");
        editJudul.setText("");
        file=null;

    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_TEXT), descriptionString);
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
    public void onSimpanClick(Jpeg data, File file1) {
        try {
            file = file1;


            String filePath = file1.getPath();
            decoded = BitmapFactory.decodeFile(filePath);
            imgLapor.setImageBitmap(decoded);
            int file_size = Integer.parseInt(String.valueOf(file1.length() / 1024));
            Log.i("isi_foto", "onSimpanClick: " + file1.getName() + " " + file_size);

        } catch (Exception e) {
            Log.i("eeee", "onSimpanClick: " + e);

        }

    }


    @OnClick(R.id.btn_lapor)
    public void btn_lapor() {
        Toast.makeText(this, "ade", Toast.LENGTH_SHORT).show();
        validator.validate();

    }

    @OnClick(R.id.btn_foto)
    public void btn_foto() {
        final CharSequence[] items = {"Camera", "Galeri",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(menu_lapor.this);
        builder.setTitle("Add Foto!");
        builder.setIcon(R.drawable.ic_baseline_account_box_24);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {
                    FragmentManager fm = getSupportFragmentManager();
                    CameraCapture dialog_new = new CameraCapture();
                    dialog_new.show(fm, "fragment_camera");

                } else if (items[item].equals("Galeri")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 90);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {

            if (requestCode == 100) {


            } else if (requestCode == 90) {

                onCaptureImageResult1(data);

            } else {
            }


        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onCaptureImageResult1(Intent data2) {
        Uri uri = data2.getData();
        try {
            decoded = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            tempUri = getImageUri(getApplicationContext(), decoded, imageTempName);
            String picturePath = getRealPathFromURI(tempUri);
            file = FileUtils.getFile(this, tempUri);
            int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
            Log.i("isi_file", "onCaptureImageResult1: " + file.getName() + " " + file_size);
            setToImageView(getResizedBitmap(decoded, max_resolution_image));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }


    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imgLapor.setImageBitmap(decoded);

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @OnClick(R.id.arrowBtn)
    public void onViewClicked() {
        if (conFoto.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardFoto, new AutoTransition());
            conFoto.setVisibility(View.VISIBLE);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
        } else {
            TransitionManager.beginDelayedTransition(cardFoto, new AutoTransition());
            conFoto.setVisibility(View.GONE);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
            arrowBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
        }
    }
}
