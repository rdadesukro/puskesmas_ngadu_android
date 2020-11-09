package goid.kotajambi.puskesmas_ngadu.view.menu_fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.squti.guru.Guru;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.jpegkit.Jpeg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.net.ssl.SSLContext;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.presenter.home;
import goid.kotajambi.puskesmas_ngadu.presenter.login;
import goid.kotajambi.puskesmas_ngadu.view.menu.CameraCapture;
import goid.kotajambi.puskesmas_ngadu.view.menu.FileUtils;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_login;
import maes.tech.intentanim.CustomIntent;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.TlsVersion;

import static android.app.Activity.RESULT_CANCELED;
import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_profil extends Fragment implements CameraCapture_new.OnInputListener {


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
    Bitmap bitmap;
    public final int SELECT_FILE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static String imageStoragePath;
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
        myContext = (FragmentActivity) activity;
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
        txtNama.setText(Guru.getString("nama_profil", "false"));
        txtEmail.setText(Guru.getString("email", "false"));
        Log.i("isi_foto", "onCreateView: " + Guru.getString("foto", "false"));
        Glide.with(this)
                .load("https://ramahpkmhandil.jambikota.go.id/uploads/profil/" + Guru.getString("foto_profil", "false"))
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
                .circleCrop()
                .error(R.drawable.us)
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
        login countryPresenter = new login(null, getActivity());
        countryPresenter.keluar(progressDialog);

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

//                            FragmentManager fm = getActivity().getSupportFragmentManager();
//                            CameraCapture_new editNameDialogFragment = new  CameraCapture_new();
//                            editNameDialogFragment.show(fm, "fragment_camera");


                            CameraCapture_new dialog1 = new CameraCapture_new();
                            dialog1.setTargetFragment(fragment_profil.this, 22); // in case of fragment to activity communication we do not need this line. But must write this i case of fragment to fragment communication
                            dialog1.show(getFragmentManager(), "fragment_camera");

                        } else if (items[item].equals("Galeri")) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);


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


    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
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

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Log.i(TAG, "getStringImage: "+encodedImage);
        return encodedImage;
    }


    @Override
    public void onSimpanClick(Jpeg data, File file1) {
        try {
            file = file1;
            String filePath = file1.getPath();
            decoded = BitmapFactory.decodeFile(filePath);
            imgFotoProfil.setImageBitmap(decoded);
            int file_size = Integer.parseInt(String.valueOf(file1.length() / 1024));
            Log.i("isi_foto", "onSimpanClick: " + file1.getName() + " " + file_size);
            Uri tempUri = getImageUri(getActivity().getApplicationContext(), decoded);
            foto(tempUri);

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

        } catch (Exception e) {
            Log.i("eeee", "onSimpanClick: " + e);

        }
    }

    void foto(Uri uri) {
        Glide.with(getActivity())
                .load(uri)
                .apply(new RequestOptions()
                        .fitCenter()
                        .circleCrop()
                        .error(R.drawable.us))
                .into(imgFotoProfil);
    }

    @SuppressLint({"MissingSuperCall", "RestrictedApi"})
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                try {


                    BitmapFactory.Options bounds = new BitmapFactory.Options();
                    bounds.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imageStoragePath, bounds);

                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    Bitmap bm = BitmapFactory.decodeFile(imageStoragePath, opts);
                    ExifInterface exif = new ExifInterface(imageStoragePath);
                    String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                    int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;

                    int rotationAngle = 0;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);


                    setToImageView(getResizedBitmap(rotatedBitmap, max_resolution_image));
                    imgFotoProfil.setImageBitmap(rotatedBitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (resultCode == RESULT_CANCELED) {

            } else {

            }
        }

        if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
            try {


                // mengambil gambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                getStringImage(decoded);
                Uri tempUri = getImageUri(getActivity().getApplicationContext(), bitmap);
                foto(tempUri);

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

                //  foto(tempUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @OnClick(R.id.btn_no_hp)
    public void btn_no_hp() {
        dialog = new BottomSheetDialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_no_hp);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        EditText  edit_no_hp = (EditText) dialog.findViewById(R.id.edit_no_hp);
        edit_no_hp.setText(txtNoHp.getText());
        edit_no_hp.requestFocus();
        Button btn_edit = (Button) dialog.findViewById(R.id.btn_edit);
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
                if (edit_no_hp.getText().toString().equals("")) {
                    //  Toast.makeText(menu_profil_pejabat_pejabat.this, "Password lama tidak boleh kosong", Toast.LENGTH_SHORT);

                    new GlideToast.makeToast(getActivity(), "Password lama tidak boleh kosong", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                    edit_no_hp.requestFocus();
                }else {
                    home countryPresenter = new home(null, getActivity());
                    countryPresenter.edit_no_hp(edit_no_hp.getText().toString().trim(), progressDialog);
                }



            }
        });



        dialog.show();
    }
}
