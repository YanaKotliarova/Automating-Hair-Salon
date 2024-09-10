package modelForClient;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Clients;

public class ClientModel {
    private SimpleStringProperty clientLogin = null;
    private SimpleStringProperty clientFIO = null;
    private SimpleStringProperty phone = null;
    private SimpleStringProperty clientCardNumber = null;
    private SimpleIntegerProperty discount = null;

    public ClientModel(){
        clientLogin = new SimpleStringProperty();
        clientFIO = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        clientCardNumber = new SimpleStringProperty();
        discount = new SimpleIntegerProperty();
    }
    public ClientModel(Clients client)
    {
        clientLogin = new SimpleStringProperty(client.getClientLogin());
        clientFIO = new SimpleStringProperty(client.getClientFIO());
        phone = new SimpleStringProperty(client.getPhone());
        clientCardNumber = new SimpleStringProperty(client.getClientCardNumber());
        discount = new SimpleIntegerProperty(client.getDiscount());
    }

    public SimpleStringProperty Login() { return clientLogin; }
    public SimpleStringProperty FIO() { return clientFIO; }
    public SimpleStringProperty Phone() { return phone; }
    public SimpleStringProperty Card() { return clientCardNumber; }
    public SimpleIntegerProperty Discount() { return discount; }
}
