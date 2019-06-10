package model.card.makeFile;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.account.FilesType;
import model.account.Shop;
import model.card.Buff;
import view.NewCardMessages;
import view.Request;
import view.enums.StateType;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class NewFileAsli {
    private static NewCardMessages newCardMessages = NewCardMessages.getInstance();
    private static Request request = new Request(StateType.ACCOUNT_MENU);

    public static void makeNewCard() {

        newCardMessages.printLine("enter type: (Spell/Minion/Hero)");
        request.getNewLine();
        String type = request.getCommand();
        FilesType typeOfFile = FilesType.getEnum("type");

        newCardMessages.printLine("enter name: ");
        request.getNewLine();
        String name = request.getCommand();
        String path = Shop.getPathOfFiles() + typeOfFile.getName() + "/" + name + ".json";
        Object object = fillObject("CardCopy", typeOfFile, name);

        if (typeOfFile == FilesType.SPELL || typeOfFile == FilesType.MINION) {
            Object change = fillObject("ChangeCopy", null, null);
            Object target = fillObject("TargetCopy", null, null);

            ((CardCopy) object).setChange((ChangeCopy) change);
            ((CardCopy) object).setTarget((TargetCopy) target);
        }

        toJson(object, path);
    }

    public static Object fillObject(String className, FilesType typeOfFile, String name) {
        try {
            Field[] fields = Class.forName(className).getFields();
            Class<?> fileClass = Class.forName(className);
            Constructor<?> constructor = fileClass.getConstructor();
            Object object = constructor.newInstance();

            for (Field field : fields) {
                if (field.getName().equals("name") && name != null) {
                    field.set(object, name);
                    continue;
                }
                if (field.getName().equals("coolDown") && typeOfFile != FilesType.HERO)
                    continue;
                if (field.getName().equals("ActivationTimeOfSpecialPower") && typeOfFile != FilesType.MINION)
                    continue;
                if (!field.getName().equals("cost") && typeOfFile == FilesType.SPELL)
                    continue;
                newCardMessages.printLine("enter " + field.getName());
                try {
                    AnnotatedType annotatedType = field.getAnnotatedType();
                    if (annotatedType.getType().getTypeName().equals("int")) {
                        request.getNewLine();
                        field.set(object, Integer.parseInt(request.getCommand()));
                    } else if (annotatedType.getType().getTypeName().equals("boolean")) {
                        request.getNewLine();
                        field.set(object, Boolean.parseBoolean(request.getCommand()));
                    } else if (annotatedType.getType().getTypeName().equals("java.lang.String")) {
                        request.getNewLine();
                        field.set(object, request.getCommand());
                    } else if (annotatedType.getType().getTypeName().equals("java.util.HashMap<java.lang.String, java.util.ArrayList<java.lang.Integer>>")) {
                        newCardMessages.printLine("enter number of buffs: ");
                        request.getNewLine();
                        HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
                        int number = Integer.parseInt(request.getCommand());
                        for (int i = 0; i < number; i++) {
                            newCardMessages.printLine("enter buff name(holy/power/poison/weakness/stun/disarm)");
                            request.getNewLine();
                            String buffName = request.getCommand();
                            String pathOfBuff = Buff.getPathOfFiles() + "/" + buffName + ".json";
                            boolean makeNewBuff = true;
                            if (new File(pathOfBuff).exists()) {
                                makeNewBuff = false;
                                System.out.println("this buff already exist so you can't change properties");
                            }
                            newCardMessages.printLine("enter how many of this buff:");
                            request.getNewLine();
                            int count = Integer.parseInt(request.getCommand());
                            ArrayList<Integer> array = new ArrayList<>();
                            for (int j = 0; j < count; j++) {
                                newCardMessages.printLine("enter for How Many Turn:");
                                request.getNewLine();
                                int num = Integer.parseInt(request.getCommand());
                                array.add(num);
                                if (makeNewBuff) {
                                    Object object1 = fillObject("BuffCopy", null, buffName);
                                    toJson(object1, pathOfBuff);
                                }
                            }
                            hashMap.put(buffName, array);
                        }
                        field.set(object, hashMap);
                    }

                } catch (Exception e) {
                    newCardMessages.printLine(e.getMessage());
                }

                return object;
            }

        } catch (Exception e) {
            newCardMessages.printLine("other error");
            newCardMessages.printLine(e.getMessage());
        }
        return null;
    }

    public static void toJson(Object object, String path) {
        try {
            //todo esm jsona classa kharabe
            YaGson altMapper = new YaGsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(path);
            altMapper.toJson(object, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            newCardMessages.printLine(e.getMessage());
        }
    }
}