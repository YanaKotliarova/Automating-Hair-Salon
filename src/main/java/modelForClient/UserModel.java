package modelForClient;

import javafx.beans.property.SimpleStringProperty;
import model.Users;

public class UserModel {

    private SimpleStringProperty login = null;
    private SimpleStringProperty password = null;
    private SimpleStringProperty role = null;

    public UserModel(){
        login = new SimpleStringProperty();
        password = new SimpleStringProperty();
        role = new SimpleStringProperty();
    }
    public UserModel(Users user)
    {
        login = new SimpleStringProperty(user.getLogin());
        password = new SimpleStringProperty(user.getPassword());
        role = new SimpleStringProperty(user.getRole());
    }

    public SimpleStringProperty Login() { return login; }
    public SimpleStringProperty Password() { return password; }
    public SimpleStringProperty Role() { return role; }
}
