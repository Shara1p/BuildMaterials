package roles;

public class Admin extends User implements Logable{
    public Admin(String dbLogin, String dbPass) {
        super(dbLogin, dbPass);
    }

    @Override
    public void menu() {
        System.out.println("1. ��������� ������");
        System.out.println("2. ������� ������ ������������");
        System.out.println("3. ������ �����������");
        System.out.println("4. ��������� �������");
        System.out.println("5. ������� ����� �������");
    }

}
