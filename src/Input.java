class Input {

    static boolean input() {
        String input = Main.scanner.nextLine();
        String[] tokens = input.split("#");
        return switch (tokens[0]) {
            case "add-library" -> {
                Library.addLibrary(tokens[1]);
                yield true;
            }
            case "add-category" -> {
                Category.addCategory(tokens[1]);
                yield true;
            }
            case "add-student" -> {
                User.addUser(tokens[1], "student");
                yield true;
            }
            case "add-staff" -> {
                User.addUser(tokens[1], "staff");
                yield true;
            }
            case "add-manager" -> {
                User.addUser(tokens[1], "manager");
                yield true;
            }
            case "remove-user" -> {
                User.removeUser(tokens[1]);
                yield true;
            }
            case "add-book" -> {
                Resource.addResource(tokens[1], "book");
                yield true;
            }
            case "add-thesis" -> {
                Resource.addResource(tokens[1], "thesis");
                yield true;
            }
            case "add-ganjineh-book" -> {
                Resource.addResource(tokens[1], "treasureTrove");
                yield true;
            }
            case "add-selling-book" -> {
                Resource.addResource(tokens[1], "forSale");
                yield true;
            }
            case "remove-resource" -> {
                Resource.removeResource(tokens[1]);
                yield true;
            }
            case "borrow" -> {
                Borrow.borrowHandler(tokens[1]);
                yield true;
            }
            case "return" -> {
                Return.returnHandler(tokens[1]);
                yield true;
            }
            case "buy" -> {
                Buy.buyHandler(tokens[1]);
                yield true;
            }
            case "read" -> {
                ReadTreasure.readHandler(tokens[1]);
                yield true;
            }
            case "add-comment" -> {
                Comment.addComment(tokens[1]);
                yield true;
            }
            case "search" -> {
                Search.searchWord(tokens[1]);
                yield true;
            }
            case "search-user" -> {
                Search.searchUser(tokens[1]);
                yield true;
            }
            case "category-report" -> {
                Report.categoryReport(tokens[1]);
                yield true;
            }
            case "library-report" -> {
                Report.libraryReport(tokens[1]);
                yield true;
            }
            case "report-passed-deadline" -> {
                Report.passedDeadline(tokens[1]);
                yield true;
            }
            case "report-penalties-sum" -> {
                Report.penaltiesSumReport(tokens[1]);
                yield true;
            }
            case "report-most-popular" -> {
                ReportMostPopular.handle(tokens[1]);
                yield true;
            }
            case "report-sell" -> {
                ReportSell.handle(tokens[1]);
                yield true;
            }
            default -> false;
        };
    }
}