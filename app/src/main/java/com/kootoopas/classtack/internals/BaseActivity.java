package com.kootoopas.classtack.internals;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import com.kootoopas.classtack.R;

/**
 * Created by Fotis on 08/06/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    protected void setActionBarTitle(int resId) {
        if (getActionBar() != null) {
            getActionBar().setTitle(resId);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
