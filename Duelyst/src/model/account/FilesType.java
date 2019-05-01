package model.account;

public enum FilesType {
    HERO("Hero"),
    MINION("Minion"),
    SPELL("Spell"),
    ITEM("item"),
    BUFF("Buff");

    private String name;

    FilesType(String name) {
        this.name = name;
    }

    public static FilesType getEnum(String input) {
        switch (input) {
            case "Spell":
                return FilesType.SPELL;
            case "Hero":
                return FilesType.HERO;
            case "Minion":
                return FilesType.MINION;
            case "item":
                return FilesType.ITEM;
            case "Buff":
                return FilesType.BUFF;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
