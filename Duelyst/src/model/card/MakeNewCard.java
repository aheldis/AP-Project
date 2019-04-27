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

    public static void makeNewCardFile() {
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

                File file = new File("../CardsFile/"   + input);
                Files.copy(Paths.get("../Duelyst/GsonModel"),file.toPath());//mesle form khaliye tosho por mikonim

                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                while ((line = bufferedReader.readLine()) != null) {
                    newCardMessages.printer(line);

                    if(line==1 || line==8 ||line==21 ||line==22 ||line==29 ||line==30)
                        buffered
                    request.getNewLine();
                    input = request.getCommand();
                    bufferedWriter.append(input+"\",");
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
