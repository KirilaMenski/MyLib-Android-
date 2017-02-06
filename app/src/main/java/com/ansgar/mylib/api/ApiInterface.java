package com.ansgar.mylib.api;

import retrofit2.http.POST;

/**
 * Created by kirill on 6.2.17.
 */

public interface ApiInterface {

    @POST("api/users")
    void getUsers();

    @POST("api/login")
    void login();

    @POST("api/synchronize")
    void synchronizeData();

}
