package roles;

public class Admin extends User implements Logable{
    public Admin(String dbLogin, String dbPass) {
        super(dbLogin, dbPass);
    }

    @Override
    public void menu() {
        System.out.println("1. Кастомный запрос");
        System.out.println("2. Создать Нового Пользователя");
        System.out.println("3. Список поставщиков");
        System.out.println("4. Категории изделий");
    }
}
