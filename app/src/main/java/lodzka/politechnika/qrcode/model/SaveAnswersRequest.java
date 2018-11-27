package lodzka.politechnika.qrcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveAnswersRequest implements Serializable {

    @SerializedName("formCode")
    @Expose
    private String formCode;

    @SerializedName("root")
    @Expose
    private RootNew root;

    public SaveAnswersRequest(String formCode, RootNew root) {
        this.formCode = formCode;
        this.root = root;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public RootNew getRoot() {
        return root;
    }

    public void setRoot(RootNew root) {
        this.root = root;
    }
}
