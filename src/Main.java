import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final String SUCCESS_STR = "success\n";
    static final String DUPLICATE_STR = "duplicate-id\n";
    static final String NOT_FOUND_STR = "not-found\n";

    public static void main(String[] args) {
        boolean to_continue = true;
        while(to_continue) {
            to_continue = Input.input();}
    }
}
