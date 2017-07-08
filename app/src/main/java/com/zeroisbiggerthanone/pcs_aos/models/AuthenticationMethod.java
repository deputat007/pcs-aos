package com.zeroisbiggerthanone.pcs_aos.models;


import android.support.annotation.StringRes;

import com.zeroisbiggerthanone.pcs_aos.R;


public enum AuthenticationMethod {

    ADMIN(R.string.title_admin),
    USER(R.string.title_user),
    USER_PHONE_NUMBER(R.string.title_user_phone_number),
    USER_SECRET_DIGIT(R.string.title_user_secret_digit);

    @StringRes
    int mName;

    AuthenticationMethod(@StringRes int name) {
        mName = name;
    }

    @StringRes
    public int getName() {
        return mName;
    }
}
