package roles;

import java.sql.Connection;
import java.sql.SQLException;

public interface Logable {
    Connection dbConnect() throws SQLException;
    void menu();
}
