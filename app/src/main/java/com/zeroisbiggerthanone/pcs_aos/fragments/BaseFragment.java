package com.zeroisbiggerthanone.pcs_aos.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.zeroisbiggerthanone.pcs_aos.activities.BaseActivity;


public abstract class BaseFragment extends Fragment {

    private View mRootView;

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initUI();

    protected abstract void setUI(@Nullable final Bundle savedInstanceState);

    @StringRes
    protected abstract int getTitle();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;

        initUI();
        changeTitle();
        setUI(savedInstanceState);
    }

    protected void setTitle(@StringRes int title) {
        getParentActivity().setTitle(title);
    }

    @SuppressWarnings("unused")
    protected View getRootView() {
        return mRootView;
    }

    protected BaseActivity getParentActivity() {
        return ((BaseActivity) getActivity());
    }

    /**
     * The method requests focus to the @param view.
     *
     * @param view;
     */
    protected void requestFocus(@NonNull final View view) {
        if (view.requestFocus()) {
            getParentActivity()
                    .getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    protected void clearEditTexts(@NonNull final EditText... editTexts) {
        for (final EditText editText :
                editTexts) {
            editText.setText("");
        }
    }

    @SuppressWarnings("unchecked")
    public <E extends View> E findViewById(@IdRes final int id) {
        return (E) mRootView.findViewById(id);
    }

    private void changeTitle() {
        if (getTitle() != -1) {
            setTitle(getTitle());
        }
    }
}
