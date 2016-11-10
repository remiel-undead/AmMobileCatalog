package com.example.undead.ammobilecatalog.bus;

public class LoginEvent {
    public final String login;
    public final String passwordEncrypted;

    public LoginEvent(String login, String passwordEncrypted) {
        this.login = login;
        this.passwordEncrypted = passwordEncrypted;
    }
}
