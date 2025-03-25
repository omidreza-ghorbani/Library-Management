import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final String SUCCESS = "success\n";
    static final String DUPLICATE = "duplicate-id\n";
    static final String NOT_FOUND = "not-found\n";
    static final String ADMIN_STR = "admin";
    static final String PERMISSION = "permission-denied\n";
    static final String INVALID_PASS = "invalid-pass\n";
    static final String NOT_ALLOWED = "not-allowed\n";

    public static void main(String[] args) {
        User admin = new Admin("admin","AdminPass");
        User.users.put("admin",admin);
        boolean toContinue = true;
        while(toContinue) {
            toContinue = Input.input();}
    }
}
