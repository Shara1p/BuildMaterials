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
    public void dbConnect() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://172.20.8.18:5432/kp0092_23", getDbLogin(), getDbPass());
    }
}
