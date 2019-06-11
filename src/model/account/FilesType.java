package model.account;

public enum FilesType {
    HERO("Hero"),
    MINION("Minion"),
    SPELL("Spell"),
    ITEM("Item"),
    BUFF("Buff"),
    USABLE("Usable"),
    COLLECTIBLE("Collectible");

    private String name;

    FilesType(String name) {
        this.name = name;
    }

    public static FilesType getEnum(String input) {
        switch (input.trim().toLowerCase()) {
            case "Spell":
                return FilesType.SPELL;
            case "hero":
                return FilesType.HERO;
            case "minion":
                return FilesType.MINION;
            case "item":
                return FilesType.ITEM;
            case "buff":
                return FilesType.BUFF;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
