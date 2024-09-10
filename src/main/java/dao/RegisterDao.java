package dao;

import model.Register;
import model.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int state;

    public List<Register> getRegisters() {
        List<Register> registerList = new ArrayList<Register>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM register";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Register register = new Register();
                register.setRegisterNumber(rs.getInt("registerNumber"));
                register.setClientLogin(rs.getString("clientLogin"));
                register.setServiceCode(rs.getInt("serviceCode"));
                register.setWorkerLogin(rs.getString("workerLogin"));
                register.setDate(rs.getString("date"));
                register.setTime(rs.getString("time"));
                registerList.add(register);
            }
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        }
        return registerList;
    }
    public List<Register> getClientRegisters(String clientLogin) {
        List<Register> registerList = new ArrayList<Register>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM register WHERE clientLogin = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, clientLogin);
            rs = ps.executeQuery();
            while (rs.next()) {
                Register register = new Register();
                register.setRegisterNumber(rs.getInt("registerNumber"));
                register.setClientLogin(rs.getString("clientLogin"));
                register.setServiceCode(rs.getInt("serviceCode"));
                register.setWorkerLogin(rs.getString("workerLogin"));
                register.setDate(rs.getString("date"));
                register.setTime(rs.getString("time"));
                registerList.add(register);
            }
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        }
        return registerList;
    }
    public List<Register> getWorkerRegisters(String workerLogin) {
        List<Register> registerList = new ArrayList<Register>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM register WHERE workerLogin = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, workerLogin);
            rs = ps.executeQuery();
            while (rs.next()) {
                Register register = new Register();
                register.setRegisterNumber(rs.getInt("registerNumber"));
                register.setClientLogin(rs.getString("clientLogin"));
                register.setServiceCode(rs.getInt("serviceCode"));
                register.setWorkerLogin(rs.getString("workerLogin"));
                register.setDate(rs.getString("date"));
                register.setTime(rs.getString("time"));
                registerList.add(register);
            }
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        }
        return registerList;
    }
    public void addRegister(Register register) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "INSERT INTO register(clientLogin, serviceCode, workerLogin, date, time) " + "VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, register.getClientLogin());
            ps.setInt(2, register.getServiceCode());
            ps.setString(3, register.getWorkerLogin());
            ps.setString(4, register.getDate());
            ps.setString(5, register.getTime());
            state = ps.executeUpdate();
            System.out.println("Добавленна запись " + state);
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
    public void updateRegister(Register register, int registerNumber) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE register SET serviceCode =?, workerLogin =?, date=?, time=?" + "WHERE registerNumber = ? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, register.getServiceCode());
            ps.setString(2, register.getWorkerLogin());
            ps.setString(3, register.getDate());
            ps.setString(4, register.getTime());
            ps.setInt(5, registerNumber);
            state = ps.executeUpdate();
            System.out.println("Изменена запись " + state);
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
    public void deleteRegister(int registerNumber) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "DELETE FROM register WHERE registerNumber=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, registerNumber);
            state = ps.executeUpdate();
            System.out.println("Удалена запись " + state);
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
    public void showAllRegisters(List<Register> registerList) {
        if (registerList.size() != 0) {
            System.out.format("%12s%20s%20s%20s%20s%20s", "Номер записи | ", "Логин клиента | ", "Код услуги |", "Логин специалиста | ",
                    "Дата | ", "Время");
            for (Register r : registerList) {
                System.out.println(" ");
                System.out.format("%12s%20s%20s%20s%20s%20s", r.getRegisterNumber() + " | ", r.getClientLogin() + " | ", r.getServiceCode() + " | ", r.getWorkerLogin() +
                        " | ", r.getDate() + " | ", r.getTime());
            }
            System.out.println(" ");
        } else {
            System.out.println("Нет услуг!");
        }
    }
    public void intoFile(String workerLogin) {
        List<Register> registerList = getWorkerRegisters(workerLogin);
        try (FileWriter writer = new FileWriter("RegistersForWorker.txt", false)) {
            String text = "Логин клиента | Код услуги | Дата | Время\n";
            for (Register r : registerList) {
                text += r.getClientLogin() + " |" + r.getServiceCode() + " |" +
                        " |" + r.getDate() + " |" + r.getTime() + "\n";
            }
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void readFile() {
        try (FileReader reader = new FileReader("RegistersForWorker.txt")) {
            char[] buf = new char[256];
            int c;
            while ((c = reader.read(buf)) > 0) {

                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
                System.out.print(buf);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
