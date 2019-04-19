package view;

import java.util.Scanner;


/**
 * mikhay esmesho bezarim Scanner (to javaDoc neveshtam ke
 *  sabz she :o )
 */

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private String command;

    public void getNewCommand() {
        this.command = scanner.nextLine();
    }
}
