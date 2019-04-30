package model.card;

import view.NewCardMessages;
import view.Request;

import java.io.*;

public class MakeNewBuff {

    public static void makeNewBuff() {
        Request request = new Request("buff");
        NewCardMessages newCardMessages = NewCardMessages.getInstance();
        int lineNumber = 1;
        while (true) {
            try {

                FileReader fileReader = new FileReader("../BuffsFile/TEMPLATE");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                newCardMessages.printer("name: ");
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
                        newCardMessages.printer(line);
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
                newCardMessages.printer("file not found");
            } catch (Exception e) {
                newCardMessages.printer("other error");
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
