package roles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Logable {
    Connection dbConnect() throws SQLException;
    void menu();
    ResultSet statement(String que, Connection userConnect) throws SQLException;
    ResultSet statement(String que, Connection userConnect, int index, String data) throws SQLException;
    ResultSet statement(String que, Connection userConnect, int index, int data) throws SQLException;
    void printStatement(ResultSet queryResult, Connection userConnect) throws SQLException;
}
