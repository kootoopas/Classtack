package com.kootoopas.classtack.internals;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Fotis on 08/06/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
