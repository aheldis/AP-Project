package model.Item;

import java.io.*;
import java.util.Scanner;

public class MakeNewUsableFile {

    public static void mainn(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {

                FileReader fileReader = new FileReader("../ItemsFile/Usable/TEMPLATE");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                System.out.println("name: ");
                String input = scanner.nextLine();
                if (input.equals("EXIT"))
                    break;
                File file = new File("../ItemsFile/Usable/" + input);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("name: " + input);
                bufferedWriter.newLine();
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    input = scanner.nextLine();

                    bufferedWriter.write(line);
                    bufferedWriter.write(" " + input);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();

            } catch (FileNotFoundException e) {
                System.out.println("file not found");
            } catch (Exception e) {
                System.out.println("other error");
            }
        }
    }
}