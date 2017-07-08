package com.zeroisbiggerthanone.pcs_aos.utils;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;

import com.zeroisbiggerthanone.pcs_aos.R;


public class ProgressBarDialogFragment extends DialogFragment {

    public static DialogFragment newInstance() {
        return new ProgressBarDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog progressDialog = new Dialog(getContext());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_dialog);
        final Window window = progressDialog.getWindow();

        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        progressDialog.setCancelable(false);

        return progressDialog;
    }
}
