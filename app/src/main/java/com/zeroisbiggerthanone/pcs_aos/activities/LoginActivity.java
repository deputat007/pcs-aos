package com.zeroisbiggerthanone.pcs_aos.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.zeroisbiggerthanone.pcs_aos.R;
import com.zeroisbiggerthanone.pcs_aos.app.MyApplication;
import com.zeroisbiggerthanone.pcs_aos.fragments.AdminFragment;
import com.zeroisbiggerthanone.pcs_aos.fragments.DefaultFragment;
import com.zeroisbiggerthanone.pcs_aos.fragments.UserFragment;
import com.zeroisbiggerthanone.pcs_aos.fragments.UserPhoneNumberFragment;
import com.zeroisbiggerthanone.pcs_aos.fragments.UserSecretDigitFragment;
import com.zeroisbiggerthanone.pcs_aos.models.AuthenticationMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class LoginActivity extends BaseActivity {

    public static Intent getIntent(@NonNull final Context packageContext,
                                   @Nullable final Bundle bundle) {
        final Intent intent = new Intent(packageContext, LoginActivity.class);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initUI() {
    }

    @Override
    protected void setUI(Bundle savedInstanceState) {
        if (MyApplication.getApplication().getPrefManager().isToken()) {
            startActivity(MainActivity.getIntent(this, null));
            finish();
        }
        if (getPreferencesManager().isAuthenticationMethod()) {
            setFragment(getPreferencesManager().getAuthenticationMethod());
        } else {
            setDefaultFragment();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_authentication_settings:
                showAuthenticationMethodDialogFragment();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onEvent(AuthenticationMethodEvent event) {
        setFragment(event.mAuthenticationMethod);
    }

    private void setFragment(AuthenticationMethod authenticationMethod) {
        Fragment fragment = null;

        switch (authenticationMethod) {
            case ADMIN:
                fragment = AdminFragment.getInstance(null);
                break;

            case USER:
                fragment = UserFragment.getInstance(null);
                break;

            case USER_PHONE_NUMBER:
                fragment = UserPhoneNumberFragment.getInstance(null);
                break;

            case USER_SECRET_DIGIT:
                fragment = UserSecretDigitFragment.getInstance(null);
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void setDefaultFragment() {
        final Fragment fragment = DefaultFragment.getInstance(null);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public static class AuthenticationMethodEvent {
        private AuthenticationMethod mAuthenticationMethod;

        public AuthenticationMethodEvent(AuthenticationMethod authenticationMethod) {
            mAuthenticationMethod = authenticationMethod;
        }
    }
}
