package roles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class User {
    private static String dbLogin;
    private static String dbPass;

    public User(String dbLogin, String dbPass){
        setDbLogin(dbLogin);
        setDbPass(dbPass);
    }

    public static String getDbLogin() {
        return dbLogin;
    }

    public static void setDbLogin(String dbLogin) {
        User.dbLogin = dbLogin;
    }

    public static String getDbPass() {
        return dbPass;
    }

    public static void setDbPass(String dbPass) {
        User.dbPass = dbPass;
    }
    public static void dbConnect() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://172.20.8.18:5432/kp0092_23", getDbLogin(), getDbPass());
    }
    public void Menu(){
        System.out.println("1. Найти Товар по названию");
        System.out.println("2. Все изделия с указаниям поставщиков");
        System.out.println("3. Самое дешевое изделие в категории ______");
        System.out.println("4. Самое дорогое изделие в категории ______");
        System.out.println("5. Все изделия");
        System.out.println("6. Наличие");
        System.out.println("7. Выход");

    }
}
