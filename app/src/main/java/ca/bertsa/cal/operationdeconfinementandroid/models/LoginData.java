package ca.bertsa.cal.operationdeconfinementandroid.models;

import java.io.Serializable;

@SuppressWarnings("unused")
public class LoginData implements Serializable {
    private final String email;
    private final String password;

    public LoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}