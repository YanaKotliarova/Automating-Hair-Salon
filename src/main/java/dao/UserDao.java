package dao;

import model.Clients;
import model.Service;
import model.Users;
import model.Workers;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public UserDao() {
    }

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int state;

    Clients client = new Clients();
    Workers worker = new Workers();
    ClientDao clientDao = new ClientDao();
    WorkerDao workerDao = new WorkerDao();

    public void addUser(Users user, Object obj) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "INSERT INTO user(login, password, role) " + "VALUES (?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            state = ps.executeUpdate();
            System.out.println("Добавленный пользователь " + state);
            if (user.getRole() == "администратор" || user.getRole() == "специалист") {
                workerDao.addWorker((Workers) obj);
            } else if (user.getRole() == "клиент") {
                clientDao.addClient((Clients) obj);
            } else System.out.println("Неверная роль пользователя");

        } catch (SQLException e) {
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

    public void showUsers() {
        List<Users> usersList = getUsers();
        if (usersList.size() != 0) {
            System.out.format("%10s%20s%20s", "Логин |", "Пароль |", "Роль");
            for (Users u : usersList) {
                System.out.println(" ");
                System.out.format("%10s%20s%20s", u.getLogin() + " |", u.getPassword() + " |", u.getRole());
            }
            System.out.println(" ");
        } else {
            System.out.println("Нет пользователей!");
        }
    }

    public List<Users> getUsers() {
        List<Users> usersList = new ArrayList<Users>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM user";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }
    public void updateUserPassword(Users user) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE user SET password=?, role=?" + "WHERE login = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getLogin());
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
    public void deleteUser(String login) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "DELETE FROM user WHERE login=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, login);
            state = ps.executeUpdate();
            System.out.println("Удалён пользователь " + state);
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
        List<Users> usersList = getUsers();
        try (FileWriter writer = new FileWriter("Users.txt", false)) {
            String text = "Логин | Роль \n";
            for (Users u : usersList) {
                text += u.getLogin() + " |" + u.getRole() + "\n";
            }
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}