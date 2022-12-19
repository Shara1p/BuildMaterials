import roles.Admin;
import roles.Logable;
import roles.User;
import roles.Worker;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Здравствуйте! Вы кто?");
        System.out.println("1.Мне просто нужна мебель");
        System.out.println("2.Я работник склада");
        System.out.println("3.Я админ склада");
        int choose = scan.nextInt();
        scan.nextLine();
        System.out.println("Введите логин и пароль");
        Logable user = null;


        switch (choose){
            case 1:
                user = new User(scan.nextLine(),scan.nextLine());
                break;
            case 2:
                user = new Worker(scan.nextLine(),scan.nextLine());
                break;
            case 3:
                user = new Admin(scan.nextLine(),scan.nextLine());
                break;
        }
        Connection userConnect;
        assert user != null;
        userConnect = user.dbConnect();


        while(true){
            user.menu();
            if(user instanceof User){
                choose = scan.nextInt();
                switch(choose){
                    case 1:
                        System.out.println("Введите название товара:");
                        String itemName = scan.nextLine();
                        String query = "select ed_izm, item_type, delivery, item_name,price from item_view where item_name = ?";
                        PreparedStatement preparedStatement = userConnect.prepareStatement(query);
                        preparedStatement.setString(1, itemName);
                        ResultSet queryResult =  preparedStatement.executeQuery();
                        while(queryResult.next()){
                            System.out.print(queryResult.getString("Единицы измерения") + " " +
                                    queryResult.getString("Тип предмета") + " " +
                                    queryResult.getString("Доставка") + " "+
                                    queryResult.getString("Название") + " "+
                                    queryResult.getString("Цена(за 1 единицу товара)") + "\n");
                        }
                }
            } else if(user instanceof Worker){

            }else{

            }
        }

    }
}
