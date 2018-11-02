package lodzka.politechnika.qrcode.client;

import android.content.Context;

import java.io.IOException;
import java.util.Locale;

import lodzka.politechnika.qrcode.api.ApiUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    synchronized public static Retrofit getClient(String baseUrl) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().client(new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {

                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder builder = chain.request().newBuilder();
                            if (ApiUtils.getToken() != null)
                                builder.addHeader("Authorization", "Bearer " + ApiUtils.getToken());
                            builder.addHeader("Accept-Language", Locale.getDefault().getDisplayLanguage());
                            Request request = builder.build();
                            Response response = chain.proceed(request);

                            return response;
                        }
                    }).build()).baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
