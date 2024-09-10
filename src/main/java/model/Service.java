package model;

import java.io.Serializable;

public class Service implements Serializable {
    private int serviceCode;
    private String workerLogin;
    private String serviceName;
    private String moreInfo;
    private double cost;
    private int lasting;

    public Service() {}
    public Service(String workerLogin, String serviceName, String moreInfo, double cost, int lasting) {
        this.workerLogin = workerLogin;
        this.serviceName = serviceName;
        this.moreInfo = moreInfo;
        this.cost = cost;
        this.lasting = lasting;
    }

    @Override
    public String toString(){
        return "Услуга"; // дописать
    }

    public int getServiceCode() {
        return serviceCode;
    }
    public String getWorkerLogin() {
        return workerLogin;
    }
    public String getServiceName() {
        return serviceName;
    }
    public String getMoreInfo() {return moreInfo;}
    public double getCost() {
        return cost;
    }
    public int getLasting() {
        return lasting;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }
    public void setWorkerLogin(String workerLogin) {
        this.workerLogin = workerLogin;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public void setLasting(int lasting) {
        this.lasting = lasting;
    }

}
