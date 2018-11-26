package lodzka.politechnika.qrcode.api;

import lodzka.politechnika.qrcode.model.Form;
import lodzka.politechnika.qrcode.model.SaveAnswersRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Bartek on 2018-10-30.
 */

public interface FormApi {

    @POST("/form/create")
    Call<Void> createForm(@Body Form form);

    @GET("/form/group/{code}")
    Call<Form[]> getFormsInGroup(@Path("code") String code);

    @POST("/form/save")
    Call<Void> saveAnswer(@Body SaveAnswersRequest request);

    @POST("/mail/{formcode}")
    Call<Void> generateCSV(@Path("formcode") String code);

    @GET("/form")
    Call<Form[]> getAllForms();
}
