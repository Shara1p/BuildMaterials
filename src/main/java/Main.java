import org.postgresql.util.PSQLException;
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
        System.out.println("1.Я просто хочу купить");
        System.out.println("2.Я работник склада");
        System.out.println("3.Я админ склада");
        int choose = scan.nextInt();
        scan.nextLine();
        System.out.println("Введите логин и пароль");
        Logable user = switch (choose) {
            case 1 -> new User(scan.nextLine(), scan.nextLine());
            case 2 -> new Worker(scan.nextLine(), scan.nextLine());
            case 3 -> new Admin(scan.nextLine(), scan.nextLine());
            default -> null;
        };


        assert user != null;
        Connection userConnect = user.dbConnect();


        while (true) {
            user.menu();
            choose = scan.nextInt();
            scan.nextLine();
            if (user instanceof Admin) {
                switch (choose) {
                    case 1 -> ((Admin) user).customQuery(userConnect);
                    case 2 -> ((Admin) user).addUsr(userConnect);
                    case 3 -> ((Admin) user).delUsr(userConnect);
                    case 4 -> ((Admin) user).addType(userConnect);
                    case 5 -> ((Admin) user).delType(userConnect);
                    case 6 -> ((Admin) user).addTable(userConnect);
                    case 7 -> ((Admin) user).delTable(userConnect);
                    case 8 -> ((Admin) user).clearTable(userConnect);
                    case 9 -> System.exit(0);
                }
            } else if (user instanceof Worker) {
                switch (choose){
                    case 1:
                        ((Worker)user).addItem(userConnect);
                        break;
                    case 2:
                        ((Worker)user).showMaintable(userConnect);
                        break;
                    case 3:
                        try {
                            ((Worker)user).delItem(userConnect);
                        } catch (PSQLException ignored){

                        }
                        break;
                    case 4:
                        ((Worker)user).haveUpd(userConnect);
                        break;
                    case 5:
                        ((Worker)user).showHave(userConnect);
                        break;
                    case 6:
                        System.exit(0);
                }
            } else {
                switch (choose) {
                    case 1 -> ((User) user).findItem(userConnect);
                    case 2 -> ((User) user).producerItem(userConnect);
                    case 3 -> ((User) user).cheapCat(userConnect);
                    case 4 -> ((User) user).expCat(userConnect);
                    case 5 -> ((User) user).allItems(userConnect);
                    case 6 -> ((User) user).showHave(userConnect);
                    case 7 -> ((User) user).addToCart(userConnect);
                    case 8 -> ((User)user).buyItem(userConnect);
                    case 9 -> ((User)user).showCart(userConnect);
                    case 10 -> System.exit(0);
                }
            }
        }
    }
}