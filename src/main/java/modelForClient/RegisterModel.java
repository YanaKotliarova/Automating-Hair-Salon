package modelForClient;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Register;

import java.sql.Date;
import java.sql.Time;

public class RegisterModel {

    private SimpleIntegerProperty registerNumber;
    private SimpleStringProperty clientLogin;
    private SimpleIntegerProperty serviceCode;
    private SimpleStringProperty workerLogin;
    private SimpleStringProperty date;
    private SimpleStringProperty time;

    public RegisterModel(){
        registerNumber = new SimpleIntegerProperty();
        clientLogin = new SimpleStringProperty();
        serviceCode = new SimpleIntegerProperty();
        workerLogin = new SimpleStringProperty();
        date = new SimpleStringProperty();
        time = new SimpleStringProperty();
    }
    public RegisterModel(Register register)
    {
        registerNumber = new SimpleIntegerProperty(register.getRegisterNumber());
        clientLogin = new SimpleStringProperty(register.getClientLogin());
        serviceCode = new SimpleIntegerProperty(register.getServiceCode());
        workerLogin = new SimpleStringProperty(register.getWorkerLogin());
        date = new SimpleStringProperty(register.getDate());
        time = new SimpleStringProperty(register.getTime());
    }

    public SimpleIntegerProperty RegNum() {return registerNumber;}
    public SimpleStringProperty ClientLogin() { return clientLogin; }
    public SimpleIntegerProperty Code() { return serviceCode; }
    public SimpleStringProperty WorkerLogin() { return workerLogin; }
    public SimpleStringProperty Date() { return date; }
    public SimpleStringProperty Time() { return time; }
}
