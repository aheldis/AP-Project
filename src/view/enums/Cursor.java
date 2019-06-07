package view.enums;

public enum Cursor {
    AUTO("pics/cursor/mouse_auto@2x.png"),
    ATTACK("pics/cursor/mouse_attack@2x.png"),
    CARD("pics/cursor/mouse_card@2x.png"),
    GREEN("pics/cursor/mouse_green.png"),
    MOVE("pics/cursor/mouse_move.png"),
    RED("pics/cursor/mouse_red.png"),
    LIGHTEN("pics/cursor/mouse.png");
    String path;
    Cursor(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
