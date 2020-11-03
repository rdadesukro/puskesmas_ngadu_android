package goid.kotajambi.puskesmas_ngadu.view.menu_fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;

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
import com.jpegkit.Jpeg;
import com.mobsandgeeks.saripaar.Validator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.home;
import goid.kotajambi.puskesmas_ngadu.presenter.login;

import goid.kotajambi.puskesmas_ngadu.view.menu.CameraCapture;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_lapor;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_login;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_utama;
import maes.tech.intentanim.CustomIntent;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.TlsVersion;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_profil extends Fragment  implements CameraCapture_new.OnInputListener  {


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

    Bitmap decoded;
    int bitmap_size = 40;
    int max_resolution_image = 800;
    Uri tempUri;
    String imageTempName;
    File file;

    private FragmentActivity myContext;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public fragment_profil() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
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
//        Glide.with(this)
//                .load("https://ramahpkmhandil.jambikota.go.id/uploads/profil/" + Guru.getString("foto_profil", "false"))
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        progresFoto.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        progresFoto.setVisibility(View.GONE);
//                        return false;
//                    }
//                })
//                .circleCrop()
//                .error(R.drawable.us)
//                .into(imgFotoProfil);
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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
        MenuItem refres = menu.findItem(R.id.refres);
        refres.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final CharSequence[] items = {"Camera", "Galeri",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Foto!");
                builder.setIcon(R.drawable.ic_baseline_account_box_24);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Camera")) {

                            FragmentManager fm = myContext.getSupportFragmentManager();
                            CameraCapture_new editNameDialogFragment = new  CameraCapture_new();
                            editNameDialogFragment.show(fm, "fragment_camera");

                        } else if (items[item].equals("Galeri")) {
                            if(ActivityCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            {
                                requestPermissions(
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        2000);
                            }
                            else {
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, 90);
                            }


                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.setCancelable(false);
                builder.show();

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
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
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_TEXT), descriptionString);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onCaptureImageResult1(Intent data2) {
        Uri uri = data2.getData();
        try {
            decoded = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

            tempUri = getImageUri(getContext().getApplicationContext(), decoded, imageTempName);
            String picturePath = getRealPathFromURI(tempUri);
            file = FileUtils.getFile(getContext(), tempUri);
            int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
            Log.i("isi_file", "onCaptureImageResult1: " + file.getName() + " " + file_size);
            setToImageView(getResizedBitmap(decoded, max_resolution_image));

            MultipartBody.Part body = null;
            RequestBody requestFile;
            if (file == null) {
                Toast.makeText(getActivity(), "File Kosong", Toast.LENGTH_SHORT).show();
            } else {
                requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                body = MultipartBody.Part.createFormData("foto_laporan", file.getName(), requestFile);
            }
            home countryPresenter = new home(null, getActivity());
            countryPresenter.edit_foto(body, progressDialog);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext().getApplicationContext(), contentUri, proj, null, null, null);
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
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }


    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        Log.i("isi_foto", "setToImageView: "+decoded);
        imgFotoProfil.setImageBitmap(decoded);

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

    @Override
    public void onSimpanClick(Jpeg data, File file) {

    }


//    @Override
//    public void onSimpanClick(Jpeg data, File file1) {
//        try {
//            file = file1;
//
//
//            String filePath = file1.getPath();
//            decoded = BitmapFactory.decodeFile(filePath);
//            imgFotoProfil.setImageBitmap(decoded);
//            int file_size = Integer.parseInt(String.valueOf(file1.length() / 1024));
//            Log.i("isi_foto", "onSimpanClick: " + file1.getName() + " " + file_size);
//
//        } catch (Exception e) {
//            Log.i("eeee", "onSimpanClick: " + e);
//
//        }
//    }
}
