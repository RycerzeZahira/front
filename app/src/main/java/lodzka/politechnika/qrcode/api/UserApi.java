package lodzka.politechnika.qrcode.api;


import lodzka.politechnika.qrcode.api.payload.AuthenticationRequest;
import lodzka.politechnika.qrcode.api.payload.JwtAuthenticationResponse;
import lodzka.politechnika.qrcode.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/registration")
    Call<Void> registerUser(@Body User user);

    @POST("/user/login")
    Call<JwtAuthenticationResponse> loginUser(@Body AuthenticationRequest loginRequest);

    @GET("/user/profile")
    Call<User> getUserProfile();

    @POST("user/changePassword")
    Call<Void> changePassword(@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);
}
