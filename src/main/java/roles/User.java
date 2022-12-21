package roles;

import java.sql.*;

public class User implements Logable {
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
    public Connection dbConnect() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://172.20.8.18:5432/kp0092_23?useUnicode=yes&characterEncoding=ru_RU.UTF8", getDbLogin(), getDbPass());
    }
    public void menu(){
        System.out.println("1. Найти Товар по названию");
        System.out.println("2. Все изделия с указаниям поставщиков");
        System.out.println("3. Самое дешевое изделие в категории ______");
        System.out.println("4. Самое дорогое изделие в категории ______");
        System.out.println("5. Все изделия");
        System.out.println("6. Наличие");
        System.out.println("7. Выход");

    }

    @Override
    public ResultSet statement(String que, Connection userConnect) throws SQLException {
        String query = que;
        Statement statement = userConnect.createStatement();
        return statement.executeQuery(query);
    }

    @Override
    public ResultSet statement(String que, Connection userConnect, int index, String data) throws SQLException{
        String query = que;
        PreparedStatement preparedStatement = userConnect.prepareStatement(query);

        preparedStatement.setString(index, data);
        return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet statement(String que, Connection userConnect, int index, int data) throws SQLException {
        String query = que;
        PreparedStatement preparedStatement = userConnect.prepareStatement(query);

        preparedStatement.setInt(index, data);
        return preparedStatement.executeQuery();
    }

}
