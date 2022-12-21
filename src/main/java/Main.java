import roles.Admin;
import roles.Logable;
import roles.User;
import roles.Worker;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in, "windows-1251");

        System.out.println("Здравствуйте! Вы кто?");
        System.out.println("1.Мне просто нужна мебель");
        System.out.println("2.Я работник склада");
        System.out.println("3.Я админ склада");
        int choose = scan.nextInt();
        scan.nextLine();
        System.out.println("Введите логин и пароль");
        Logable user = null;


        switch (choose) {
            case 1:
                user = new User(scan.nextLine(), scan.nextLine());
                break;
            case 2:
                user = new Worker(scan.nextLine(), scan.nextLine());
                break;
            case 3:
                user = new Admin(scan.nextLine(), scan.nextLine());
                break;
        }
        assert user != null;
        Connection userConnect = user.dbConnect();


        while (true) {
            user.menu();
            if (user instanceof User) {
                choose = scan.nextInt();
                scan.nextLine();
                switch (choose) {
                    case 1:
                        String query = "select ed_izm, item_type, delivery, item_name,price from item_view where item_name = ?";
                        System.out.println("Введите название товара:");
                        String itemName = scan.nextLine();
                        ResultSet queryResult = user.statement(query, userConnect, 1, itemName);
                        while (queryResult.next()) {
                            System.out.print(queryResult.getString("ed_izm") + " " +
                                    queryResult.getString("item_type") + " " +
                                    queryResult.getString("delivery") + "rue " +
                                    queryResult.getString("item_name") + " " +
                                    queryResult.getString("price") + "\n");
                        }
                        break;
                    case 2:
                        query = "select * from producer_item";
                        queryResult = user.statement(query, userConnect);
                        while (queryResult.next()) {
                            System.out.print(queryResult.getString("item_name") + " " +
                                    queryResult.getString("amount") + " " +
                                    queryResult.getString("price") + " " +
                                    queryResult.getString("ed_izm") + " " +
                                    queryResult.getString("delivery") + " " +
                                    queryResult.getString("prod_name") + " " +
                                    queryResult.getString("phone_num") + "\n");
                        }
                        break;
                    case 3:
                        query = "select * from item_view where price = (select min(price) from item_view where item_type = " +
                                "(select type_id from item_type where type_name = ?))";
                        System.out.println("Введите название категории");
                        String category = scan.nextLine();
                        queryResult = user.statement(query, userConnect, 1, category);
                        while (queryResult.next()) {
                            System.out.print(queryResult.getString("ed_izm") + " " +
                                    queryResult.getString("delivery") + " " +
                                    queryResult.getString("item_name") + " " +
                                    queryResult.getString("price") + "\n");
                        }
                        break;
                    case 4:
                        query = "select * from item_view where price = (select max(price) from item_view where item_type = " +
                                "(select type_id from item_type where type_name = ?))";
                        System.out.println("Введите название категории");
                        category = scan.nextLine();
                        queryResult = user.statement(query, userConnect, 1, category);
                        while (queryResult.next()) {
                            System.out.print(queryResult.getString("ed_izm") + " " +
                                    queryResult.getString("delivery") + " " +
                                    queryResult.getString("item_name") + " " +
                                    queryResult.getString("price") + "\n");
                        }
                        break;
                    case 5:
                        query = "select * from item_view";
                        queryResult = user.statement(query, userConnect);
                        while (queryResult.next()) {
                            System.out.print(queryResult.getString("ed_izm") + " " +
                                    queryResult.getString("item_type") + " " +
                                    queryResult.getString("delivery") + " " +
                                    queryResult.getString("item_name") + " " +
                                    queryResult.getString("price") + "\n");
                        }
                        break;
                    case 6:
                        query = "select amount, ed_izm, delivery, item_name, price from have_view left join item_view on item_view.item_id = have_view.item_id";
                        queryResult = user.statement(query,userConnect);
                        while (queryResult.next()) {
                            System.out.print(queryResult.getString("amount") + " " +
                                    queryResult.getString("ed_izm") + " " +
                                    queryResult.getString("delivery") + " " +
                                    queryResult.getString("item_name") + " " +
                                    queryResult.getString("price") + "\n");
                        }
                        break;
                    case 7:
                        System.exit(0);
                }

            } else if (user instanceof Worker) {
                System.out.println();
            } else {

            }
        }
    }
}