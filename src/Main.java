import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        User admin = new Admin("admin", "AdminPass");
        User.users.put("admin", admin);
        boolean toContinue = true;
        while (toContinue) {
            toContinue = Input.input();
        }
    }
}