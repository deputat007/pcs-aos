package com.zeroisbiggerthanone.pcs_aos.api;


import com.zeroisbiggerthanone.pcs_aos.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface RegistrationService {

    String BASE_URL = "register/";
    String REGISTER_USER = BASE_URL + "user/";
    String REGISTER_ADMIN = BASE_URL + "admin/";

    @POST(REGISTER_USER)
    Call<String> registerUser(@Body User user);

    @POST(REGISTER_ADMIN)
    Call<String> registerAdmin(@Body User user);
}
