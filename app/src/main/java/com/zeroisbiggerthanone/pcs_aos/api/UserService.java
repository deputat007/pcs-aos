package com.zeroisbiggerthanone.pcs_aos.api;


import com.zeroisbiggerthanone.pcs_aos.models.UserBase;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

interface UserService {

    String BASE_URL = "user/";
    String CURRENT_USER = BASE_URL + "current";

    @GET(CURRENT_USER)
    Call<UserBase> getCurrentUser(@Header(ApiHeaders.TOKEN_HEADER) String token);
}
