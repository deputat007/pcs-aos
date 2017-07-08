package com.zeroisbiggerthanone.pcs_aos.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zeroisbiggerthanone.pcs_aos.R;
import com.zeroisbiggerthanone.pcs_aos.activities.LoginActivity;
import com.zeroisbiggerthanone.pcs_aos.activities.RegistrationActivity;
import com.zeroisbiggerthanone.pcs_aos.adapters.SelectAuthenticationMethodAdapter;
import com.zeroisbiggerthanone.pcs_aos.app.MyApplication;
import com.zeroisbiggerthanone.pcs_aos.models.AuthenticationMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;


public class SelectAuthenticationMethodDialogFragment extends DialogFragment
        implements OnClickListener, View.OnClickListener {

    private SelectAuthenticationMethodAdapter mAdapter;

    public static DialogFragment newInstance() {
        return new SelectAuthenticationMethodDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        @SuppressLint("InflateParams") final View view = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.content_authentication_methods, null);

        final RecyclerView recyclerView = (RecyclerView)
                view.findViewById(R.id.rv_authentication_methods);
        final AppCompatCheckBox checkBox = (AppCompatCheckBox)
                view.findViewById(R.id.cb_default_authentication_method);
        view.findViewById(R.id.wrapper_registration).setOnClickListener(this);

        mAdapter = new SelectAuthenticationMethodAdapter(Arrays.asList(AuthenticationMethod.values()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), this, recyclerView));
        recyclerView.addItemDecoration(SpaceItemDecoration.getDefaultSpaceItemDecoration(getContext()));

        if (MyApplication.getApplication().getPrefManager().isAuthenticationMethod()) {
            final AuthenticationMethod method = MyApplication
                    .getApplication()
                    .getPrefManager()
                    .getAuthenticationMethod();
            mAdapter.singleSelection(mAdapter.getAuthenticationMethods().indexOf(method));
        }

        checkBox.setChecked(MyApplication
                .getApplication()
                .getPrefManager()
                .isAuthenticationMethod());

        final Dialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(R.string.title_authentication_method)
                .setPositiveButton(R.string.text_button_ok, null)
                .setNegativeButton(R.string.text_button_cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                final Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mAdapter.getSelectedItemCount() <= 0) {
                            Toast.makeText(getContext(), getString(R.string.text_select_authentication_method),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            final AuthenticationMethod method =
                                    mAdapter.getSelectedAuthenticationMethods().get(0);

                            if (checkBox.isChecked()) {
                                MyApplication
                                        .getApplication()
                                        .getPrefManager()
                                        .saveAuthenticationMethod(method);
                            } else {
                                MyApplication
                                        .getApplication()
                                        .getPrefManager()
                                        .removeAuthenticationMethod();
                            }

                            final LoginActivity.AuthenticationMethodEvent event =
                                    new LoginActivity.AuthenticationMethodEvent(method);

                            EventBus.getDefault().post(event);

                            dismiss();
                        }
                    }
                });
            }
        });

        return dialog;
    }

    @Override
    public void onClick(View view, int position) {
        mAdapter.singleSelection(position);
    }

    @Override
    public void onLongClick(View view, int position) {
        mAdapter.singleSelection(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wrapper_registration:
                startActivity(RegistrationActivity.getIntent(getActivity(), null));
        }
    }
}
