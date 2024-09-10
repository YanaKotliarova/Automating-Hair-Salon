package modelForClient;

import javafx.beans.property.SimpleStringProperty;
import model.Workers;

public class WorkerModel {
    private SimpleStringProperty workerLogin = null;
    private SimpleStringProperty workerFIO = null;
    private SimpleStringProperty phone = null;
    private SimpleStringProperty e_mail = null;
    private SimpleStringProperty specialty = null;

    public WorkerModel(){
        workerLogin = new SimpleStringProperty();
        workerFIO = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        e_mail = new SimpleStringProperty();
        specialty = new SimpleStringProperty();
    }
    public WorkerModel(Workers worker)
    {
        workerLogin = new SimpleStringProperty(worker.getWorkerLogin());
        workerFIO = new SimpleStringProperty(worker.getWorkerFIO());
        phone = new SimpleStringProperty(worker.getPhone());
        e_mail = new SimpleStringProperty(worker.getE_mail());
        specialty = new SimpleStringProperty(worker.getSpecialty());
    }

    public SimpleStringProperty Login() { return workerLogin; }
    public SimpleStringProperty FIO() { return workerFIO; }
    public SimpleStringProperty Phone() { return phone; }
    public SimpleStringProperty E_mail() { return e_mail; }
    public SimpleStringProperty Specialty() { return specialty; }
}
