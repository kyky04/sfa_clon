package com.pertaminalubricants.mysfa.model;

/**
 * Created by nunu on 10/30/2016.
 */

public class Contributor {
    public String login;
    public int contributions;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }
}
