package view;

import model.account.Shop;

public class Main {
   // public static File file = new File("Duelyst/TestFile");
   // public static BufferedReader br;
    public static void main(String[] args) throws Exception {
       // br =new BufferedReader(new FileReader(file));
        Shop.getInstance();
        controller.MenuController.main();
    }
}
