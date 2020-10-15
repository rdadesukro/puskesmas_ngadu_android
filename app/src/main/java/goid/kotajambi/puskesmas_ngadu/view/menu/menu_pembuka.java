package goid.kotajambi.puskesmas_ngadu.view.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.squti.guru.Guru;
import com.trncic.library.DottedProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import maes.tech.intentanim.CustomIntent;

public class menu_pembuka extends AppCompatActivity {

    @BindView(R.id.progress2)
    DottedProgressBar progress2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pembuka);
        ButterKnife.bind(this);
        progress2.setVisibility(View.VISIBLE);
        progress2.startProgress();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent goInput = new Intent(menu_pembuka.this, menu_login.class);
                startActivity(goInput);
                CustomIntent.customType(menu_pembuka.this, "fadein-to-fadeout");
            }
        }, 3000L); //3000 L = 3 detik
    }

}