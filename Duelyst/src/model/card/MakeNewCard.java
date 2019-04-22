package model.card;

import view.Request;

import java.io.*;

public class MakeNewCard {

    public static void makeNewCardFromFile(String filePathName) {
        Request request=new Request("Card");
        while (true) {
            try {
                FileReader fileReader = new FileReader("../CardsFile/"+filePathName+"/TEMPLATE");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                System.out.println("name: ");
                request.getNewLine();
                String input = request.getCommand();
                if (input.equals("EXIT"))
                    break;
                File file = new File("../CardsFile/"+filePathName+"/" + input);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    request.getNewLine();
                    input = request.getCommand();

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
