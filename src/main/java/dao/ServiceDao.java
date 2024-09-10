package dao;

import model.Clients;
import model.Register;
import model.Service;
import model.Users;

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

public class ServiceDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int state;
    public void addService(Service service) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "INSERT INTO service(workerLogin, serviceName, moreInfo, cost, lasting) " + "VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, service.getWorkerLogin());
            ps.setString(2, service.getServiceName());
            ps.setString(3, service.getMoreInfo());
            ps.setDouble(4, service.getCost());
            ps.setInt(5, service.getLasting());
            state = ps.executeUpdate();
            System.out.println("Добавленная услуга " + state);
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

    public void updateServiceInfo(Service service, int serviceCode) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE service SET serviceName =?, moreInfo =?, cost=?, lasting=? WHERE serviceCode = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getMoreInfo());
            ps.setDouble(3, service.getCost());
            ps.setInt(4, service.getLasting());
            ps.setInt(5, serviceCode);
            state = ps.executeUpdate();
            System.out.println("Изменена услуга " + state);
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

    public void deleteService(int serviceCode) {
        con = ConnectionFactory.getConnection();
        try {
            String query = "DELETE FROM service WHERE serviceCode=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, serviceCode);
            state = ps.executeUpdate();
            System.out.println("Удалена услуга " + state);
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

    public List<Service> chooseServiceCode(String workerLogin) {
        List<Service> serviceList = new ArrayList<Service>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM service WHERE workerLogin=?";
            ps = con.prepareStatement(query);
            ps.setString(1, workerLogin);
            rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setServiceCode(rs.getInt("serviceCode"));
                service.setWorkerLogin(rs.getString("workerLogin"));
                service.setServiceName(rs.getString("serviceName"));
                service.setMoreInfo(rs.getString("moreInfo"));
                service.setCost(rs.getDouble("cost"));
                service.setLasting(rs.getInt("lasting"));
                serviceList.add(service);
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
        return serviceList;
    }

    public String showTimetable(int serviceCode) {
        con = ConnectionFactory.getConnection();
        String workerLogin = "";
        try {
            String query = "SELECT * FROM service WHERE serviceCode = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, serviceCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setServiceCode(rs.getInt("serviceCode"));
                service.setWorkerLogin(rs.getString("workerLogin"));
                service.setServiceName(rs.getString("serviceName"));
                service.setMoreInfo(rs.getString("moreInfo"));
                service.setCost(rs.getDouble("cost"));
                service.setLasting(rs.getInt("lasting"));
                workerLogin = service.getWorkerLogin();
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
        return workerLogin;
    }

    public List<Service> getWorkerServices(String workerLogin) {
        List<Service> serviceList = new ArrayList<Service>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM service WHERE workerLogin = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, workerLogin);
            rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setServiceCode(rs.getInt("serviceCode"));
                service.setWorkerLogin(rs.getString("workerLogin"));
                service.setServiceName(rs.getString("serviceName"));
                service.setMoreInfo(rs.getString("moreInfo"));
                service.setCost(rs.getDouble("cost"));
                service.setLasting(rs.getInt("lasting"));
                serviceList.add(service);
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
        return serviceList;
    }
    public List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<Service>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM service";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setServiceCode(rs.getInt("serviceCode"));
                service.setWorkerLogin(rs.getString("workerLogin"));
                service.setServiceName(rs.getString("serviceName"));
                service.setMoreInfo(rs.getString("moreInfo"));
                service.setCost(rs.getDouble("cost"));
                service.setLasting(rs.getInt("lasting"));
                serviceList.add(service);
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
        return serviceList;
    }

    public void showServices(String workerLogin) {
        Users user = new Users();
        List<Service> serviceList = chooseServiceCode(workerLogin);
        if (serviceList.size() != 0) {
            System.out.format("%12s%20s%22s%20s%20s%20s", "Код услуги |", "Логин специалиста |", "Наименование услуги |",
                    "Больше информации |", "Стоимость |", "Длительность ");
            for (Service s : serviceList) {
                System.out.println(" ");
                System.out.format("%12s%20s%22s%20s%20s%20s", s.getServiceCode() + " |", s.getWorkerLogin() + " |", s.getServiceName() +
                        " |", s.getMoreInfo() + " |", s.getCost() + " |", s.getLasting());
            }
            System.out.println(" ");
        } else {
            System.out.println("Нет услуг!");
        }
    }

    public void showAllServices(List<Service> serviceList) {
        if (serviceList.size() != 0) {
            System.out.format("%12s%20s%20s%20s%20s%20s", "Код услуги |", "Логин специалиста |", "Наименование услуги",
                    "Больше информации |", "Стоимость |", "Длительность ");
            for (Service s : serviceList) {
                System.out.println(" ");
                System.out.format("%12s%20s%20s%20s%20s%20s", s.getServiceCode() + " |", s.getWorkerLogin() + " |", s.getServiceName() +
                        " |", s.getMoreInfo() + " |", s.getCost() + " |", s.getLasting());
            }
            System.out.println(" ");
        } else {
            System.out.println("Нет услуг!");
        }
    }

    public void intoFile() {
        List<Service> serviceList = getAllServices();
        try (FileWriter writer = new FileWriter("Services.txt", false)) {
            String text = "Код услуги | Логин специалиста | Наименование услуги | Больше информации | Стоимость | Длительность\n";
            for (Service s : serviceList) {
                text += s.getServiceCode() + " |" + s.getWorkerLogin() + " |" + s.getServiceName() +
                        " |" + s.getMoreInfo() + " |" + s.getCost() + " |" + s.getLasting() + "\n";
            }
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void readFile() {
        try (FileReader reader = new FileReader("Services.txt")) {
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

    public List<Service> searchByName(String serviceName) {
        List<Service> serviceList = new ArrayList<Service>();
        con = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM service WHERE serviceName = ? OR serviceName like '" + serviceName + "%'";
            ps = con.prepareStatement(query);
            ps.setString(1, serviceName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setServiceCode(rs.getInt("serviceCode"));
                service.setWorkerLogin(rs.getString("workerLogin"));
                service.setServiceName(rs.getString("serviceName"));
                service.setMoreInfo(rs.getString("moreInfo"));
                service.setCost(rs.getDouble("cost"));
                service.setLasting(rs.getInt("lasting"));
                serviceList.add(service);
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
        return serviceList;
    }
}
