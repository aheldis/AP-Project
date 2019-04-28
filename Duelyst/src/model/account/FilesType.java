package model.account;

public enum FilesType {
    HERO("hero"),
    MINION("minion"),
    SPELL("spell"),
    ITEM("item");

    private String name;

    public String getName() {
        return name;
    }

    FilesType(String name) {
        this.name = name;
    }
}
