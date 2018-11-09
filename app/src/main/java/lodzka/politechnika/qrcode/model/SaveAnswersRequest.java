package lodzka.politechnika.qrcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveAnswersRequest {

    @SerializedName("formCode")
    @Expose
    private String formCode;

    @SerializedName("root")
    @Expose
    private Root root;

    public SaveAnswersRequest(String formCode, Root root) {
        this.formCode = formCode;
        this.root = root;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
