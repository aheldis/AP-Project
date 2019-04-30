package model.card;

import view.NewCardMessages;
import view.Request;
import view.enums.RequestType;
import view.enums.StateType;

import java.io.*;

public class MakeNewBuff {

    public static void makeNewBuff() {
        Request request = new Request(StateType.ACCOUNT_MENU);//MOHM NI CHI BASHE STATE
        NewCardMessages newCardMessages = NewCardMessages.getInstance();
        int lineNumber = 1;
        while (true) {
            try {

                FileReader fileReader = new FileReader("../BuffsFile/TEMPLATE");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                newCardMessages.printLine("name: ");
                request.getNewLine();
                String input = request.getCommand();
                if (input.equals("EXIT"))
                    break;
                File file = new File("../BuffsFile/" + input);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("name: " + input);
                bufferedWriter.newLine();
                while ((line = bufferedReader.readLine()) != null) {
                    do {
                        newCardMessages.printLine(line);
                        request.getNewLine();
                        input = request.getCommand();
                    } while (checkSyntax(lineNumber, input));
                    bufferedWriter.write(line);
                    bufferedWriter.write(" " + input);
                    bufferedWriter.newLine();
                    lineNumber++;
                }
                bufferedWriter.close();

            } catch (FileNotFoundException e) {
                newCardMessages.printLine("file not found");
            } catch (Exception e) {
                newCardMessages.printLine("other error");
            }
        }
    }

    private static boolean checkSyntax(int lineNumber, String input) {
        switch (lineNumber) {
            case 1:
                if (!input.matches("\\w+"))
                    return false;
                break;
            case 2:
                if (!input.matches("\\d+"))
                    return false;
                break;
            case 3:
                if (!input.matches("\\d+"))
                    return false;
                break;
            case 4:
                if (!input.matches("(true|false)"))
                    return false;
                break;
            case 5:
                if (!input.matches("(true|false)"))
                    return false;
                break;
            case 6:
                if (!input.matches("\\d+"))
                    return false;
                break;
        }
        return true;
    }

}
