package model;

import java.io.Serializable;
import java.util.List;

public class Clients implements Serializable {
    private String clientLogin;
    private String clientFIO;
    private String phone;
    private String clientCardNumber;
    private int discount;

    public Clients() {
    }

    public Clients(String clientLogin, String clientFIO, String phone, String clientCardNumber) {
        this.clientLogin = clientLogin;
        this.clientFIO = clientFIO;
        this.phone = phone;
        this.clientCardNumber = clientCardNumber;
    }

    public void toString(List<Clients> clientsList) {
        if (clientsList.size() != 0) {
            System.out.format("%10s%20s%20s%20s%20s", "Логин |", "ФИО |", "Телефон |", "Номер карты клиента ", "Скидка (%)");
            for (Clients c: clientsList) {
                System.out.println(" ");
                System.out.format("%10s%20s%20s%20s%20s", c.getClientLogin() + " |", c.getClientFIO() + " |", c.getPhone(),
                        c.getClientCardNumber() + " |", c.getDiscount());
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет пользователей!");
        }
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public String getClientFIO() {
        return clientFIO;
    }

    public String getClientCardNumber() {
        return clientCardNumber;
    }

    public String getPhone() {
        return phone;
    }

    public int getDiscount() {
        return discount;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public void setClientFIO(String clientFIO) {
        this.clientFIO = clientFIO;
    }

    public void setClientCardNumber(String clientCardNumber) {
        this.clientCardNumber = clientCardNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
