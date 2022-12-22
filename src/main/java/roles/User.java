package roles;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Logable {
    private static String dbLogin;
    private static String dbPass;

    private static ArrayList<Bucket> cart = new ArrayList<>();

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
        System.out.println("7. Добавить в корзину");
        System.out.println("8. Купить");
        System.out.println("9. Корзина");
        System.out.println("10. Выход");

    }

    public void findItem(Connection userConnect) throws SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        String query = "select ed_izm, item_type, delivery, item_name,price from item_view where item_name = ?";
        System.out.println("Введите название товара:");
        String itemName = scan.nextLine();
        this.printStatement(this.statement(query,userConnect, 1, itemName),userConnect);
    }

    public void producerItem(Connection userConnect) throws SQLException{
        this.printStatement(this.statement("select * from producer_item",userConnect),userConnect);
    }
    public void cheapCat(Connection userConnect) throws SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        String query = "select * from item_view where price = (select min(price) from item_view where item_type = " +
                "(select type_id from item_type where type_name = ?))";
        System.out.println("Введите название категории");
        String category = scan.nextLine();
        this.printStatement(this.statement(query, userConnect,1, category), userConnect);
    }

    public void expCat(Connection userConnect) throws SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        String query = "select * from item_view where price = (select max(price) from item_view where item_type = " +
                "(select type_id from item_type where type_name = ?))";
        System.out.println("Введите название категории");
        String category = scan.nextLine();
        this.printStatement(this.statement(query, userConnect,1, category), userConnect);
    }

    public void allItems(Connection userConnect) throws SQLException{
        this.printStatement(this.statement("select * from item_view",userConnect),userConnect);
    }

    public void showHave(Connection userConnect) throws SQLException{
        String query = "select amount, ed_izm, delivery, item_name, price from have_view left join item_view on item_view.item_id = have_view.item_id";
        this.printStatement((this.statement(query, userConnect)),userConnect);
    }

    public void addToCart(Connection userConnect)throws  SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        allItems(userConnect);
        System.out.println("Положить в корзину позицию: ");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.println("Кол-во: ");
        int amount = scan.nextInt();
        scan.nextLine();
        ResultSet queryResult = this.statement("select amount from have_view where item_id = "+id, userConnect);
        queryResult.next();
        int realAmount = queryResult.getInt("amount");
        while(amount > realAmount){
            System.out.println("Такого количества у нас нет(");
            System.out.println("Кол-во: ");
            amount = scan.nextInt();
            scan.nextLine();
        }
        queryResult = this.statement("select price from item_view where item_id = "+ id, userConnect);
        queryResult.next();
        int price = queryResult.getInt("price") * amount;
        queryResult = this.statement("select item_name from item_view where item_id = "+ id, userConnect);
        queryResult.next();
        String itemName = queryResult.getString("item_name");
        cart.add(new Bucket(id,amount, price, itemName));
    }

    public void buyItem(Connection userConnect) throws  SQLException{
        System.out.println("***ПОКУПКА***");
        System.out.println("***ПОКУПКА***");
        System.out.println("***ПОКУПКА***");
        for (Bucket cart : cart) {
            try {
                this.statement("update have_view set amount = amount - " + cart.amount + "where item_id = "+cart.getItemId(), userConnect);
            } catch (PSQLException ignored) {

            }
        }
        showCart(userConnect);
    }
    public void showCart(Connection userConnect) throws  SQLException{
        System.out.println("Nтого: ");
        int fullprice = 0;
        for(int i = 0 ; i < cart.size(); i++){
            fullprice += cart.get(i).getPrice();
            System.out.println("Кол-во: "+ cart.get(i).getAmount());
            System.out.println("Цена: "+ cart.get(i).getPrice());
            System.out.println("Название: "+ cart.get(i).getItemName());
        }
        System.out.println("Nтоговая цена: " + fullprice);
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

    @Override
    public void printStatement(ResultSet queryResult, Connection userConnect) throws SQLException {
        int columns = queryResult.getMetaData().getColumnCount();
        while(queryResult.next()){
            for(int i = 1; i <= columns; i++){
                System.out.print(queryResult.getString(i) + " ");
            }
            System.out.println();
        }
    }
    private class Bucket{
        private int itemId;
        private int amount;
        private int price;

        private String itemName;

        public Bucket(int itemId, int amount, int price, String itemName) {
            setAmount(amount);
            setItemId(itemId);
            setPrice(price);
            setItemName(itemName);
        }
        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setPrice(int price){
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

        public String getItemName() {
            return itemName;
        }
        public void setItemName(String itemName){
            this.itemName = itemName;
        }
    }
}
