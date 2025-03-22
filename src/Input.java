public class Input {
    static boolean input() {
        String input =  Main.scanner.nextLine();
        String[] tokens = input.split("#");
        return switch (tokens[0]) {
            case "add-library" -> {
                LibraryManager.addLibrary(tokens[1]); yield true;}
            case "add-category" -> {
                CategoryManager.addCategory(tokens[1]); yield true;}
            default -> false;
        };
    }
}
