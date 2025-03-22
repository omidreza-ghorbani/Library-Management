public class Input {
    static boolean input() {
        String input =  Main.scanner.nextLine();
        String[] tokens = input.split("#");
        return switch (tokens[0]) {
            case "add-library" -> {
                LibraryManager.addLibrary(tokens[1]); yield true;}
            case "add-category" -> {
                CategoryManager.addCategory(tokens[1]); yield true;}
            case "add-student" -> {
                User.addUser(tokens[1], "student"); yield true;}
            case "add-staff" -> {
                User.addUser(tokens[1], "staff"); yield true;}
            case "add-manager" -> {
                User.addUser(tokens[1], "manager"); yield true;}
            default -> false;};
    }
}
