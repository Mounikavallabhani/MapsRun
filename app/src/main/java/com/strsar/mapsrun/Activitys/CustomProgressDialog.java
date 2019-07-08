package com.strsar.mapsrun.Activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.strsar.mapsrun.R;

public class CustomProgressDialog extends AlertDialog {
    public CustomProgressDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.custom_progress_dialog);
    }
}
