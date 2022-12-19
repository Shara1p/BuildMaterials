package roles;

public class Worker extends User implements Logable{
    public Worker(String dbLogin, String dbPass) {
        super(dbLogin, dbPass);
    }

    @Override
    public void menu() {
        System.out.println("1.Добавить новый предмет");
    }
}
