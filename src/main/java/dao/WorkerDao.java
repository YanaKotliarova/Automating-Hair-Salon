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

public class WorkerDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int state;

    public void addWorker(Workers worker){
        con = ConnectionFactory.getConnection();
        try {
            String query = "INSERT INTO worker(workerLogin, workerFIO, phone, `e-mail`, specialty) " + "VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, worker.getWorkerLogin());
            ps.setString(2, worker.getWorkerFIO());
            ps.setString(3, worker.getPhone());
            ps.setString(4, worker.getE_mail());
            ps.setString(5, worker.getSpecialty());
            state = ps.executeUpdate();
            System.out.println("Добавленный специалист " + state);
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
    public List<Workers> getWorkers() {
        List<Workers> workersList = new ArrayList<Workers>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM worker";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Workers worker = new Workers();
                worker.setWorkerLogin(rs.getString("workerLogin"));
                worker.setWorkerFIO(rs.getString("workerFIO"));
                worker.setPhone(rs.getString("phone"));
                worker.setE_mail(rs.getString("e-mail"));
                worker.setSpecialty(rs.getString("specialty"));
                workersList.add(worker);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return workersList;
    }
    public Workers getWorker(String workerLogin) {
        Workers worker = new Workers();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM worker WHERE workerLogin = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, workerLogin);
            rs = ps.executeQuery();
            while (rs.next()) {
                worker.setWorkerLogin(rs.getString("workerLogin"));
                worker.setWorkerFIO(rs.getString("workerFIO"));
                worker.setPhone(rs.getString("phone"));
                worker.setE_mail(rs.getString("e-mail"));
                worker.setSpecialty(rs.getString("specialty"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return worker;
    }
    public void showWokers() {
        List<Workers> workersList = getWorkers();
        if (workersList.size() != 0) {
            System.out.format("%10s%20s%20s%20s%20s", "Логин |", "ФИО |", "Телефон |", "Почта ", "Специализация");
            for (Workers w: workersList) {
                System.out.println(" ");
                System.out.format("%10s%20s%20s%20s%20s", w.getWorkerLogin() + " |", w.getWorkerFIO() + " |", w.getPhone(),
                        w.getE_mail() + " |", w.getSpecialty());
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет пользователей!");
        }
    }
    public void updateWorkerInfo(Workers worker) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE worker SET workerFIO =?, phone =?, `e-mail`=?, specialty =?" + "WHERE workerLogin = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, worker.getWorkerFIO());
            ps.setString(2, worker.getPhone());
            ps.setString(3, worker.getE_mail());
            ps.setString(4, worker.getSpecialty());
            ps.setString(5, worker.getWorkerLogin());
            state = ps.executeUpdate();
            System.out.println("Изменен сотрудник " + state);
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
        List<Workers> workersList = getWorkers();
        try (FileWriter writer = new FileWriter("Workers.txt", false)) {
            String text = "Логин сотрудника | ФИО сотрудника | Телефон | Электронная почта | Специализация\n";
            for (Workers w : workersList) {
                text += w.getWorkerLogin() + " |" + w.getWorkerFIO() + " |" + w.getPhone() +
                        " |" + w.getE_mail() + " |" + w.getSpecialty() + "\n";
            }
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
