package modelForClient;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Register;
import model.Service;

import java.math.BigDecimal;

public class ServiceModel {
    private SimpleIntegerProperty serviceCode = null;
    private SimpleStringProperty workerLogin = null;
    private SimpleStringProperty serviceName = null;
    private SimpleStringProperty moreInfo = null;
    private SimpleDoubleProperty cost = null;
    private SimpleIntegerProperty lasting = null;

    public ServiceModel(){
        serviceCode = new SimpleIntegerProperty();
        workerLogin = new SimpleStringProperty();
        serviceName = new SimpleStringProperty();
        moreInfo = new SimpleStringProperty();
        cost = new SimpleDoubleProperty();
        lasting = new SimpleIntegerProperty();
    }
    public ServiceModel(Service service)
    {
        serviceCode = new SimpleIntegerProperty(service.getServiceCode());
        workerLogin = new SimpleStringProperty(service.getWorkerLogin());
        serviceName = new SimpleStringProperty(service.getServiceName());
        moreInfo = new SimpleStringProperty(service.getMoreInfo());
        cost = new SimpleDoubleProperty(service.getCost());
        lasting = new SimpleIntegerProperty(service.getLasting());
    }

    public SimpleIntegerProperty Code() { return serviceCode; }
    public SimpleStringProperty Wlogin() { return workerLogin; }
    public SimpleStringProperty Servicename() { return serviceName; }
    public SimpleStringProperty Info() { return moreInfo; }
    public SimpleDoubleProperty Cost() { return cost; }
    public SimpleIntegerProperty Lasting() { return lasting; }

}
