package com.zeroisbiggerthanone.pcs_aos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.zeroisbiggerthanone.pcs_aos.R;
import com.zeroisbiggerthanone.pcs_aos.activities.RegistrationActivity;


public class DefaultFragment extends BaseFragment implements View.OnClickListener {

    private FloatingActionButton mFabAddUser;
    private TextView mTextViewSelectMethod;

    public static Fragment getInstance(@Nullable Bundle args) {
        final Fragment fragment = new DefaultFragment();

        if (args != null) {
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_default;
    }

    @Override
    protected void initUI() {
        mFabAddUser = findViewById(R.id.fab_add_user);
        mTextViewSelectMethod = findViewById(R.id.tv_select_authorization_method);
    }

    @Override
    protected void setUI(@Nullable Bundle savedInstanceState) {
        mFabAddUser.setOnClickListener(this);
        mTextViewSelectMethod.setOnClickListener(this);
    }

    @Override
    protected int getTitle() {
        return -1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_user:
                startActivity(RegistrationActivity.getIntent(getParentActivity(), null));
                break;

            case R.id.tv_select_authorization_method:
                getParentActivity().showAuthenticationMethodDialogFragment();
                break;
        }
    }

}
