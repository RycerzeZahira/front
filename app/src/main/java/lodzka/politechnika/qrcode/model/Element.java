package lodzka.politechnika.qrcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bartek on 2018-11-26.
 */

public class Element implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("element")
    @Expose
    private Serializable element;

    public Element(String type, String code, Serializable element) {
        this.type = type;
        this.code = code;
        this.element = element;
    }

    public Element() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Serializable getElement() {
        return element;
    }

    public void setElement(Serializable element) {
        this.element = element;
    }
}
