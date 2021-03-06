package lodzka.politechnika.qrcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bartek on 2018-10-30.
 */

public class Form {

    @SerializedName("groupCode")
    @Expose
    private String groupCode;

    @SerializedName("expiredDate")
    @Expose
    private String expiredDate;

    @SerializedName("root")
    @Expose
    private Root root;

    @SerializedName("group")
    private Group group;

    @SerializedName("code")
    private String code;


    public Form() {
    }


    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public Group getGroup() {
        return group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
