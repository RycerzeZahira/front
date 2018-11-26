package lodzka.politechnika.qrcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bartek on 2018-11-26.
 */

public class Email implements Serializable {

    @SerializedName("email")
    @Expose
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
