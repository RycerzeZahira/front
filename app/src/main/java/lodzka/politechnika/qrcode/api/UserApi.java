package lodzka.politechnika.qrcode.api;


import lodzka.politechnika.qrcode.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/registration")
    @FormUrlEncoded
    Call<User> registerUser(@Body User user);
}
