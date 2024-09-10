package Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.*;
import modelForClient.*;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class Client {
    @FXML
    private TextField Login;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField NPassword;
    @FXML
    private TextField FIO;
    @FXML
    private TextField Phone;
    @FXML
    private TextField Email;
    @FXML
    private TextField Specialty;
    @FXML
    private TextField ServiceCode;
    @FXML
    private TextField ServiceName;
    @FXML
    private TextField Date;
    @FXML
    private TextField Time;
    @FXML
    private TextField RegisterNum;
    @FXML
    private TextArea MoreInfo;
    @FXML
    private TextField Cost;
    @FXML
    private TextField Lasting;
    @FXML
    private TextField Discount;
    @FXML
    private PieChart pieChart;


    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private TableView table;
    @FXML
    private TableView table2;

    private TableColumn<ClientModel, String> login_col = new TableColumn<ClientModel, String>("Логин");
    private TableColumn<ClientModel, String> fio_col = new TableColumn<ClientModel, String>("ФИО");
    private TableColumn<ClientModel, String> phone_col = new TableColumn<ClientModel, String>("Телефон");
    private TableColumn<ClientModel, String> card_col = new TableColumn<ClientModel, String>("Номер карты");
    private TableColumn<ClientModel, Integer> discount_col = new TableColumn<ClientModel, Integer>("Скидка");


    private TableColumn<RegisterModel, Integer> regNum_col = new TableColumn<RegisterModel, Integer>("Номер записи");
    private TableColumn<RegisterModel, String> clientLogin_col = new TableColumn<RegisterModel, String>("Логин клиента");
    private TableColumn<RegisterModel, Integer> code_col = new TableColumn<RegisterModel, Integer>("Код услуги");
    private TableColumn<RegisterModel, String> workerLogin_col = new TableColumn<RegisterModel, String>("Логин работника");
    private TableColumn<RegisterModel, String> date_col = new TableColumn<RegisterModel, String>("Дата");
    private TableColumn<RegisterModel, String> time_col = new TableColumn<RegisterModel, String>("Время");


    private TableColumn<ServiceModel, Integer> servicecode_col = new TableColumn<ServiceModel, Integer>("Код услуги");
    private TableColumn<ServiceModel, String> workerlogin_col = new TableColumn<ServiceModel, String>("Логин сотрудника");
    private TableColumn<ServiceModel, String> servicename_col = new TableColumn<ServiceModel, String>("Наименование услуги");
    private TableColumn<ServiceModel, String> moreinfo_col = new TableColumn<ServiceModel, String>("Доп. информация");
    private TableColumn<ServiceModel, Double> cost_col = new TableColumn<ServiceModel, Double>("Стоимость");
    private TableColumn<ServiceModel, Integer> lasting_col = new TableColumn<ServiceModel, Integer>("Длительность");


    private TableColumn<WorkerModel, String> wlogin_col = new TableColumn<WorkerModel, String>("Логин");
    private TableColumn<WorkerModel, String> wfio_col = new TableColumn<WorkerModel, String>("ФИО");
    private TableColumn<WorkerModel, String> wphone_col = new TableColumn<WorkerModel, String>("Телефон");
    private TableColumn<WorkerModel, String> email_col = new TableColumn<WorkerModel, String>("Электронная почта");
    private TableColumn<WorkerModel, String> specialty_col = new TableColumn<WorkerModel, String>("Специализация");


    private TableColumn<UserModel, String> ulogin_col = new TableColumn<UserModel, String>("Логин");
    private TableColumn<UserModel, String> urole_col = new TableColumn<UserModel, String>("Роль");

    private ObservableList<ClientModel> dataClients = FXCollections.observableArrayList();
    private ObservableList<WorkerModel> dataWorkers = FXCollections.observableArrayList();
    private ObservableList<RegisterModel> dataRegister = FXCollections.observableArrayList();
    private ObservableList<ServiceModel> dataService = FXCollections.observableArrayList();
    private ObservableList<UserModel> dataUsers = FXCollections.observableArrayList();
    Socket clientSocket = null;
    private static ObjectOutputStream coos = null;
    private static ObjectInputStream cois = null;

    Users user = new Users();
    Clients client = new Clients();

    @FXML
    private void CloseWindow() throws IOException {
        BasicWindowAction basicWindowActions = new BasicWindowAction();
        basicWindowActions.CloseWindow();
        coos.writeObject("exit");
    }

    @FXML
    private void SignUp() {
        try {
            clientSocket = new Socket("127.0.0.1", 2525);
            coos = new ObjectOutputStream(clientSocket.getOutputStream());
            cois = new ObjectInputStream(clientSocket.getInputStream());
        }catch (Exception e) {
            e.printStackTrace();
        }
        BasicWindowAction basicWindowActions = new BasicWindowAction();
        basicWindowActions.CloseWindow();
        basicWindowActions.OpenWindow("Registration.fxml", 600, 400);
    }


    private void SetupUserTable() {
        ulogin_col.setPrefWidth(table.getPrefWidth() / 2.f);
        urole_col.setPrefWidth(table.getPrefWidth() / 2.f);

        ulogin_col.setCellValueFactory(c -> c.getValue().Login());
        urole_col.setCellValueFactory(c -> c.getValue().Role());

        ulogin_col.setCellFactory(TextFieldTableCell.forTableColumn());
        urole_col.setCellFactory(TextFieldTableCell.forTableColumn());

        table.getColumns().addAll(ulogin_col, urole_col);
    }

    private void SetupClientTable() {
        login_col.setPrefWidth(table.getPrefWidth() / 5.f);
        fio_col.setPrefWidth(table.getPrefWidth() / 5.f);
        phone_col.setPrefWidth(table.getPrefWidth() / 5.f);
        card_col.setPrefWidth(table.getPrefWidth() / 5.f);
        discount_col.setPrefWidth(table.getPrefWidth() / 5.f);

        login_col.setCellValueFactory(c -> c.getValue().Login());
        fio_col.setCellValueFactory(c -> c.getValue().FIO());
        phone_col.setCellValueFactory(c -> c.getValue().Phone());
        card_col.setCellValueFactory(c -> c.getValue().Card());
        discount_col.setCellValueFactory(c -> c.getValue().Discount().asObject());

        login_col.setCellFactory(TextFieldTableCell.forTableColumn());
        fio_col.setCellFactory(TextFieldTableCell.forTableColumn());
        phone_col.setCellFactory(TextFieldTableCell.forTableColumn());
        card_col.setCellFactory(TextFieldTableCell.forTableColumn());
        discount_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        table.getColumns().addAll(login_col, fio_col, phone_col, card_col, discount_col);
    }

    private void SetupWorkerTable() {
        wlogin_col.setPrefWidth(table2.getPrefWidth() / 5.f);
        wfio_col.setPrefWidth(table2.getPrefWidth() / 5.f);
        wphone_col.setPrefWidth(table2.getPrefWidth() / 5.f);
        email_col.setPrefWidth(table2.getPrefWidth() / 5.f);
        specialty_col.setPrefWidth(table2.getPrefWidth() / 5.f);

        wlogin_col.setCellValueFactory(c -> c.getValue().Login());
        wfio_col.setCellValueFactory(c -> c.getValue().FIO());
        wphone_col.setCellValueFactory(c -> c.getValue().Phone());
        email_col.setCellValueFactory(c -> c.getValue().E_mail());
        specialty_col.setCellValueFactory(c -> c.getValue().Specialty());

        wlogin_col.setCellFactory(TextFieldTableCell.forTableColumn());
        wfio_col.setCellFactory(TextFieldTableCell.forTableColumn());
        wphone_col.setCellFactory(TextFieldTableCell.forTableColumn());
        email_col.setCellFactory(TextFieldTableCell.forTableColumn());
        specialty_col.setCellFactory(TextFieldTableCell.forTableColumn());

        table2.getColumns().addAll(wlogin_col, wfio_col, wphone_col, email_col, specialty_col);
    }

    private void SetupRegisterTable() {
        regNum_col.setPrefWidth(table.getPrefWidth() / 6.f);
        clientLogin_col.setPrefWidth(table.getPrefWidth() / 6.f);
        code_col.setPrefWidth(table.getPrefWidth() / 6.f);
        workerLogin_col.setPrefWidth(table.getPrefWidth() / 6.f);
        date_col.setPrefWidth(table.getPrefWidth() / 6.f);
        time_col.setPrefWidth(table.getPrefWidth() / 6.f);

        regNum_col.setCellValueFactory(c -> c.getValue().RegNum().asObject());
        clientLogin_col.setCellValueFactory(c -> c.getValue().ClientLogin());
        code_col.setCellValueFactory(c -> c.getValue().Code().asObject());
        workerLogin_col.setCellValueFactory(c -> c.getValue().WorkerLogin());
        date_col.setCellValueFactory(c -> c.getValue().Date());
        time_col.setCellValueFactory(c -> c.getValue().Time());

        regNum_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clientLogin_col.setCellFactory(TextFieldTableCell.forTableColumn());
        code_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        workerLogin_col.setCellFactory(TextFieldTableCell.forTableColumn());
        date_col.setCellFactory(TextFieldTableCell.forTableColumn());
        time_col.setCellFactory(TextFieldTableCell.forTableColumn());

        table.getColumns().addAll(regNum_col, clientLogin_col, code_col, workerLogin_col, date_col, time_col);
    }

    private void SetupServiceTable() {
        servicecode_col.setPrefWidth(table2.getPrefWidth() / 6.f);
        workerlogin_col.setPrefWidth(table2.getPrefWidth() / 6.f);
        servicename_col.setPrefWidth(table2.getPrefWidth() / 6.f);
        moreinfo_col.setPrefWidth(table2.getPrefWidth() / 6.f);
        cost_col.setPrefWidth(table2.getPrefWidth() / 6.f);
        lasting_col.setPrefWidth(table2.getPrefWidth() / 6.f);

        servicecode_col.setCellValueFactory(c -> c.getValue().Code().asObject());
        workerlogin_col.setCellValueFactory(c -> c.getValue().Wlogin());
        servicename_col.setCellValueFactory(c -> c.getValue().Servicename());
        moreinfo_col.setCellValueFactory(c -> c.getValue().Info());
        cost_col.setCellValueFactory(c -> c.getValue().Cost().asObject());
        lasting_col.setCellValueFactory(c -> c.getValue().Lasting().asObject());

        servicecode_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        workerlogin_col.setCellFactory(TextFieldTableCell.forTableColumn());
        servicename_col.setCellFactory(TextFieldTableCell.forTableColumn());
        moreinfo_col.setCellFactory(TextFieldTableCell.forTableColumn());
        cost_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        lasting_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        table2.getColumns().addAll(servicecode_col, workerlogin_col, servicename_col, moreinfo_col, cost_col, lasting_col);
    }


    public void buttomEnter(ActionEvent event) {
        String str = "", str2 = "hello world";
        try {
            clientSocket = new Socket("127.0.0.1", 2525);
            coos = new ObjectOutputStream(clientSocket.getOutputStream());
            cois = new ObjectInputStream(clientSocket.getInputStream());
            coos.writeObject("login");
            coos.writeObject(Login.getText());
            coos.writeObject(Password.getText());
            str = (String) cois.readObject();
            if (Objects.equals(str, "администратор")) {
                str2 = "Admin";
            } else if (Objects.equals(str, "клиент")) {
                str2 = "Client";
            } else if (Objects.equals(str, "специалист")) {
                str2 = "Worker";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Connected! " + str2);
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(str2 + "Menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttomRegistration(ActionEvent event) {
        String str = null;
        try {
            coos.writeObject("register");
            coos.writeObject(Login.getText());
            coos.writeObject(Password.getText());
            coos.writeObject(FIO.getText());
            coos.writeObject(Phone.getText());

            str = (String) cois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str.equals("error")) {
            System.out.println("Ошибка, есть такой логин");
//            errorTextRegistration.setText("Ошибка. Пользователь с таким логином уже существует");
            Login.clear();
        } else {
            try {
                System.out.println("Connected!");
                FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ClientMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
                stage.setTitle("Menu");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void clientViewPersonalInfoDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ClientsInfo.fxml"));
        try {
            coos.writeObject("view personal info");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientUpdatePersonalInfoDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("NewClientInfo.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clientsViewDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ClientsInfo.fxml"));
        try {
            coos.writeObject("view clients");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void clientViewPersonalInfo() {
        SetupClientTable();
        dataClients.clear();
        try {
            int size = (int) cois.readObject();

            for (int i = 0; i < size; i++) {
                dataClients.add(new ClientModel((Clients) cois.readObject()));
            }
            table.getItems().clear();
            table.setItems(dataClients);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void clientUpdatePersonalInfo(ActionEvent event) {
        try {
            coos.writeObject("update personal info");
            coos.writeObject(FIO.getText());
            coos.writeObject(Phone.getText());
            coos.writeObject(NPassword.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void intoFileClients() {
        try {
            coos.writeObject("clients file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteClient() {
        try {
            coos.writeObject(Login.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void deleteViewClient() {
        SetupClientTable();
        dataClients.clear();
        try {
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataClients.add(new ClientModel((Clients) cois.readObject()));
            }
            table.getItems().clear();
            table.setItems(dataClients);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteClientDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("DeleteClient.fxml"));
        try {
            coos.writeObject("delete client");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Delete Client");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void updateClientDiscountDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("addDiscount.fxml"));
        try {
            coos.writeObject("add discount");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Add discount");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateClientDiscount() {
        try {
            coos.writeObject(Login.getText());
            coos.writeObject(Integer.parseInt(Discount.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addAdmin() {
        try {
            coos.writeObject(Login.getText());
            coos.writeObject(Password.getText());
            coos.writeObject(FIO.getText());
            coos.writeObject(Phone.getText());
            coos.writeObject(Email.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addAdminDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("NewAdmin.fxml"));
        try {
            coos.writeObject("add admin");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Add Admin");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void adminViewPersonalInfoDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("AdminsInfo.fxml"));
        try {
            coos.writeObject("view personal info");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void adminUpdatePersonalInfoDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("NewAdminInfo.fxml"));
        try {
            coos.writeObject("update personal info");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void adminsViewDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("AdminsInfo.fxml"));
        try {
            coos.writeObject("view workers");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void workersViewInfo() {
        SetupWorkerTable();
        dataWorkers.clear();
        try {
            int size = (int) cois.readObject();

            for (int i = 0; i < size; i++) {
                dataWorkers.add(new WorkerModel((Workers) cois.readObject()));
            }
            table2.getItems().clear();
            table2.setItems(dataWorkers);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void workerViewPersonalInfoDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("WorkersInfo.fxml"));
        try {
            coos.writeObject("view personal info");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void workerViewDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("WorkersInfo.fxml"));
        try {
            coos.writeObject("view workers");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addWorker() {
        try {
            coos.writeObject(Login.getText());
            coos.writeObject(Password.getText());
            coos.writeObject(FIO.getText());
            coos.writeObject(Phone.getText());
            coos.writeObject(Email.getText());
            coos.writeObject(Specialty.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addWorkerDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("NewWorker.fxml"));
        try {
            coos.writeObject("add worker");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Add Worker");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteWorker() {
        try {
            coos.writeObject(Login.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void deleteViewWorkers() {
        SetupWorkerTable();
        dataWorkers.clear();
        try {
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataWorkers.add(new WorkerModel((Workers) cois.readObject()));
            }
            table2.getItems().clear();
            table2.setItems(dataWorkers);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void deleteWorkerDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("DeleteWorker.fxml"));
        try {
            coos.writeObject("delete worker");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Delete Worker");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void workerUpdatePersonalInfo(ActionEvent event) {
        try {
            coos.writeObject(FIO.getText());
            coos.writeObject(Phone.getText());
            coos.writeObject(Email.getText());
            coos.writeObject(Specialty.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void workerUpdatePersonalInfoDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("NewWorkerInfo.fxml"));
        try {
            coos.writeObject("update personal info");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void intoFileWorkers() {
        try {
            coos.writeObject("workers file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void usersViewDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("UsersInfo.fxml"));
        try {
            coos.writeObject("view users");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void userViewInfo() {
        SetupUserTable();
        dataUsers.clear();
        try {
            int size = (int) cois.readObject();

            for (int i = 0; i < size; i++) {
                dataUsers.add(new UserModel((Users) cois.readObject()));
            }
            table.getItems().clear();
            table.setItems(dataUsers);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void intoFileUsers() {
        try {
            coos.writeObject("users file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void servicesViewDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ServicesInfo.fxml"));
        try {
            coos.writeObject("view services");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("InfoServices");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void servicesViewInfo() {
        SetupServiceTable();
        dataService.clear();
        try {
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataService.add(new ServiceModel((Service) cois.readObject()));
            }
            table2.getItems().clear();
            table2.setItems(dataService);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void servicesSearchDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ServicesSearch.fxml"));
        try {
            coos.writeObject("search service");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("InfoServices");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void servicesSearchInfo() {
        SetupServiceTable();
        dataService.clear();
        try {
            coos.writeObject(ServiceName.getText());
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataService.add(new ServiceModel((Service) cois.readObject()));
            }
            table2.getItems().clear();
            table2.setItems(dataService);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteService() {
        try {
            coos.writeObject(Integer.parseInt(ServiceCode.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void deleteViewService() {
        SetupServiceTable();
        dataService.clear();
        try {
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataService.add(new ServiceModel((Service) cois.readObject()));
            }
            table2.getItems().clear();
            table2.setItems(dataService);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteServiceDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("DeleteService.fxml"));
        try {
            coos.writeObject("delete service");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Delete Service");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addService() {
        String costString = Cost.getText();
        double costDouble = Double.parseDouble(costString);
        if (costDouble < 0) {
            System.out.println("Ошибка. Стоимость меньше 0");
            Cost.clear();
        } else {
            try {
                coos.writeObject("add service");
                coos.writeObject(ServiceName.getText());
                coos.writeObject(MoreInfo.getText());

                coos.writeObject(Double.parseDouble(Cost.getText()));
                coos.writeObject(Integer.parseInt(Lasting.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addServiceDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("AddService.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Add Service");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewServicesWorker() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ServicesInfoForWorker.fxml"));
        try {
            coos.writeObject("view services");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("InfoServices");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateService() {
        try {
            coos.writeObject(Integer.parseInt(ServiceCode.getText()));
            coos.writeObject(ServiceName.getText());
            coos.writeObject(MoreInfo.getText());
            coos.writeObject(Double.parseDouble(Cost.getText()));
            coos.writeObject(Integer.parseInt(Lasting.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateServiceDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("UpdateService.fxml"));
        try {
            coos.writeObject("update service");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Update Service");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void registersViewDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("RegistersInfo.fxml"));
        try {
            coos.writeObject("view registers");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Info Registers");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void registersViewInfo() {
        SetupRegisterTable();
        dataRegister.clear();
        try {
            int size = (int) cois.readObject();

            for (int i = 0; i < size; i++) {
                dataRegister.add(new RegisterModel((Register) cois.readObject()));
            }
            table.getItems().clear();
            table.setItems(dataRegister);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void registersViewClientDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("ViewClientRegisters.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("InfoServices");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void registersViewClient() {
        SetupRegisterTable();
        dataRegister.clear();
        try {
            coos.writeObject("view register");
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataRegister.add(new RegisterModel((Register) cois.readObject()));
            }
            table.getItems().clear();
            table.setItems(dataRegister);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addRegister() {
        try {
            coos.writeObject(Integer.parseInt(ServiceCode.getText()));
            coos.writeObject(Date.getText());
            coos.writeObject(Time.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRegisterDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("NewRegister.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Add Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void addRegisterView() {
        SetupServiceTable();
        dataService.clear();
        try {
            coos.writeObject("add register");
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataService.add(new ServiceModel((Service) cois.readObject()));
            }
            table2.getItems().clear();
            table2.setItems(dataService);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteRegister() {
        try {
            coos.writeObject(Integer.parseInt(RegisterNum.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void deleteViewRegister() {
        SetupRegisterTable();
        dataRegister.clear();
        try {
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataRegister.add(new RegisterModel((Register) cois.readObject()));
            }
            table.getItems().clear();
            table.setItems(dataRegister);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteRegisterDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("DeleteRegister.fxml"));
        try {
            coos.writeObject("delete register");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Delete Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRegister() {
        try {
            coos.writeObject(Integer.parseInt(RegisterNum.getText()));
            coos.writeObject(Integer.parseInt(ServiceCode.getText()));
            coos.writeObject(Date.getText());
            coos.writeObject(Time.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void updateViewRegister() {
        SetupRegisterTable();
        dataRegister.clear();
        try {
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                dataRegister.add(new RegisterModel((Register) cois.readObject()));
            }
            table.getItems().clear();
            table.setItems(dataRegister);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateRegisterDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("UpdateRegister.fxml"));
        try {
            coos.writeObject("update register");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Update Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void analyticsUsers() {
        try {
            coos.writeObject("users analytics");
            int admins = (int) cois.readObject();
            int workers = (int) cois.readObject();
            int clients = (int) cois.readObject();
            ObservableList<PieChart.Data> serviceList = FXCollections.observableArrayList(
                    new PieChart.Data("Администраторы", admins),
                    new PieChart.Data("Специалисты", workers),
                    new PieChart.Data("Клиенты", clients)
            );
            pieChart.setData(serviceList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void analyticsUsersDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Analytics.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Update Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void analyticsDiscount() {
        try {
            coos.writeObject("discount analytics");
            int little = (int) cois.readObject();
            int middle = (int) cois.readObject();
            int large = (int) cois.readObject();
            ObservableList<PieChart.Data> serviceList = FXCollections.observableArrayList(
                    new PieChart.Data("Cкидка 5%", little),
                    new PieChart.Data("Cкидка 10%", middle),
                    new PieChart.Data("Cкидка 15%", large)
            );
            pieChart.setData(serviceList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void analyticsDiscountDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Analytics3.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Discount analytics");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void analyticsService() {
        try {
            coos.writeObject("service analytics");

            XYChart.Series series = new XYChart.Series<>();
            Service service;
            int size = (int) cois.readObject();
            for (int i = 0; i < size; i++) {
                service = (Service) cois.readObject();
                series.getData().add(new XYChart.Data<>(service.getServiceName(), service.getCost()));
            }
            barChart.getData().addAll(series);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void analyticsServiceDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Analytics2.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Update Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void analyticsCost() {
        try {
            coos.writeObject("cost analytics");
            int litlle = (int) cois.readObject();
            int middle = (int) cois.readObject();
            int large = (int) cois.readObject();

            XYChart.Series series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Низкий", litlle));
            series.getData().add(new XYChart.Data<>("Средний", middle));
            series.getData().add(new XYChart.Data<>("Высокий", large));
            barChart.getData().addAll(series);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void analyticsCostDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Analytics4.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Analytics");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

