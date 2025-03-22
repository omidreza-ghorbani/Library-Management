import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final String SUCCESS = "success\n";
    static final String DUPLICATE = "duplicate-id\n";
    static final String NOT_FOUND = "not-found\n";

    public static void main(String[] args) {
        boolean toContinue = true;
        while(toContinue) {
            toContinue = Input.input();}
    }
}
