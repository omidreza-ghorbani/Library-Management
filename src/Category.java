class Category {
    private final String adminName;
    private final String adminPassword;
    private final String id;
    private final String categoryName;
    private final String parentsCategory;
    public Category(String adminName, String adminPassword, String id, String categoryName, String parentsCategory) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.id = id;
        this.categoryName = categoryName;
        this.parentsCategory = parentsCategory;
    }
}