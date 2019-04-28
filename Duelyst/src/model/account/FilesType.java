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

    public static FilesType getEnum(String input){
        for(FilesType type: FilesType.values()){
            if(type.getName().equals(input))
                return type;
        }
        return null;
    }
}
