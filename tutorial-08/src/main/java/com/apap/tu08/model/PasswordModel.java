package com.apap.tu08.model;

public class PasswordModel {
    private String newPass;
    private String oldPass;
    private String confirmPassword;

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPass(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
