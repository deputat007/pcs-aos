package com.zeroisbiggerthanone.pcs_aos.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.zeroisbiggerthanone.pcs_aos.R;
import com.zeroisbiggerthanone.pcs_aos.api.ApiHelper;
import com.zeroisbiggerthanone.pcs_aos.api.UserUnauthorizedException;
import com.zeroisbiggerthanone.pcs_aos.app.MyApplication;
import com.zeroisbiggerthanone.pcs_aos.models.UserBase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {

    private TextView mTextViewLogin;

    public static Intent getIntent(@NonNull final Context packageContext,
                                   @Nullable final Bundle bundle) {
        final Intent intent = new Intent(packageContext, MainActivity.class);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        mTextViewLogin = findViewWithId(R.id.tv_user_login);
    }

    @Override
    protected void setUI(Bundle savedInstanceState) {
//        setUserLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                MyApplication.getApplication().getPrefManager().removeToken();
                startActivity(LoginActivity.getIntent(getApplicationContext(), null));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUserLogin() {
        try {
            ApiHelper.getCurrentUser(new Callback<UserBase>() {
                @Override
                public void onResponse(Call<UserBase> call, Response<UserBase> response) {
                    if (response.isSuccessful()) {
                        mTextViewLogin.setText(response.body().getLogin());
                    } else {
                        Toast.makeText(getApplicationContext(), response.errorBody().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserBase> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (UserUnauthorizedException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
