package model.card.makeFile;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.account.FilesType;
import model.account.Shop;
import view.NewCardMessages;
import view.Request;
import view.enums.StateType;

import java.io.FileWriter;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class MakeNewFile {

    private static NewCardMessages newCardMessages = NewCardMessages.getInstance();
    private static Request request = new Request(StateType.BATTLE);//mohem ni chi bashe mikham az scanneresh estefade konam
    private static String path;

//    private static String checkSyntax(FileWriter fileWriter, String line) {
//    }


    public static void makeNewCardFile() {

        String input = null;
        do {
            newCardMessages.printLine("type: (Hero/Minion/Spell/Buff)");
            request.getNewLine();
            input = request.getCommand();
        } while (FilesType.getEnum(input) == null);

        FilesType typeOfFile = FilesType.getEnum(input);
        newCardMessages.printLine("name: ");
        request.getNewLine();
        input = request.getCommand();
        String nameOfFile = input;

        path = Shop.getPathOfFiles() + typeOfFile.getName() + "/" + nameOfFile + ".json";

        Object object = null;
        if (typeOfFile == FilesType.BUFF) {
            object = fillObject("BuffCopy");
        } else { //minion hero spell

            object = fillObject("CardCopy");
            Object change = fillObject("ChangeCopy");
            Object target = fillObject("TargetCopy");

            ((CardCopy) object).setChange((ChangeCopy) change);
            ((CardCopy) object).setTarget((TargetCopy) target);
        }

        try {
            YaGson altMapper = new YaGsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(path);
            altMapper.toJson(object, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
           newCardMessages.printLine(e.getMessage());
        }

    }

    public static Object fillObject(String className) {
        try {
            Field[] fields = Class.forName(className).getFields();

            Class<?> fileClass = Class.forName(className);
            Constructor<?> constructor = fileClass.getConstructor();
            Object object = constructor.newInstance();

            for (Field field : fields) {
               newCardMessages.printLine("enter " + field.getName() + " (" + field.getAnnotatedType().getType().getTypeName() + "):");
                try {
                    AnnotatedType annotatedType = field.getAnnotatedType();

                    if (annotatedType.getType().getTypeName().equals("int")) {
                        request.getNewLine();
                        field.set(object, Integer.parseInt(request.getCommand()));
                    } else if (annotatedType.getType().getTypeName().equals("boolean")) {
                        request.getNewLine();
                        field.set(object, Boolean.parseBoolean(request.getCommand()));
                    } else if (annotatedType.getType().getTypeName().equals("java.util.ArrayList<java.lang.String>")) {
                        ArrayList<String> arr = new ArrayList<>();
                       newCardMessages.printLine("enter number of array items: ");
                        request.getNewLine();
                        int number = Integer.parseInt(request.getCommand());
                        for (int i = 0; i < number; i++) {
                            request.getNewLine();
                            arr.add(request.getCommand());
                        }
                        field.set(object, arr);
                    } else if (annotatedType.getType().getTypeName().equals("java.lang.String")) {
                        request.getNewLine();
                        field.set(object, request.getCommand());
                    } else if (annotatedType.getType().getTypeName().equals("java.util.HashMap<java.lang.String, java.lang.Integer>")) {
                       newCardMessages.printLine("enter number of buffs: ");
                        request.getNewLine();
                        HashMap<String, Integer> hashMap = new HashMap<>();
                        int number = Integer.parseInt(request.getCommand());
                        for (int i = 0; i < number; i++) {
                           newCardMessages.printLine("enter buff name(holy/power/poison/weakness/stun/disarm) CORRECTLY");
                            request.getNewLine();
                            String buffName = request.getCommand();
                           newCardMessages.printLine("for How Many Turn");
                            request.getNewLine();
                            int num = Integer.parseInt(request.getCommand());
                            hashMap.put(buffName, num);
                        }
                        field.set(object, hashMap);
                    }

                } catch (Exception e) {
                   newCardMessages.printLine(e.getMessage());
                }
            }

            return object;

        } catch (Exception e) {
           newCardMessages.printLine("other error");
           newCardMessages.printLine(e.getMessage());
        }
        return null;
    }
}
