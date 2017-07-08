package com.zeroisbiggerthanone.pcs_aos.api;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zeroisbiggerthanone.pcs_aos.BuildConfig;
import com.zeroisbiggerthanone.pcs_aos.app.MyApplication;
import com.zeroisbiggerthanone.pcs_aos.models.User;
import com.zeroisbiggerthanone.pcs_aos.models.UserBase;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiHelper {

    private static final String BASE_URL = "http://10.10.10.247:8080/";

    private static final Retrofit sRetrofit;
    private static final RegistrationService sRegistrationService;
    private static final LoginService sLoginService;
    private static final UserService sUserService;

    static {
        final OkHttpClient.Builder okClient = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okClient.addInterceptor(logging);
        }

        final Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        sRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okClient.build())
                .build();

        sRegistrationService = sRetrofit.create(RegistrationService.class);
        sLoginService = sRetrofit.create(LoginService.class);
        sUserService = sRetrofit.create(UserService.class);
    }

    public static void registerUser(@NonNull final User user,
                                    @NonNull final Callback<String> callback) {
        sRegistrationService.registerUser(user).enqueue(callback);
    }

    public static void registerAdmin(@NonNull final User user,
                                     @NonNull final Callback<String> callback) {
        sRegistrationService.registerAdmin(user).enqueue(callback);
    }

    public static void loginUser(@NonNull final String login,
                                 @NonNull final String password,
                                 @NonNull final Callback<String> callback) {
        sLoginService.getUserToken(login, password).enqueue(callback);
    }

    public static void loginAdmin(@NonNull final String login,
                                  @NonNull final String password,
                                  @NonNull final Callback<String> callback) {
        sLoginService.getAdminToken(login, password).enqueue(callback);
    }

    public static void sendCode(@NonNull final String login,
                                @NonNull final Callback<Void> callback) {
        sLoginService.sendSms(login).enqueue(callback);
    }

    public static void loginPhoneNumber(@NonNull final String login,
                                        @NonNull final String code,
                                        @NonNull final Callback<String> callback) {
        sLoginService.getUserTokenByCode(login, code).enqueue(callback);
    }

    public static void loginSecretNumber(@NonNull final String login,
                                         final int number,
                                         final int sum,
                                         @NonNull final Callback<String> callback) {
        sLoginService.getUserTokenByDigit(login, number, sum).enqueue(callback);
    }

    public static void generateNumber(@NonNull final Callback<Integer> callback) {
        sLoginService.generateNumber().enqueue(callback);
    }

    public static void getCurrentUser(@NonNull final Callback<UserBase> callback)
            throws UserUnauthorizedException {
        final String token = MyApplication.getApplication().getPrefManager().getToken();

        if (token != null) {
            sUserService.getCurrentUser(token).enqueue(callback);
        } else {
            throw new UserUnauthorizedException("user is unauthorized");
        }
    }
}
