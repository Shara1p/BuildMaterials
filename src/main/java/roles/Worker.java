package roles;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Scanner;

public class Worker extends User implements Logable{
    public Worker(String dbLogin, String dbPass) {
        super(dbLogin, dbPass);
    }

    @Override
    public void menu() {
        System.out.println("1.�������� ����� �������");
        System.out.println("2.�������� �������� �������");
        System.out.println("3.������� �������");
        System.out.println("4.������������� �������");
        System.out.println("5.�������");
        System.out.println("6.�����");
    }
    public void showMaintable(Connection userConnect) throws SQLException {
        String query = "select * from work_edit order by item_id";
        this.printStatement(this.statement(query, userConnect),userConnect);
    }

    public void addItem(Connection userConnect) throws SQLException {
        Scanner scan = new Scanner(System.in, "windows-1251");
        showMaintable(userConnect);
        String query = "call work_edit_insert(?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = userConnect.prepareStatement(query);
        System.out.println("������: ");
        preparedStatement.setInt(1,scan.nextInt());
        scan.nextLine();
        System.out.println("��.���: ");
        preparedStatement.setString(2, scan.nextLine());
        System.out.println("Id ����: ");
        preparedStatement.setInt(3, scan.nextInt());
        scan.nextLine();
        System.out.println("��������(true/false): ");
        preparedStatement.setBoolean(4, scan.nextBoolean());
        scan.nextLine();
        System.out.println("�������� ��������: ");
        preparedStatement.setString(5, scan.nextLine());
        System.out.println("����: ");
        preparedStatement.setInt(6, scan.nextInt());
        scan.nextLine();
        System.out.println("Id ����������: ");
        preparedStatement.setInt(7, scan.nextInt());
        scan.nextLine();
        System.out.println("���-��: ");
        preparedStatement.setInt(8, scan.nextInt());
        scan.nextLine();
        System.out.println("Id ������: ");
        preparedStatement.setInt(9, scan.nextInt());
        scan.nextLine();
        preparedStatement.executeUpdate();
        showMaintable(userConnect);
    }

    public void delItem(Connection userConnect)throws SQLException{
        showMaintable(userConnect);
        Scanner scan = new Scanner(System.in);
        System.out.println("��� ����� ������� ������� �������?");
        int id = scan.nextInt();
        String query = "call work_edit_del(?)";
        this.statement(query, userConnect, 1,id);
        showMaintable(userConnect);
    }
    public void haveUpd(Connection userConnect)throws SQLException{
        Scanner scan = new Scanner(System.in);
        String query = "call have_update(?,?)";
        System.out.println("Id ������������ ��������: ");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.println("����� ���-��: ");
        int amount = scan.nextInt();
        scan.nextLine();
        PreparedStatement preparedStatement = userConnect.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2,amount);
        preparedStatement.executeUpdate();
    }
    public void showHave(Connection userConnect) throws SQLException{
        String query = "select * from have_view";
        this.printStatement(this.statement(query,userConnect),userConnect);
    }
}
