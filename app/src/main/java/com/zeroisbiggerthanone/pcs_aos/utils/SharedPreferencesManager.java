package com.zeroisbiggerthanone.pcs_aos.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.zeroisbiggerthanone.pcs_aos.models.AuthenticationMethod;


public class SharedPreferencesManager {

    private interface Constants {
        String FILE_NAME = "pcs";

        String KEY_AUTHENTICATION_METHOD = "AUTHENTICATION_METHOD";
        String KEY_TOKEN = "TOKEN";
    }

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesManager(@NonNull final Context context) {
        mSharedPreferences = context.getSharedPreferences(Constants.FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveAuthenticationMethod(@NonNull final AuthenticationMethod authenticationMethod) {
        final Gson gson = new Gson();

        mEditor.putString(Constants.KEY_AUTHENTICATION_METHOD, gson.toJson(authenticationMethod));
        saveChanges();
    }

    @Nullable
    public AuthenticationMethod getAuthenticationMethod() {
        if (!isAuthenticationMethod()) {
            return null;
        }

        final Gson gson = new Gson();

        return gson.fromJson(mSharedPreferences.getString(Constants.KEY_AUTHENTICATION_METHOD, null),
                AuthenticationMethod.class);
    }

    public void removeAuthenticationMethod() {
        mEditor.remove(Constants.KEY_AUTHENTICATION_METHOD);
        saveChanges();
    }

    public void saveToken(@NonNull final String token) {
        mEditor.putString(Constants.KEY_TOKEN, token);

        saveChanges();
    }

    @Nullable
    public String getToken() {
        return mSharedPreferences.getString(Constants.KEY_TOKEN, null);
    }

    public void removeToken() {
        mEditor.remove(Constants.KEY_TOKEN);

        saveChanges();
    }

    public boolean isToken() {
        return mSharedPreferences.contains(Constants.KEY_TOKEN);
    }

    public boolean isAuthenticationMethod() {
        return mSharedPreferences.contains(Constants.KEY_AUTHENTICATION_METHOD);
    }

    private void saveChanges() {
        mEditor.commit();
    }
}
