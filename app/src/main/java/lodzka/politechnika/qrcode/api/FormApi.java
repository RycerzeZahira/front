package lodzka.politechnika.qrcode.api;

import lodzka.politechnika.qrcode.model.QRCodeJsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Bartek on 2018-10-30.
 */

public interface FormApi {

    @POST("/form/create")
    Call<Void> createForm(@Body QRCodeJsonObject qrCodeJsonObject);

}
