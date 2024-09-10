package Server;

import authorization.LogIn;
import dao.*;
import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

public class ClientThread implements Runnable {

    private Socket client;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;

    public ClientThread() {
    }

    public ClientThread(Socket client) {
        this.client = client;
        this.cois = null;
        this.coos = null;
    }

    @Override
    public void run() {
        try {
            cois = new ObjectInputStream(client.getInputStream());
            coos = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection refused");
            return;
        }

        Users user;
        UserDao userDao = new UserDao();
        Clients clients = new Clients();
        ClientDao clientDao = new ClientDao();
        Register register = new Register();
        RegisterDao registerDao = new RegisterDao();
        Workers workers = new Workers();
        WorkerDao workerDao = new WorkerDao();
        Service service = new Service();
        ServiceDao serviceDao = new ServiceDao();

        String clientCommand = null;
        String login = "", role = "";
        while (!Objects.equals(clientCommand, "exit")) {
            try {
                String loginType = (String) cois.readObject();
                switch (loginType) {
                    case "login": {
                        LogIn logIn = new LogIn();
                        login = (String) cois.readObject();
                        String password = (String) cois.readObject();
                        role = logIn.authorization(login, password);
                        coos.writeObject(role);
                        break;
                    }
                    case "register": {
                        login = (String) cois.readObject();
                        String password = (String) cois.readObject();
                        role = "клиент";
                        String FIO = (String) cois.readObject();
                        String phone = (String) cois.readObject();

                        if (checkUniqueLogin(login)) {
                            user = new Users(login, password, role);
                            clients = new Clients(login, FIO, phone, phone);
                            userDao.addUser(user, clients);
                            coos.writeObject("Здравствуйте, " + login);
                        } else {
                            coos.writeObject("error");
                            role = null;
                        }
                        break;
                    }
                }
//        try {
//            String loginType = (String) cois.readObject();
//            if (loginType.equals("login")) {
//                LogIn logIn = new LogIn();
//                login = (String) cois.readObject();
//                String password = (String) cois.readObject();
//                role = logIn.authorization(login, password);
//                coos.writeObject(role);
//
//            } else if (loginType.equals("register")) {
//                login = (String) cois.readObject();
//                String password = (String) cois.readObject();
//                role = "клиент";
//                String FIO = (String) cois.readObject();
//                String phone = (String) cois.readObject();
//
//                if (checkUniqueLogin(login)) {
//                    user = new Users(login, password, role);
//                    clients = new Clients(login, FIO, phone, phone);
//                    userDao.addUser(user, clients);
//                    coos.writeObject("Здравствуйте, " + login);
//                } else {
//                    coos.writeObject("error");
//                    role = null;
//                }
//            }
                switch (role) {
                    case "администратор": {
                        while ((clientCommand = (String) cois.readObject()) != null) {
                            switch (clientCommand) {
                                case "view personal info": {
                                    System.out.println("Просмотр личной информации сотрудника " + login);
                                    workers = workerDao.getWorker(login);
                                    coos.writeObject(1);
                                    coos.writeObject(workers);
                                    break;
                                }
                                case "update personal info": {
                                    System.out.println("Изменение личной информации администратора " + login);
                                    String fio = (String) cois.readObject();
                                    String phone = (String) cois.readObject();
                                    String email = (String) cois.readObject();
                                    String specialty = (String) cois.readObject();
                                    workers = new Workers(login, fio, phone, email, specialty);
                                    workerDao.updateWorkerInfo(workers);
                                    break;
                                }

                                case "view services": {
                                    List<Service> serviceList = serviceDao.getAllServices();
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    break;
                                }
                                case "search service": {
                                    String serviceName = (String) cois.readObject();
                                    List<Service> serviceList = serviceDao.searchByName(serviceName);
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    break;
                                }
                                case "delete service": {
                                    List<Service> serviceList = serviceDao.getAllServices();
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    int serviceCode = (Integer) cois.readObject();
                                    System.out.println(serviceCode);
                                    serviceDao.deleteService(serviceCode);
                                    break;
                                }
                                case "service analytics": {
                                    List<Service> serviceList = serviceDao.getAllServices();
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    break;
                                }
                                case "cost analytics": {
                                    int little = 0, middle = 0, large = 0;
                                    List<Service> serviceList = serviceDao.getAllServices();
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        if (serviceList.get(i).getCost() <= 40)
                                            little++;
                                        else if (serviceList.get(i).getCost() > 40 && serviceList.get(i).getCost() <= 70)
                                            middle++;
                                        else if (serviceList.get(i).getCost() > 70)
                                            large++;
                                    }
                                    coos.writeObject(little);
                                    coos.writeObject(middle);
                                    coos.writeObject(large);
                                    break;
                                }

                                case "view registers": {
                                    List<Register> registerList = registerDao.getRegisters();
                                    coos.writeObject(registerList.size());
                                    System.out.println(registerList.size());
                                    for (int i = 0; i < registerList.size(); i++) {
                                        coos.writeObject(registerList.get(i));
                                    }
                                    break;
                                }
                                case "users analytics": {
                                    int client = 0, worker = 0, admin = 0;
                                    List<Users> usersList = userDao.getUsers();
                                    for (int i = 0; i < usersList.size(); i++) {
                                        if (Objects.equals(usersList.get(i).getRole(), "администратор"))
                                            admin++;
                                        else if (Objects.equals(usersList.get(i).getRole(), "специалист"))
                                            worker++;
                                        else if (Objects.equals(usersList.get(i).getRole(), "клиент"))
                                            client++;
                                    }
                                    coos.writeObject(admin);
                                    coos.writeObject(worker);
                                    coos.writeObject(client);
                                    break;
                                }

                                case "add admin": {
                                    String newlogin = (String) cois.readObject();
                                    String password = (String) cois.readObject();
                                    role = "администратор";
                                    String FIO = (String) cois.readObject();
                                    String phone = (String) cois.readObject();
                                    String email = (String) cois.readObject();
                                    String specialty = "Администратор";
                                    user = new Users(newlogin, password, role);
                                    workers = new Workers(newlogin, FIO, phone, email, specialty);
                                    userDao.addUser(user, workers);
                                    break;
                                }
                                case "add worker": {
                                    String newlogin = (String) cois.readObject();
                                    String password = (String) cois.readObject();
                                    role = "специалист";
                                    String FIO = (String) cois.readObject();
                                    String phone = (String) cois.readObject();
                                    String email = (String) cois.readObject();
                                    String specialty = (String) cois.readObject();
                                    user = new Users(newlogin, password, role);
                                    workers = new Workers(newlogin, FIO, phone, email, specialty);
                                    userDao.addUser(user, workers);
                                    break;
                                }
                                case "delete worker": {
                                    List<Workers> workersList = workerDao.getWorkers();
                                    coos.writeObject(workersList.size());
                                    System.out.println(workersList.size());
                                    for (int i = 0; i < workersList.size(); i++) {
                                        coos.writeObject(workersList.get(i));
                                    }
                                    String workerlogin = (String) cois.readObject();
                                    System.out.println(workerlogin);
                                    userDao.deleteUser(workerlogin);
                                    break;
                                }
                                case "view workers": {
                                    List<Workers> workersList = workerDao.getWorkers();
                                    coos.writeObject(workersList.size());
                                    System.out.println(workersList.size());
                                    for (int i = 0; i < workersList.size(); i++) {
                                        coos.writeObject(workersList.get(i));
                                    }
                                    break;
                                }
                                case "delete client": {
                                    List<Clients> clientsList = clientDao.getClients();
                                    coos.writeObject(clientsList.size());
                                    System.out.println(clientsList.size());
                                    for (int i = 0; i < clientsList.size(); i++) {
                                        coos.writeObject(clientsList.get(i));
                                    }
                                    String clientLogin = (String) cois.readObject();
                                    System.out.println(clientLogin);
                                    userDao.deleteUser(clientLogin);
                                    break;
                                }
                                case "view clients": {
                                    List<Clients> clientsList = clientDao.getClients();
                                    coos.writeObject(clientsList.size());
                                    System.out.println(clientsList.size());
                                    for (int i = 0; i < clientsList.size(); i++) {
                                        coos.writeObject(clientsList.get(i));
                                    }
                                    break;
                                }
                                case "clients file": {
                                    clientDao.intoFile();
                                    System.out.println("Запись прошла успешно!");
                                    break;
                                }
                                case "add discount": {
                                    List<Clients> clientsList = clientDao.getClients();
                                    coos.writeObject(clientsList.size());
                                    System.out.println(clientsList.size());
                                    for (int i = 0; i < clientsList.size(); i++) {
                                        coos.writeObject(clientsList.get(i));
                                    }
                                    String clientLogin = (String) cois.readObject();
                                    int discount = (int) cois.readObject();
                                    clientDao.addDiscount(clientLogin, discount);
                                    break;
                                }
                                case "discount analytics": {
                                    int little = 0, middle = 0, large = 0;
                                    List<Clients> clientsList = clientDao.getClients();
                                    System.out.println(clientsList.size());
                                    for (int i = 0; i < clientsList.size(); i++) {
                                        if (clientsList.get(i).getDiscount() == 5)
                                            little++;
                                        else if (clientsList.get(i).getDiscount() == 10)
                                            middle++;
                                        else if (clientsList.get(i).getDiscount() == 15)
                                            large++;
                                    }
                                    coos.writeObject(little);
                                    coos.writeObject(middle);
                                    coos.writeObject(large);
                                    break;
                                }

                                case "view users": {
                                    List<Users> usersList = userDao.getUsers();
                                    coos.writeObject(usersList.size());
                                    System.out.println(usersList.size());
                                    for (int i = 0; i < usersList.size(); i++) {
                                        coos.writeObject(usersList.get(i));
                                    }
                                    break;
                                }
                                case "users file": {
                                    userDao.intoFile();
                                    System.out.println("Запись прошла успешно!");
                                    break;
                                }

                                case "exit": {
                                    clientCommand = "exit";
                                    break;
                                }
                            }
                        }
                    }
                    case "специалист": {
                        while ((clientCommand = (String) cois.readObject()) != null) {
                            switch (clientCommand) {
                                case "view personal info": {
                                    System.out.println("Просмотр личной информации сотрудника " + login);
                                    workers = workerDao.getWorker(login);
                                    coos.writeObject(1);
                                    coos.writeObject(workers);
                                    break;
                                }
                                case "update personal info": {
                                    System.out.println("Изменение личной информации сотрудника " + login);
                                    String fio = (String) cois.readObject();
                                    String phone = (String) cois.readObject();
                                    String email = (String) cois.readObject();
                                    String specialty = (String) cois.readObject();
                                    workers = new Workers(login, fio, phone, email, specialty);
                                    workerDao.updateWorkerInfo(workers);
                                    break;
                                }
                                case "workers file": {
                                    registerDao.intoFile(login);
                                    System.out.println("Запись прошла успешно!");
                                    break;
                                }

                                case "add service": {
                                    String serviceName = (String) cois.readObject();
                                    String moreInfo = (String) cois.readObject();
                                    double cost = (double) cois.readObject();
                                    int lasting = (int) cois.readObject();
                                    service = new Service(login, serviceName, moreInfo, cost, lasting);
                                    serviceDao.addService(service);
                                    break;
                                }
                                case "update service": {
                                    List<Service> serviceList = serviceDao.getWorkerServices(login);
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    int serviceCode = (int) cois.readObject();
                                    String serviceName = (String) cois.readObject();
                                    String moreInfo = (String) cois.readObject();
                                    double cost = (double) cois.readObject();
                                    int lasting = (int) cois.readObject();
                                    service = new Service(login, serviceName, moreInfo, cost, lasting);
                                    serviceDao.updateServiceInfo(service, serviceCode);
                                    break;
                                }
                                case "delete service": {
                                    List<Service> serviceList = serviceDao.getWorkerServices(login);
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    int serviceCode = (int) cois.readObject();
                                    serviceDao.deleteService(serviceCode);
                                    break;
                                }
                                case "view services": {
                                    List<Service> serviceList = serviceDao.getWorkerServices(login);
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    break;
                                }

                                case "view registers": {
                                    List<Register> registerList = registerDao.getWorkerRegisters(login);
                                    coos.writeObject(registerList.size());
                                    System.out.println(registerList.size());
                                    for (int i = 0; i < registerList.size(); i++) {
                                        coos.writeObject(registerList.get(i));
                                    }
                                    break;
                                }

                                case "exit": {
                                    clientCommand = "exit";
                                    break;
                                }
                            }
                        }
                    }
                    case "клиент": {
                        System.out.println("Вы вошли как клиент!");
                        while ((clientCommand = (String) cois.readObject()) != null) {
                            switch (clientCommand) {
                                case "view personal info": {
                                    System.out.println("Просмотр личной информации клиента " + login);
                                    clients = clientDao.getClient(login);
                                    coos.writeObject(1);
                                    coos.writeObject(clients);
                                    break;
                                }
                                case "update personal info": {
                                    System.out.println("Изменение личной информации клиента " + login);
                                    String fio = (String) cois.readObject();
                                    String phone = (String) cois.readObject();
                                    String password = (String) cois.readObject();
                                    user = new Users(login, password, role);
                                    clients = new Clients(login, fio, phone, phone);
                                    userDao.updateUserPassword(user);
                                    clientDao.updateClientInfo(clients);
                                    break;
                                }

                                case "add register": {
                                    List<Service> serviceList = serviceDao.getAllServices();
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    int serviceCode = (int) cois.readObject();
                                    String date = (String) cois.readObject();
                                    String time = (String) cois.readObject();
                                    String workerLogin = serviceDao.showTimetable(serviceCode);
                                    register = new Register(login, serviceCode, workerLogin, date, time);
                                    registerDao.addRegister(register);
                                    break;
                                }
                                case "delete register": {
                                    List<Register> registerList = registerDao.getClientRegisters(login);
                                    coos.writeObject(registerList.size());
                                    System.out.println(registerList.size());
                                    for (int i = 0; i < registerList.size(); i++) {
                                        coos.writeObject(registerList.get(i));
                                    }
                                    int registerNum = (int) cois.readObject();
                                    registerDao.deleteRegister(registerNum);
                                    break;
                                }
                                case "view register": {
                                    List<Register> registerList = registerDao.getClientRegisters(login);
                                    coos.writeObject(registerList.size());
                                    System.out.println(registerList.size());
                                    for (int i = 0; i < registerList.size(); i++) {
                                        coos.writeObject(registerList.get(i));
                                    }
                                    break;
                                }
                                case "update register": {
                                    List<Register> registerList = registerDao.getClientRegisters(login);
                                    coos.writeObject(registerList.size());
                                    System.out.println(registerList.size());
                                    for (int i = 0; i < registerList.size(); i++) {
                                        coos.writeObject(registerList.get(i));
                                    }
                                    List<Service> serviceList = serviceDao.getAllServices();
                                    coos.writeObject(serviceList.size());
                                    System.out.println(serviceList.size());
                                    for (int i = 0; i < serviceList.size(); i++) {
                                        coos.writeObject(serviceList.get(i));
                                    }
                                    int registerNum = (int) cois.readObject();
                                    int serviceCode = (int) cois.readObject();
                                    String date = (String) cois.readObject();
                                    String time = (String) cois.readObject();
                                    String workerLogin = serviceDao.showTimetable(serviceCode);
                                    register = new Register(login, serviceCode, workerLogin, date, time);
                                    registerDao.updateRegister(register, registerNum);
                                    break;
                                }

                                case "exit": {
                                    clientCommand = "exit";
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Exit");
        try {
            coos.close();
            cois.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUniqueLogin(String login) {
        UserDao userDao = new UserDao();
        boolean isUnique = true;
        for (Users u : userDao.getUsers()) {
            if (u.getLogin().equals(login)) {
                isUnique = false;
            }
        }
        return isUnique;
    }
}