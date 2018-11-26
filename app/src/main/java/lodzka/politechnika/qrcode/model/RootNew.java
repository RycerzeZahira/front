package lodzka.politechnika.qrcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartek on 2018-11-26.
 */

public class RootNew implements Serializable{

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("element")
    @Expose
    private List<Element> element;

    public RootNew() {
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

    public List<Element> getElement() {
        return element;
    }

    public void setElement(List<Element> element) {
        this.element = element;
    }
}
