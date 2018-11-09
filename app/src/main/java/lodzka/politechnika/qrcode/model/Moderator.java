package lodzka.politechnika.qrcode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Moderator {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("isLocked")
    @Expose
    private boolean isLocked;

    public Moderator() {
    }

    public Moderator(String email, boolean isLocked) {
        this.email = email;
        this.isLocked = isLocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
