package model.card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.account.FilesType;
import model.account.Shop;
import view.NewCardMessages;
import view.Request;
import view.enums.StateType;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class MakeNewCard {


//    private static String checkSyntax(FileWriter fileWriter, String line) {
//    }

    public static void makeNewCardFile() {
        NewCardMessages newCardMessages = NewCardMessages.getInstance();
        Request request = new Request(StateType.BATTLE);//mohem ni chi bashe mikham az scanneresh estefade konam
//        while (true) {
        try {

//            FileReader fileReader = new FileReader("../InfoForMakeCard/");
//            BufferedReader bufferedReader = new BufferedReader(fileReader);

//            String line = null;
            String input = null;
            do {
                newCardMessages.printLine("type: (hero/minion/spell/item)");
                request.getNewLine();
                input = request.getCommand().toLowerCase();
            } while (FilesType.getEnum(input) != null);
            FilesType typeOfFile = FilesType.getEnum(input);

            newCardMessages.printLine("name: ");
            request.getNewLine();
            input = request.getCommand();
            String nameOfFile = input;

//                if (input.equals("EXIT"))
//                    break;

            //          Card card = new Card();

            File file = new File(Shop.getPathOfFiles() + typeOfFile.getName() + File.pathSeparator + nameOfFile);

            String className = Character.toString(typeOfFile.getName().charAt(0)).toUpperCase() + typeOfFile.getName().substring(1).toLowerCase();
            Field[] fields = Class.forName(className).getFields();

            Class<?> fileClass = Class.forName(className);
            Constructor<?> constructor = fileClass.getConstructor(String.class);
            Object object = constructor.newInstance();

            for (Field field : fields) {

                newCardMessages.printLine(field.getName());
                request.getNewLine();
                field.set(object, request.getCommand());
            }

            /*
            FileWriter fileWriter = new FileWriter(file);
            while ((line = bufferedReader.readLine()) != null) {
                newCardMessages.printLine(line);

                request.getNewLine();
                //todo reflector
                input = request.getCommand();


                //todo GsonBuilder . prety prnting . create .
            }
            fileWriter.close();*/
            Gson gson = new GsonBuilder().create();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(object));

        } catch (FileNotFoundException e) {
            newCardMessages.printLine("file not found");
        } catch (Exception e) {
            newCardMessages.printLine("other error");
        }
    }


//    }

}
