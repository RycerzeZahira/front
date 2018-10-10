package lodzka.politechnika.qrcode.api;


import lodzka.politechnika.qrcode.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/registration")
    Call<Void> registerUser(@Body User user);
}
