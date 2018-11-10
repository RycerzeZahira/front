package lodzka.politechnika.qrcode.api;

import java.util.ArrayList;

import lodzka.politechnika.qrcode.model.Group;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Bartek on 2018-10-29.
 */

public interface GroupApi {

    @POST("/group/create")
    Call<Void> createGroup(@Body Group group);

    @GET("/group/")
    Call<ArrayList<Group>> getMyGroupsList();

    @DELETE("/group/{code}")
    Call<Void> deleteGroup(@Path("code") String code);
}
