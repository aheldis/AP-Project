package model.Item;

import view.NewCardMessages;
import view.Request;
import view.enums.StateType;

import java.io.*;
import java.util.Scanner;

public class MakeNewUsableFile {

    private static boolean checkSyntax(int lineNumber,String input){
        switch (lineNumber){
            case 1:
                if(!input.matches("\\d+"))
                    return false;
                break;
            case 2:
                if(!input.matches("[\\+|-]?\\d+_\\d+"))
                    return false;
                break;
            case 3:
                if(!input.matches("\\w+_\\d+"))
                    return false;
                break;
            case 4:
                if(!input.matches("(ranged|hybrid|melee)"))
                    return false;
                break;
            case 5:
                if(!input.matches("\\d+"))
                    return false;
                break;
            case 6:
                if(!input.matches("(friend|enemy)_(hero|minion)_(true_false)"))
                    return false;
                break;
            case 7:
                if(!input.matches("\\d+"))
                    return false;
                break;
            case 8:
                if(!input.matches("(attack|death|put)"))
                    return false;
                break;
        }
        return true;
    }

    public static void makeUsableFile() {
        Request request=new Request(StateType.BATTLE);//mohem nist chon az get typesh estefade nmikonam
        NewCardMessages newCardMessages=NewCardMessages.getInstance();
        int lineNumber=1;
        while (true) {
            try {

                FileReader fileReader = new FileReader("../ItemsFile/Usable/TEMPLATE");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                newCardMessages.printer("name: ");
                request.getNewLine();
                String input = request.getCommand();
                if (input.equals("EXIT"))
                    break;
                File file = new File("../ItemsFile/Usable/" + input);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("name: " + input);
                bufferedWriter.newLine();
                while ((line = bufferedReader.readLine()) != null) {
                    do{
                        newCardMessages.printer(line);
                        request.getNewLine();
                        input = request.getCommand();
                    }while (checkSyntax(lineNumber,input));
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
}