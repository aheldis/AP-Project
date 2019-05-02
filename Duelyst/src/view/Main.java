package view;

import model.account.Shop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {
    public static File file = new File("Duelyst/TestFile");
    public static BufferedReader br;
    public static void main(String[] args) throws Exception {
        br =new BufferedReader(new FileReader(file));
        Shop.getInstance().show();
        Controller.MenuController.main();
    }
}
