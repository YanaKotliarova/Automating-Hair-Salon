package model;

import java.io.Serializable;

public class Users implements Serializable {
    private String login;
    private String password;
    private String role;

    public Users() {}
    public Users(String login, String password, String role){
        this.login=login;
        this.password=password;
        this.role=role;
    }

    @Override
    public String toString(){
        return "Пользователь " + login; // дописать
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return this.role;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) { this.role = role; }
}
