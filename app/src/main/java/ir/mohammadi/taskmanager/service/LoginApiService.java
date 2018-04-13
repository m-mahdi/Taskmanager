package ir.mohammadi.taskmanager.service;

import ir.mohammadi.taskmanager.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginApiService {
        @POST("/auth/login")
//    Call<LoginResponse> login(@Header("Authorization") String authorization);
    Call<LoginResponse> login(@Header("X-Backtory-Authentication-Id") String authorization);
}
