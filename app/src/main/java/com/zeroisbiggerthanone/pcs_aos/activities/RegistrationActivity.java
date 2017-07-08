package com.zeroisbiggerthanone.pcs_aos.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zeroisbiggerthanone.pcs_aos.R;
import com.zeroisbiggerthanone.pcs_aos.api.ApiHelper;
import com.zeroisbiggerthanone.pcs_aos.models.Password;
import com.zeroisbiggerthanone.pcs_aos.models.User;
import com.zeroisbiggerthanone.pcs_aos.models.UserBase;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    private TextInputLayout mTextInputLayoutLogin;
    private TextInputEditText mTextInputEditTextLogin;

    private TextInputLayout mTextInputLayoutPassword;
    private TextInputEditText mTextInputEditTextPassword;

    private TextInputLayout mTextInputLayoutConfirmPassword;
    private TextInputEditText mTextInputEditTextConfirmPassword;

    private TextInputLayout mTextInputLayoutSecretNumber;
    private TextInputEditText mTextInputEditTextSecretNumber;

    private TextInputLayout mTextInputLayoutPhoneNumber;
    private TextInputEditText mTextInputEditTextPhoneNumber;

    private AppCompatRadioButton mRadioButtonUser;
    private AppCompatRadioButton mRadioButtonAdmin;

    private Button mButtonCreateAccount;

    public static Intent getIntent(@NonNull final Context packageContext,
                                   @Nullable final Bundle bundle) {
        final Intent intent = new Intent(packageContext, RegistrationActivity.class);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initUI() {

        mTextInputLayoutLogin = findViewWithId(R.id.text_input_layout_login);
        mTextInputEditTextLogin = findViewWithId(R.id.edit_text_login);

        mTextInputLayoutPassword = findViewWithId(R.id.text_input_layout_password);
        mTextInputEditTextPassword = findViewWithId(R.id.edit_text_password);

        mTextInputLayoutConfirmPassword = findViewWithId(R.id.text_input_layout_confirm_password);
        mTextInputEditTextConfirmPassword = findViewWithId(R.id.edit_text_confirm_password);

        mTextInputLayoutSecretNumber = findViewWithId(R.id.text_input_layout_secret_number);
        mTextInputEditTextSecretNumber = findViewWithId(R.id.edit_text_secret_number);

        mTextInputLayoutPhoneNumber = findViewWithId(R.id.text_input_layout_phone_number);
        mTextInputEditTextPhoneNumber = findViewWithId(R.id.edit_text_phone_number);

        mRadioButtonUser = findViewWithId(R.id.radio_user);
        mRadioButtonAdmin = findViewWithId(R.id.radio_admin);

        mButtonCreateAccount = findViewWithId(R.id.button_create_account);
    }

    @Override
    protected void setUI(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.title_registration);

        mButtonCreateAccount.setOnClickListener(this);

        mTextInputEditTextLogin.addTextChangedListener(
                new TextValidator(mTextInputEditTextLogin));
        mTextInputEditTextPhoneNumber.addTextChangedListener(
                new TextValidator(mTextInputEditTextPhoneNumber));
        mTextInputEditTextSecretNumber.addTextChangedListener(
                new TextValidator(mTextInputEditTextSecretNumber));
        mTextInputEditTextPassword.addTextChangedListener(
                new TextValidator(mTextInputEditTextPassword));
        mTextInputEditTextConfirmPassword.addTextChangedListener(
                new TextValidator(mTextInputEditTextConfirmPassword));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_account:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        if (validateLogin() && validatePassword() && validateConfirmPassword() &&
                validateSecretNumber() && validatePhoneNumber()) {
            final String login = mTextInputEditTextLogin.getText().toString().trim();
            final String password = mTextInputEditTextPassword.getText().toString().trim();
            final String secretNumber = mTextInputEditTextSecretNumber.getText().toString().trim();
            final String phoneNumber = mTextInputEditTextPhoneNumber.getText().toString().trim();

            final User user = new User("-1",
                    new UserBase("-1", login, null, phoneNumber, secretNumber),
                    new Password("-1", password));

            if (mRadioButtonUser.isChecked()) {
                showProgressBarDialog();
                ApiHelper.registerUser(user, new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), R.string.text_user_created,
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            try {
                                Toast.makeText(RegistrationActivity.this, response.errorBody().string(),
                                        Toast.LENGTH_SHORT).show();
                            } catch (IOException ignored) {
                            }
                        }
                        hideProgressBarDialog();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        hideProgressBarDialog();
                    }
                });
            } else if (mRadioButtonAdmin.isChecked()) {
                showProgressBarDialog();
                ApiHelper.registerAdmin(user, new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), R.string.text_user_created,
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), response.errorBody().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBarDialog();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        hideProgressBarDialog();
                    }
                });
            }
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

    private boolean validateConfirmPassword() {
        final String password = mTextInputEditTextPassword.getText().toString().trim();
        final String confirmPassword = mTextInputEditTextConfirmPassword.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            mTextInputLayoutConfirmPassword.setError(
                    getString(R.string.error_password_does_not_match_the_confirm_password));
            requestFocus(mTextInputEditTextConfirmPassword);
            return false;
        }

        mTextInputLayoutConfirmPassword.setErrorEnabled(false);
        return true;
    }

    private boolean validatePhoneNumber() {
        final String phoneNumber = mTextInputEditTextPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber)) {
            mTextInputLayoutPhoneNumber.setError(getString(R.string.error_phone_number_is_empty));
            requestFocus(mTextInputEditTextPhoneNumber);
            return false;
        }

        if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            mTextInputLayoutPhoneNumber.setError(getString(R.string.error_phone_number_is_incorrect));
            requestFocus(mTextInputEditTextPhoneNumber);
            return false;
        }

        mTextInputLayoutPhoneNumber.setErrorEnabled(false);
        return true;
    }

    private boolean validateSecretNumber() {
        final String secretNumber = mTextInputEditTextSecretNumber.getText().toString().trim();

        if (TextUtils.isEmpty(secretNumber)) {
            mTextInputLayoutSecretNumber.setError(getString(R.string.error_secret_number_is_empty));
            requestFocus(mTextInputEditTextSecretNumber);
            return false;
        }

        mTextInputLayoutSecretNumber.setErrorEnabled(false);
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

                case R.id.edit_text_confirm_password:
                    validateConfirmPassword();
                    break;

                case R.id.edit_text_phone_number:
                    validatePhoneNumber();
                    break;

                case R.id.edit_text_secret_number:
                    validateSecretNumber();
                    break;
            }
        }
    }
}
