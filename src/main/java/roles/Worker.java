package roles;

public class Worker extends User{
    public Worker(String dbLogin, String dbPass) {
        super(dbLogin, dbPass);
    }

    @Override
    public void Menu() {
        System.out.println("1.Добавить новый предмет");
    }
}
