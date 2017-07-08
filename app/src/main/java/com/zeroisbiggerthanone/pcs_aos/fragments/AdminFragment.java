package com.zeroisbiggerthanone.pcs_aos.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zeroisbiggerthanone.pcs_aos.R;
import com.zeroisbiggerthanone.pcs_aos.activities.MainActivity;
import com.zeroisbiggerthanone.pcs_aos.api.ApiHelper;
import com.zeroisbiggerthanone.pcs_aos.app.MyApplication;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminFragment extends BaseFragment implements View.OnClickListener {

    private TextInputLayout mTextInputLayoutLogin;
    private TextInputEditText mTextInputEditTextLogin;

    private TextInputLayout mTextInputLayoutPassword;
    private TextInputEditText mTextInputEditTextPassword;

    private Button mButtonLogin;

    public static Fragment getInstance(@Nullable Bundle args) {
        final Fragment fragment = new AdminFragment();

        if (args != null) {
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_admin;
    }

    @Override
    protected void initUI() {

        mTextInputLayoutLogin = findViewById(R.id.text_input_layout_login);
        mTextInputEditTextLogin = findViewById(R.id.edit_text_login);

        mTextInputLayoutPassword = findViewById(R.id.text_input_layout_password);
        mTextInputEditTextPassword = findViewById(R.id.edit_text_password);

        mButtonLogin = findViewById(R.id.button_login);
    }

    @Override
    protected void setUI(@Nullable Bundle savedInstanceState) {
        mButtonLogin.setOnClickListener(this);

        mTextInputEditTextLogin.addTextChangedListener(
                new TextValidator(mTextInputEditTextLogin));
        mTextInputEditTextPassword.addTextChangedListener(
                new TextValidator(mTextInputEditTextPassword));
    }

    @Override
    protected int getTitle() {
        return R.string.title_admin;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                login();
                break;
        }
    }

    private void login() {
        if (validateLogin() && validatePassword()) {
            final String login = mTextInputEditTextLogin.getText().toString().trim();
            final String password = mTextInputEditTextPassword.getText().toString().trim();

            getParentActivity().showProgressBarDialog();
            ApiHelper.loginAdmin(login, password, new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        MyApplication.getApplication().getPrefManager().saveToken(response.body());

                        startActivity(MainActivity.getIntent(getParentActivity(), null));
                        getParentActivity().hideProgressBarDialog();
                        getParentActivity().finish();
                    } else {
                        try {
                            getParentActivity().hideProgressBarDialog();
                            Toast.makeText(getParentActivity(), response.errorBody().string(),
                                    Toast.LENGTH_SHORT).show();
                        } catch (IOException ignored) {
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getParentActivity(), t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    getParentActivity().hideProgressBarDialog();
                }
            });
        }
    }

    private boolean validateLogin() {
        final String login = mTextInputEditTextLogin.getText().toString().trim();

        if (TextUtils.isEmpty(login)) {
            mTextInputLayoutLogin.setError(getString(R.string.error_login_is_empty));
            requestFocus(mTextInputEditTextLogin);
            return false;
        }

        if (login.length() < 6) {
            mTextInputLayoutLogin.setError(getString(R.string.error_login_is_too_small));
            requestFocus(mTextInputEditTextLogin);
            return false;
        }

        mTextInputLayoutLogin.setErrorEnabled(false);
        return true;
    }

    private boolean validatePassword() {
        final String password = mTextInputEditTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            mTextInputLayoutPassword.setError(getString(R.string.error_password_is_empty));
            requestFocus(mTextInputEditTextPassword);
            return false;
        }

        if (password.length() < 6) {
            mTextInputLayoutPassword.setError(getString(R.string.error_password_is_too_small));
            requestFocus(mTextInputEditTextPassword);
            return false;
        }

        mTextInputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private class TextValidator implements TextWatcher {

        private View mView;

        TextValidator(View view) {
            mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (mView.getId()) {
                case R.id.edit_text_login:
                    validateLogin();
                    break;

                case R.id.edit_text_password:
                    validatePassword();
                    break;
            }
        }
    }
}
