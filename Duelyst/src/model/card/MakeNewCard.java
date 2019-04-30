package model.card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.account.FilesType;
import model.account.Shop;
import view.NewCardMessages;
import view.Request;
import view.enums.StateType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class MakeNewCard {


//    private static String checkSyntax(FileWriter fileWriter, String line) {
//    }

    public static void makeNewCardFile() {
        NewCardMessages newCardMessages = NewCardMessages.getInstance();
        Request request = new Request(StateType.BATTLE);//mohem ni chi bashe mikham az scanneresh estefade konam
        try {
            String input = null;
            do {
                newCardMessages.printLine("type: (Hero/Minion/Spell/Item)");
                request.getNewLine();
                input = request.getCommand();
            } while (FilesType.getEnum(input) == null);
            FilesType typeOfFile = FilesType.getEnum(input);

            newCardMessages.printLine("name: ");
            request.getNewLine();
            input = request.getCommand();
            String nameOfFile = input;


            File file = new File(Shop.getPathOfFiles() + typeOfFile.getName() + nameOfFile + ".txt");

            String className = typeOfFile.getName();
            Field[] fields = Class.forName(className).getFields();

            Class<?> fileClass = Class.forName(className);
            Constructor<?> constructor = fileClass.getConstructor(String.class);
            Object object = constructor.newInstance();

            for (Field field : fields) {

                newCardMessages.printLine(field.getName());
                request.getNewLine();
                field.set(object, request.getCommand());
            }

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
