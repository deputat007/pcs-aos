package com.zeroisbiggerthanone.pcs_aos.api;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


interface LoginService {

    String BASE_URL = "login/";
    String ADMIN = BASE_URL + "admin";
    String USER = BASE_URL + "user";
    String USER_DIGIT = BASE_URL + "user_by_digit";
    String USER_SMS = BASE_URL + "user_by_sms";
    String GENERATE_NUMBER = BASE_URL + "generate_number";

    String SEND_SMS = BASE_URL + "send_sms_code";

    @POST(USER)
    Call<String> getUserToken(@Query("login") String login,
                              @Query("password") String password);

    @POST(ADMIN)
    Call<String> getAdminToken(@Query("login") String login,
                               @Query("password") String password);

    @POST(USER_DIGIT)
    Call<String> getUserTokenByDigit(@Query("login") String login,
                                     @Query("number") int number,
                                     @Query("sum") int sum);

    @POST(USER_SMS)
    Call<String> getUserTokenByCode(@Query("login") String login,
                                    @Query("code") String code);

    @POST(SEND_SMS)
    Call<Void> sendSms(@Query("login") String login);

    @POST(GENERATE_NUMBER)
    Call<Integer> generateNumber();
}
