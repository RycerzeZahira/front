package lodzka.politechnika.qrcode.api;


import lodzka.politechnika.qrcode.client.RetrofitClient;

public class ApiUtils {
    private ApiUtils() {
    }

    private static final String BASE_URL = "https://api-qr-code.herokuapp.com/";
    private static String token;

    public static UserApi getAUserApi() {
        return RetrofitClient.getClient(BASE_URL).create(UserApi.class);
    }

    public static GroupApi getGroupApi() {
        return RetrofitClient.getClient(BASE_URL).create(GroupApi.class);
    }

    public static FormApi getFormApi() {
        return RetrofitClient.getClient(BASE_URL).create(FormApi.class);
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ApiUtils.token = token;
    }
}
