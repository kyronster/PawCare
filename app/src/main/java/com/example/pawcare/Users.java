package com.example.pawcare;

public class Users {
    private String name;
    private String phone;
    private String email;
    private String pass;

    private String confirmPass;
    private boolean isChecked;



    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String name, String phone, String email, String pass, String confirmPass0, boolean isChecked) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
        this.isChecked = isChecked;
        this.confirmPass = confirmPass0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = pass;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }





}