package lodzka.politechnika.qrcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bartek on 2018-10-29.
 */

public class Group {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("publicGroup")
    @Expose
    private Boolean publicGroup;


    public Group(String name, Boolean publicGroup) {
        this.name = name;
        this.publicGroup = publicGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPublicGroup() {
        return publicGroup;
    }

    public void setPublicGroup(Boolean publicGroup) {
        this.publicGroup = publicGroup;
    }
}
