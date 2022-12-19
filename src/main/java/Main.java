import roles.Admin;
import roles.User;
import roles.Worker;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Здравствуйте! Вы кто?");
        System.out.println("1.Мне просто нужна мебель");
        System.out.println("2.Я работник склада");
        System.out.println("3.Я админ склада");
        int choose = scan.nextInt();
        System.out.println("Введите логин и пароль");
        switch(choose){
            case 1:
                User basicUsr = new User(scan.nextLine(),scan.nextLine());
                break;
            case 2:
                Worker worker = new Worker(scan.nextLine(),scan.nextLine());
                break;
            case 3:
                Admin admin = new Admin(scan.nextLine(),scan.nextLine());
                break;
        }
        try {
            User.dbConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
