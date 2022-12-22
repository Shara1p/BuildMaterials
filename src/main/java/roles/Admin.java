package roles;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin extends User implements Logable{
    public Admin(String dbLogin, String dbPass) {
        super(dbLogin, dbPass);
    }

    @Override
    public void menu() {
        System.out.println("1. Кастомный запрос");
        System.out.println("2. Создать нового Пользователя");
        System.out.println("3. Удалить пользователя");
        System.out.println("4. Новый тип изделия");
        System.out.println("5. Удалить тип");
        System.out.println("6. Создать новую таблицу");
        System.out.println("7. Удалить таблицу");
        System.out.println("8. Очистить таблицу");
        System.out.println("9. Выход");
    }
    public void customQuery(Connection userConnect) throws SQLException {
        Scanner scan = new Scanner(System.in, "windows-1251");
        try{
            this.printStatement(this.statement(scan.nextLine(),userConnect),userConnect);
        } catch (PSQLException ignored){

        }
    }
    public void addUsr(Connection userConnect) throws SQLException {
        Scanner scan = new Scanner(System.in, "windows-1251");
        System.out.println("login: ");
        String login = scan.nextLine();
        System.out.println("pass: ");
        String pass = scan.nextLine();
        System.out.println("createDb(true/false): ");
        boolean createDb = scan.nextBoolean();
        scan.nextLine();
        System.out.println("createRole(true/false): ");
        boolean createRl = scan.nextBoolean();
        scan.nextLine();
        try{
            if(createDb && createRl){
                this.statement("create role " + login +" with createdb createrole login password '" + pass + "'", userConnect);
            }else if(createRl){
                this.statement("create role " + login +" with createrole login password '" + pass + "'", userConnect);
            }else if (createDb){
                this.statement("create role " + login +" with createdb login password '" + pass + "'", userConnect);
            }else{
                this.statement("create role " + login +" with login password '" + pass + "'", userConnect);
            }
        }catch (PSQLException e){

        }

    }

    public void delUsr(Connection userConnect) throws SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        System.out.println("Имя удаляемой учетной записи: ");
        String name = scan.nextLine();
        try{
            this.statement("drop role if exists " + name , userConnect);

        }catch (PSQLException ignored){

        }
    }
    public void addType(Connection userConnect) throws SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        this.printStatement(this.statement("select * from item_type order by type_id",userConnect), userConnect);
        System.out.println("id: ");
        int id = scan.nextInt();
        scan.nextLine();
        System.out.println("Название типа");
        String typeName = scan.nextLine();
        try{
            this.statement("insert into item_type(type_id,type_name) values(" + id +", '" + typeName + "')",userConnect);

        }catch (PSQLException ignored){

        }
    }
    public void delType(Connection userConnect)throws SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        System.out.println("Удалить тип предмета: ");
        try{
            this.statement("delete from item_type where type_id = " + scan.nextInt(), userConnect);
        } catch (PSQLException ignored){

        }
    }
    public void addTable(Connection userConnect) throws SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        System.out.println("Название таблицы: ");
        String tableName = scan.nextLine();
        System.out.println("Кол-во столбцов: ");
        int columns = scan.nextInt();
        scan.nextLine();
        String query = "create table " + tableName + "(";
        System.out.println("Название столбцов через enter");
        for(int i = 0; i < columns - 1; i++){
            query += scan.nextLine() + ",\n";
        }
        query += scan.nextLine() + ")";
        try{
            this.statement(query,userConnect);
        }catch (PSQLException ignored){

        }
    }
    public void delTable(Connection userConnect) throws SQLException{
        Scanner scan = new Scanner(System.in, "windows-1251");
        System.out.println("Название удаляемой таблицы: ");
        try{
            this.statement("drop table if exists " + scan.nextLine(), userConnect);
        }catch (PSQLException ignored){

        }
    }
    public void clearTable(Connection userConnect) throws SQLException {
        Scanner scan = new Scanner(System.in, "windows-1251");
        System.out.println("Очистить таблицу: ");
        try{
            this.statement("truncate " + scan.nextLine() + " restart identity", userConnect);
        }catch(PSQLException ignored){

        }
    }
}
