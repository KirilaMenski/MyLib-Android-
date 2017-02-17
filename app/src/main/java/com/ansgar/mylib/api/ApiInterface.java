package com.ansgar.mylib.api;

import com.ansgar.mylib.database.entity.User;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kirill on 6.2.17.
 */

public interface ApiInterface {

    @POST("api/users")
    void getUsers();

    @POST("api/login")
    void login();

    @GET("api/{userId}/synchronize")
    Observable<User> synchronizeData(@Path("userId") int userId);

}
