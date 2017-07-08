package com.zeroisbiggerthanone.pcs_aos.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.zeroisbiggerthanone.pcs_aos.R;
import com.zeroisbiggerthanone.pcs_aos.app.Constants;
import com.zeroisbiggerthanone.pcs_aos.app.MyApplication;
import com.zeroisbiggerthanone.pcs_aos.utils.ProgressBarDialogFragment;
import com.zeroisbiggerthanone.pcs_aos.utils.SelectAuthenticationMethodDialogFragment;
import com.zeroisbiggerthanone.pcs_aos.utils.SharedPreferencesManager;

/**
 * This Activity has basic methods, which help to find views{@link BaseActivity#findViewById(int)}
 *
 * @author Deputat
 */
public abstract class BaseActivity extends AppCompatActivity {

    private DialogFragment mProgressBarDialog;

    /**
     * This method returns your layout;
     *
     * @return R.layout.your_layout.
     */
    @LayoutRes
    protected abstract int getContentView();

    /**
     * In this method you can find your views.
     */
    protected abstract void initUI();

    /**
     * In this method you can set some listeners or data.
     */
    protected abstract void setUI(final Bundle savedInstanceState);

    /**
     * This method can easy find your view @{@link View}, without casting;
     *
     * @param id  - view id;
     * @param <E> - View type, that will returned;
     * @return your view.
     */
    @SuppressWarnings("unchecked")
    protected <E extends View> E findViewWithId(@IdRes final int id) {
        return (E) findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        setToolbar();
        initUI();
        setUI(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onHomeAsUpClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @return shared preferences helper @{@link SharedPreferencesManager}.
     */
    public SharedPreferencesManager getPreferencesManager() {
        return MyApplication.getApplication().getPrefManager();
    }

    /**
     * This method displays or hides HomeAsUp(<-);
     *
     * @param showHomeAsUp - show HomeAsUp(<-);
     */
    protected void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    /**
     * This method is called when HomeAsUp is clicked.
     */
    protected void onHomeAsUpClicked() {
        onBackPressed();
    }

    /**
     * The method requests focus to the @param view.
     *
     * @param view;
     */
    protected void requestFocus(@NonNull final View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * This method sets Toolbar @{@link Toolbar} if it exists in activity.
     */
    private void setToolbar() {
        final ActionBar actionBar = getSupportActionBar();
        final Toolbar toolbar = findViewWithId(R.id.toolbar);

        if (actionBar == null && toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    /**
     * @return true if progress bar @{@link BaseActivity#mProgressBarDialog} is shown.
     */
    public boolean isProgressBarDialogShown() {
        final DialogFragment fragment = (DialogFragment)
                getSupportFragmentManager().findFragmentByTag(Constants.TAG_DIALOG_PROGRESS_BAR);
        return fragment != null && fragment.getDialog() != null && fragment.getDialog().isShowing();
    }

    /**
     * This method shows progress bar @{@link BaseActivity#mProgressBarDialog} if it is not shown.
     */
    public void showProgressBarDialog() {
        if (!isFinishing() && isProgressBarDialogShown())
            return;

        mProgressBarDialog = ProgressBarDialogFragment.newInstance();
        mProgressBarDialog.setCancelable(false);

        mProgressBarDialog.show(getSupportFragmentManager(), Constants.TAG_DIALOG_PROGRESS_BAR);
    }

    /**
     * This method hides progress bar @{@link BaseActivity#mProgressBarDialog} if it is shown.
     */
    public void hideProgressBarDialog() {
        if (isProgressBarDialogShown()) {
            mProgressBarDialog.dismiss();
        }
    }

    public void showAuthenticationMethodDialogFragment() {
        final DialogFragment dialogFragment = SelectAuthenticationMethodDialogFragment.newInstance();

        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(), Constants.TAG_AUTHENTICATION_METHOD_DIALOG);
    }
}
