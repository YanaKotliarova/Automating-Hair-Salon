package authorization;

import dao.UserDao;
import model.Users;
import java.util.List;

public class LogIn {

    public String authorization(String login, String password) {

        UserDao userDao = new UserDao();
        List<Users> usersList = userDao.getUsers();
        String role = "";

        Users currentUser = new Users();

        for(Users u : usersList) {
            if(u.getLogin().equals(login) && u.getPassword().equals(password))
                currentUser = new Users(u.getLogin(), u.getPassword(), u.getRole());
        }
        if (currentUser != null) {
            System.out.println("Авторизация пройдена успешно!");
            role = currentUser.getRole();
            switch (role) {
                case "администратор":
                    System.out.println("Вы вошли как администратор");
                    break;
                case "специалист":
                    System.out.println("Вы вошли как работник");
                    currentUser.setLogin(currentUser.getLogin());
                    break;
                case "клиент":
                    System.out.println("Вы вошли как клиент");
                    break;
            }
        }
        else {
            System.out.println("Такого пользователя не найдено");
        }
        return role;
    }
}