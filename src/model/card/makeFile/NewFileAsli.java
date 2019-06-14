package model.card.makeFile;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.account.Account;
import model.account.FilesType;
import model.account.Shop;
import model.battle.Player;
import model.card.Buff;
import model.card.Card;
import view.NewCardMessages;
import view.Request;
import view.enums.StateType;

import java.io.*;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewFileAsli {
    private static NewCardMessages newCardMessages = NewCardMessages.getInstance();
    private static Request request = new Request(StateType.ACCOUNT_MENU);

    public static void main(String[] args) {
        makeNewCard(null);
    }
    public static void makeNewCard(Account account) {
            newCardMessages.printLine("enter type: (Spell/Minion/Hero)");
            request.getNewLine();
            String type = request.getCommand();
            FilesType typeOfFile = FilesType.getEnum(type);

            newCardMessages.printLine("enter name: ");
            request.getNewLine();
            String name = request.getCommand();
            String path = Shop.getPathOfFiles() + typeOfFile.getName() + "/" + name + ".json";
            Object object = fillObject("model.card.makeFile.CardCopy", typeOfFile, name);

            if (typeOfFile == FilesType.SPELL || typeOfFile == FilesType.MINION) {
                Object change = fillObject("model.card.makeFile.ChangeCopy", null, null);
                Object target = fillObject("model.card.makeFile.TargetCopy", null, null);

                ((CardCopy) object).setChange((ChangeCopy) change);
                ((CardCopy) object).setTarget((TargetCopy) target);
            }

            toJson(object, path);
            switch (typeOfFile) {
                case HERO:
                    changeInFile(path, "@type", "model.card.Hero");
                    break;
                case SPELL:
                    changeInFile(path, "@type", "model.card.Spell");
                    break;
                case MINION:
                    changeInFile(path, "@type", "model.card.Minion");
                    break;
            }

            account.getCollection().addToCards((Card)object);
            Shop.getInstance().makeNewFromFile(path, typeOfFile);
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
                            }
                            if (makeNewBuff) {
                                Object object1 = fillObject("model.card.makeFile.BuffCopy", null, buffName);
                                toJson(object1, pathOfBuff);
                                changeInFile(pathOfBuff, "@type", "model.card.Buff");
                            }
                            hashMap.put(buffName, array);
                        }
                        field.set(object, hashMap);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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

    public static void changeInFile(String path, String fieldName, String content){
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                if(line.contains(fieldName))
                    line = "\"" + fieldName + "\": \"" + content + "\",";
                lines.add(line);
            }
            bufferedReader.close();
            fileReader.close();

            File file = new File(path);
            file.delete();
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            lines.forEach(line -> {
                try {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}