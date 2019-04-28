package model.card;

import view.NewCardMessages;
import view.Request;
import view.enums.StateType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MakeNewCard {

    private static String checkSyntax(FileWriter fileWriter, String line) {
    }

    public static Card makeNewCardFile() {
        NewCardMessages newCardMessages = NewCardMessages.getInstance();
        Request request = new Request(StateType.BATTLE);//mohem ni chi bashe mikham az scanneresh estefade konam
        while (true) {
            try {

                FileReader fileReader = new FileReader("../InfoForMakeCard/");
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = null;
                newCardMessages.printer("name: ");
                request.getNewLine();
                String input = request.getCommand();

                if (input.equals("EXIT"))
                    break;

                Card card = new Card() ;

                File file = new File("../CardsFile/"   + input);

                FileWriter fileWriter = new FileWriter(file);
                while ((line = bufferedReader.readLine()) != null) {
                    newCardMessages.printer(line);

                    request.getNewLine();
                    //todo reflector
                    input = request.getCommand();




                    //todo GsonBuilder . prety prnting . create .
                }
                fileWriter.close();

            } catch (FileNotFoundException e) {
                newCardMessages.printer("file not found");
            } catch (Exception e) {
                newCardMessages.printer("other error");
            }
        }
        fileWriter.write(gson.toJson(card));
    }

}
