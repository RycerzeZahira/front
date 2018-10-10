package lodzka.politechnika.qrcode.api;


import lodzka.politechnika.qrcode.client.RetrofitClient;

public class ApiUtils {
    private ApiUtils() {
    }

    public static final String BASE_URL = "https://api-qr-code.herokuapp.com/";

    public static UserApi getAUserApi() {
        return RetrofitClient.getClient(BASE_URL).create(UserApi.class);
    }
}
