package model;

import java.io.Serializable;

public class Workers implements Serializable {
    private String workerLogin;
    private String workerFIO;
    private String phone;
    private String e_mail;
    private String specialty;

    public Workers() {    }
    public Workers(String workerLogin, String workerFIO, String phone, String e_mail, String specialty) {
        this.workerLogin = workerLogin;
        this.workerFIO = workerFIO;
        this.phone = phone;
        this.e_mail = e_mail;
        this.specialty = specialty;
    }

    @Override
    public String toString(){
        return "Сотрудник"; // дописать
    }

    public String getWorkerLogin() {
        return workerLogin;
    }
    public String getWorkerFIO() {
        return workerFIO;
    }
    public String getPhone() {return phone;}
    public String getE_mail() {
        return e_mail;
    }
    public String getSpecialty() {
        return specialty;
    }

    public void setWorkerLogin(String workerLogin) {
        this.workerLogin = workerLogin;
    }
    public void setWorkerFIO(String workerFIO) {
        this.workerFIO = workerFIO;
    }
    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
