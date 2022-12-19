package roles;

public class Admin extends User{
    public Admin(String dbLogin, String dbPass) {
        super(dbLogin, dbPass);
    }

    @Override
    public void Menu() {
        System.out.println("1. Кастомный запрос");
        System.out.println("2. Создать Нового Пользователя");
        System.out.println("3. Список поставщиков");
        System.out.println("4. Категории изделий");
    }
}
