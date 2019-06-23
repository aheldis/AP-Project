package model.card.makeFile;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.scene.control.TextField;
import model.account.Account;
import model.account.FilesType;
import model.account.Shop;
import model.card.Buff;
import view.Graphic.NewCardGraphic;
import view.NewCardMessages;
import view.Request;
import view.enums.StateType;

import java.io.*;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class MakeNewFile {
    private static NewCardMessages newCardMessages = NewCardMessages.getInstance();
    private static Request request = new Request(StateType.ACCOUNT_MENU);
    private static ArrayList<String> fieldNames = new ArrayList<>();
    private static ArrayList<String> changeFieldNames = new ArrayList<>();
    private static ArrayList<String> targetFieldNames = new ArrayList<>();
    private static ArrayList<String> fieldAnnotatedTyped = new ArrayList<>();
    private static ArrayList<String> buffFieldNames = new ArrayList<>();

    private static ArrayList<HashMap<String, TextField>> hashMaps = new ArrayList<>(); //Be tartib: 0: khod card - 1: change - 2: target - 3 be bad buffHa
    private static int numberOfBuffHashmap = 3;
    private static int spriteNumber = 0;
    private static int spriteNumberCount = 0;


    public static void setSpriteNumber(int spriteNumber, int spriteNumberCount) {
        MakeNewFile.spriteNumber = spriteNumber;
        MakeNewFile.spriteNumberCount = spriteNumberCount;
    }

    public static void setHashMaps(ArrayList<HashMap<String, TextField>> hashMaps) {
        MakeNewFile.hashMaps = hashMaps;
    }

    public static void addPicture(FilesType typeOfFile, String name, Object object) {
        String pathOfFolder = "pics/" + typeOfFile.getName() + "/";
        if (typeOfFile != FilesType.SPELL)
            copyFile(pathOfFolder + "defaults/" + spriteNumber + ".gif",
                    pathOfFolder + name + ".gif");
        copyFile(pathOfFolder + "defaults/" + spriteNumber + ".png",
                pathOfFolder + name + ".png");


        ((CardCopy) object).setCountOfAnimation(spriteNumberCount);
        ((CardCopy) object).setPathOfAnimation(pathOfFolder + name + ".png");
        if (typeOfFile != FilesType.SPELL)
            ((CardCopy) object).setPathOfThePicture(pathOfFolder + name + ".gif");

    }

    public static void makeNewCard(Account account, FilesType typeOfFile) {
        try {
            String name = hashMaps.get(0).get("name").getText();
            if (name.equals("")) {
                NewCardGraphic.setError("Cannot make card!");
                return;
            }
            String path = Shop.getPathOfFiles() + typeOfFile.getName() + "/" + name + ".json";
            File file = new File(path);
            if (file.exists()) {
                NewCardGraphic.setError("Card with this name already exist!");
                return;
            }

            Object object = fillObject("model.card.makeFile.CardCopy", typeOfFile, 0);
            if (typeOfFile == FilesType.SPELL || typeOfFile == FilesType.MINION) {
                Object change = fillObject("model.card.makeFile.ChangeCopy", null, 1);
                Object target = fillObject("model.card.makeFile.TargetCopy", null, 2);
                ((CardCopy) object).setChange((ChangeCopy) change);
                ((CardCopy) object).setTarget((TargetCopy) target);
            }
            switch (typeOfFile) {
                case HERO:
                    addPicture(FilesType.HERO, name, object);
                    break;
                case SPELL:
                    addPicture(FilesType.SPELL, name, object);
                    break;
                case MINION:
                    addPicture(FilesType.MINION, name, object);
                    break;
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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Shop.getInstance().makeNewFromFile(path, typeOfFile);
                    if (account != null)
                        account.getCollection().addToCards(Shop.getInstance().getNewCardByName(name));
                }
            }).start();
            return;
        } catch (Exception e) {
            NewCardGraphic.setError("Cannot make card!");

        }
    }

    public static Object fillObject(String className, FilesType typeOfFile, int hashMapNumber) {
        try {
            Field[] fields = Class.forName(className).getFields();
            Class<?> fileClass = Class.forName(className);
            Constructor<?> constructor = fileClass.getConstructor();
            Object object = constructor.newInstance();

            for (Field field : fields) {
                String fieldName = field.getName();
                if (ignoreField(fieldName, typeOfFile))
                    continue;

                try {
                    AnnotatedType annotatedType = field.getAnnotatedType();
                    if (annotatedType.getType().getTypeName().equals("int")) {
                        field.set(object, Integer.parseInt(hashMaps.get(hashMapNumber).get(fieldName).getText()));
                    } else if (annotatedType.getType().getTypeName().equals("boolean")) {
                        field.set(object, Boolean.parseBoolean(hashMaps.get(hashMapNumber).get(fieldName).getText()));
                    } else if (annotatedType.getType().getTypeName().equals("java.lang.String")) {
                        field.set(object, hashMaps.get(hashMapNumber).get(fieldName).getText());
                    } else if (annotatedType.getType().getTypeName().equals("java.util.HashMap<java.lang.String, java.util.ArrayList<java.lang.Integer>>")) {
                        HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
                        int number = Integer.parseInt(hashMaps.get(hashMapNumber).get("number of buffs").getText());
                        for (int i = 0; i < number; i++) {

                            String buffName = hashMaps.get(numberOfBuffHashmap).get("name").getText();
                            String pathOfBuff = Buff.getPathOfFiles() + "/" + buffName + ".json";

                            boolean makeNewBuff = true;
                            if (new File(pathOfBuff).exists()) {
                                makeNewBuff = false;
//                                System.out.println("this buff already exist so you can't change properties");
                            }

                            int count = Integer.parseInt(hashMaps.get(numberOfBuffHashmap).get("how many of this buff").getText());
                            int num = Integer.parseInt(hashMaps.get(numberOfBuffHashmap).get("for how many turn").getText());
                            System.out.println("count = " + count);
                            System.out.println("num = " + num);
                            ArrayList<Integer> array = new ArrayList<>();
                            for (int j = 0; j < count; j++) {
                                array.add(num);
                            }
                            if (makeNewBuff) {
                                Object object1 = fillObject("model.card.makeFile.BuffCopy", FilesType.BUFF, numberOfBuffHashmap);
                                toJson(object1, pathOfBuff);
                                changeInFile(pathOfBuff, "@type", "model.card.Buff");
                            }
                            numberOfBuffHashmap++;

                            hashMap.put(buffName, array);
                        }
                        field.set(object, hashMap);
                    }

                } catch (Exception e) {
                    // e.printStackTrace();
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

    public static void changeInFile(String path, String fieldName, String content) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                if (line.contains(fieldName))
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

    public static ArrayList<String> getBuffFieldNames() {
        return buffFieldNames;
    }

    public static ArrayList<String> getFieldNames(FilesType typeOfFile) {
        fieldNames.clear();
        changeFieldNames.clear();
        targetFieldNames.clear();
        buffFieldNames.clear();
        fillFieldNames(typeOfFile);
        return fieldNames;
    }

    public static void fillFieldNames(FilesType typeOfFile) {
        fieldNames.clear();
        fieldAnnotatedTyped.clear();

        fillFieldNamesOfObject(fieldNames, "model.card.makeFile.CardCopy", typeOfFile);

        if (typeOfFile == FilesType.SPELL || typeOfFile == FilesType.MINION) {
            changeFieldNames.add("__Change:");
            fillFieldNamesOfObject(changeFieldNames, "model.card.makeFile.ChangeCopy", null);
            targetFieldNames.add("__Target:");
            fillFieldNamesOfObject(targetFieldNames, "model.card.makeFile.TargetCopy", null);
            buffFieldNames.add("__Buff:");
            fillFieldNamesOfObject(buffFieldNames, "model.card.makeFile.BuffCopy", null);
        }
    }

    public static void fillFieldNamesOfObject(ArrayList<String> fieldNames, String className, FilesType typeOfFile) {
        try {
            Field[] fields = Class.forName(className).getFields();

            for (Field field : fields) {
                if (ignoreField(field.getName(), typeOfFile))
                    continue;

                fieldNames.add(field.getName());
                fieldAnnotatedTyped.add(field.getAnnotatedType().getType().getTypeName());
                AnnotatedType annotatedType = field.getAnnotatedType();
                if (annotatedType.getType().getTypeName().equals("java.util.HashMap<java.lang.String, java.util.ArrayList<java.lang.Integer>>")) {
                    fieldNames.remove(field.getName());
                    fieldNames.add("number of buffs");
                    fieldAnnotatedTyped.add("java.lang.String");
                    buffFieldNames.add("how many of this buff");
                    buffFieldNames.add("for how many turn");
                }
            }

        } catch (Exception e) {
            newCardMessages.printLine(e.getMessage());
        }
    }

    private static boolean ignoreField(String fieldName, FilesType typeOfFile) {
        if (fieldName.equals("coolDown") && typeOfFile != FilesType.HERO)
            return true;
        if (fieldName.equals("ActivationTimeOfSpecialPower") && typeOfFile != FilesType.MINION)
            return true;
        if (!fieldName.equals("cost") && !fieldName.equals("name") && typeOfFile == FilesType.SPELL)
            return true;
        return false;
    }

    public static ArrayList<String> getChangeFieldNames() {
        return changeFieldNames;
    }

    public static ArrayList<String> getTargetFieldNames() {
        return targetFieldNames;
    }

    public static ArrayList<String> getFieldAnnotatedTyped() {
        return fieldAnnotatedTyped;
    }

    private static void copyFile(String sourcePath, String destinationPath) {
        try {
            File sourceFile = new File(sourcePath);
            File destinationFile = new File(destinationPath);
            Files.copy(sourceFile.toPath(), destinationFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}