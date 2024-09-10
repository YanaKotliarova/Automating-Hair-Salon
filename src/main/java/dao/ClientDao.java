package dao;

import model.Clients;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int state;

    public void addClient(Clients client){
        con = ConnectionFactory.getConnection();
        try {
            String query = "INSERT INTO client(clientLogin, clientFIO, phone, clientCardNumber) " + "VALUES (?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, client.getClientLogin());
            ps.setString(2, client.getClientFIO());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getClientCardNumber());
            state = ps.executeUpdate();
            System.out.println("Добавленный клиент " + state);
        }catch (SQLException e) {
            state = -1;
            e.printStackTrace();
        } catch (Exception e) {
            state = -2;
            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        }
    }

    public List<Clients> getClients() {
        List<Clients> clientsList = new ArrayList<Clients>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM client";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Clients client = new Clients();
                client.setClientLogin(rs.getString("clientLogin"));
                client.setClientFIO(rs.getString("clientFIO"));
                client.setPhone(rs.getString("phone"));
                client.setClientCardNumber(rs.getString("clientCardNumber"));
                client.setDiscount(rs.getInt("discount"));
                clientsList.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientsList;
    }
    public Clients getClient(String clientLogin) {
        Clients client = new Clients();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM client WHERE clientLogin = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, clientLogin);
            rs = ps.executeQuery();
            while (rs.next()) {
                client.setClientLogin(rs.getString("clientLogin"));
                client.setClientFIO(rs.getString("clientFIO"));
                client.setPhone(rs.getString("phone"));
                client.setClientCardNumber(rs.getString("clientCardNumber"));
                client.setDiscount(rs.getInt("discount"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }
    public void showClients(List<Clients> clientsList) {
        if (clientsList.size() != 0) {
            System.out.format("%10s%20s%20s", "Логин |", "ФИО |", "Телефон |", "Номер карты клиента ", "Скидка (%)");
            for (Clients c: clientsList) {
                System.out.println(" ");
                System.out.format("%10s%20s%20s", c.getClientLogin() + " |", c.getClientFIO() + " |", c.getPhone(),
                        c.getClientCardNumber() + " |", c.getDiscount());
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет пользователей!");
        }
    }
    public void updateClientInfo(Clients client) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE client SET clientFIO =?, phone =?, clientCardNumber=?" + "WHERE clientLogin = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, client.getClientFIO());
            ps.setString(2, client.getPhone());
            ps.setString(3, client.getClientCardNumber());
            ps.setString(4, client.getClientLogin());
            state = ps.executeUpdate();
            System.out.println("Изменен пользователь " + state);
        } catch (Exception e) {
            state = -2;
            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        }
    }
    public void intoFile() {
        List<Clients> clientsList = getClients();
        try (FileWriter writer = new FileWriter("Clients.txt", false)) {
            String text = "Логин клиента | ФИО клиента | Телефон | Номер карты клиента | Скидка\n";
            for (Clients c : clientsList) {
                text += c.getClientLogin() + " |" + c.getClientFIO() + " |" + c.getPhone() +
                        " |" + c.getPhone() + " |" + c.getDiscount() + "\n";
            }
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addDiscount(String clientLogin, int discount){
        Clients client = new Clients();
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE client SET discount =? WHERE clientLogin = ? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, discount);
            ps.setString(2, clientLogin);
            state = ps.executeUpdate();
            System.out.println("Изменен пользователь " + state);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
