package lodzka.politechnika.qrcode.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by Bartek on 2018-10-29.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {

    @SerializedName("moderator")
    private User moderator;

    @SerializedName("users")
    private Set<User> users;

    @SerializedName("expiredDate")
    @Expose
    private String expiredDate;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("publicGroup")
    @Expose
    private Boolean publicGroup;

    @SerializedName("root")
    private Root root;


    public Group(String name, String code, Boolean publicGroup) {
        this.name = name;
        this.code = code;
        this.publicGroup = publicGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getPublicGroup() {
        return publicGroup;
    }

    public void setPublicGroup(Boolean publicGroup) {
        this.publicGroup = publicGroup;
    }

    public User getModerator() {
        return moderator;
    }

    public Set<User> getUsers() {
        return users;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public Root getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return name;
    }
}
