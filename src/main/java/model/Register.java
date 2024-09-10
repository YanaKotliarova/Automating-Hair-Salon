package model;

import java.io.Serializable;


public class Register implements Serializable {
    private int registerNumber;
    private String clientLogin;
    private int serviceCode;
    private String workerLogin;
    private String date;
    private String time;
    public Register() { }
    public Register(String clientLogin, int serviceCode, String workerLogin, String date, String time) {
        this.clientLogin = clientLogin;
        this.serviceCode = serviceCode;
        this.workerLogin=workerLogin;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString(){
        return "Запись"; // дописать
    }

    public int getRegisterNumber(){return registerNumber;}
    public int getServiceCode() {
        return serviceCode;
    }
    public String getClientLogin() {
        return clientLogin;
    }
    public String getWorkerLogin() {
        return workerLogin;
    }
    public String getDate(){return date;}
    public String getTime(){return time;}

    public void setRegisterNumber(int registerNumber){this.registerNumber=registerNumber;}
    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }
    public void setClientLogin(String clientLogin) {this.clientLogin=clientLogin;}
    public void setWorkerLogin(String workerLogin) {this.workerLogin=workerLogin;}
    public void setDate(String date){this.date=date;}
    public void setTime(String time){this.time=time;}
}
