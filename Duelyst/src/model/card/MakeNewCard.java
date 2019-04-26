package model.card;

import view.NewCardMessages;
import view.Request;

import java.io.*;

public class MakeNewCard {

    private static String checkSyntax(FileWriter fileWriter, String line) {
    }

    public static void makeNewCardFile(String filePathName) {
        NewCardMessages newCardMessages = NewCardMessages.getInstance();
        Request request = new Request("Card");
        while (true) {
            try {
                FileReader fileReader = new FileReader("../CardsFile/" + filePathName + "/TEMPLATE");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                newCardMessages.printer("name: ");
                request.getNewLine();
                String input = request.getCommand();
                if (input.equals("EXIT"))
                    break;
                File file = new File("../CardsFile/" + filePathName + "/" + input);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                while ((line = bufferedReader.readLine()) != null) {
                    newCardMessages.printer(line);

                    request.getNewLine();
                    input = request.getCommand();
                    bufferedWriter.write(line);
                    bufferedWriter.write(" " + input);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();

            } catch (FileNotFoundException e) {
                newCardMessages.printer("file not found");
            } catch (Exception e) {
                newCardMessages.printer("other error");
            }
        }
    }

}
