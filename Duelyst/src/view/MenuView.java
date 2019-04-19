package view;

public class MenuView {
    private MenuView singleInstacne = null;

    public MenuView getInstance() {
        if (singleInstacne == null)
            singleInstacne = new MenuView();
        return singleInstacne;
    }

    public void helpForMainMenu(){
        System.out.println("Enter Collection - goto your collection");
        System.out.println("Enter Shop - goto shop");
        System.out.println("Enter Battle - start a game");
        System.out.println("Enter Help - show help of main menu");
        System.out.println("Enter Exit - logout from game");
    }
}
