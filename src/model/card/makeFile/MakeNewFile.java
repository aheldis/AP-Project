package model.card.makeFile;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import controller.RequestEnum;
import controller.Transmitter;
import controller.client.TransferController;
import model.account.FilesType;
import model.account.Shop;
import model.card.Buff;
import model.card.Card;
import view.NewCardMessages;
import view.Request;
import view.enums.ErrorType;
import view.enums.StateType;

import java.io.*;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class MakeNewFile {
    private NewCardMessages newCardMessages = NewCardMessages.getInstance();
    private ArrayList<String> fieldNames = new ArrayList<>();
    private ArrayList<String> changeFieldNames = new ArrayList<>();
    private ArrayList<String> targetFieldNames = new ArrayList<>();
    private ArrayList<String> fieldAnnotatedTyped = new ArrayList<>();
    private ArrayList<String> buffFieldNames = new ArrayList<>();
    private ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>(); //Be tartib: 0: khod card - 1: change - 2: target - 3 be bad buffHa
    private int numberOfBuffHashMap = 3;
    private int spriteNumber = 0;
    private int spriteNumberCount = 0;


    public void setSpriteNumber(int spriteNumber, int spriteNumberCount) {
        this.spriteNumber = spriteNumber;
        this.spriteNumberCount = spriteNumberCount;
    }

    private void addPicture(FilesType typeOfFile, String name, Object object) {
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

    public ErrorType makeNewCard(FilesType typeOfFile) {
        try {
            String name = hashMaps.get(0).get("name");
            if (name.equals("")) {
                //NewCardGraphic.setError("Cannot make card!");
                return ErrorType.CAN_NOT_MAKE_CARD;
            }
            String path = Shop.getPathOfFiles() + typeOfFile.getName() + "/" + name + ".json";
            File file = new File(path);
            if (file.exists()) {
                //NewCardGraphic.setError("Card with this name already exist!");
                return ErrorType.CARD_WITH_THIS_NAME_EXIST;
            }

            Object object = fillObject("model.card.makeFile.CardCopy", typeOfFile, 0);
            if (typeOfFile == FilesType.SPELL || typeOfFile == FilesType.MINION) {
                Object change = fillObject("model.card.makeFile.ChangeCopy", null, 1);
                Object target = fillObject("model.card.makeFile.TargetCopy", null, 2);
                assert object != null;
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

            new Thread(() -> {
                Card card = Shop.getInstance().getNewCardByName(name);
                if (card == null) {
                    Shop.getInstance().makeNewFromFile(path, typeOfFile);
                    card = Shop.getInstance().getNewCardByName(name);
                }
                Transmitter transmitter = new Transmitter();
                transmitter.card = card;
                TransferController.main(RequestEnum.NEW_CARD_ID, transmitter);
            }).start();
        } catch (Exception e) {
            //NewCardGraphic.setError("Cannot make card!");
            return ErrorType.CAN_NOT_MAKE_CARD;
        }
        return null;
    }

    private Object fillObject(String className, FilesType typeOfFile, int hashMapNumber) {
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
                    switch (annotatedType.getType().getTypeName()) {
                        case "int":
                            field.set(object, Integer.parseInt(hashMaps.get(hashMapNumber).get(fieldName)));
                            break;
                        case "boolean":
                            field.set(object, Boolean.parseBoolean(hashMaps.get(hashMapNumber).get(fieldName)));
                            break;
                        case "java.lang.String":
                            field.set(object, hashMaps.get(hashMapNumber).get(fieldName));
                            break;
                        case "java.util.HashMap<java.lang.String, java.util.ArrayList<java.lang.Integer>>":
                            HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
                            int number = Integer.parseInt(hashMaps.get(hashMapNumber).get("number of buffs"));
                            for (int i = 0; i < number; i++) {

                                String buffName = hashMaps.get(numberOfBuffHashMap).get("name");
                                String pathOfBuff = Buff.getPathOfFiles() + "/" + buffName + ".json";

                                boolean makeNewBuff = true;
                                if (new File(pathOfBuff).exists()) {
                                    makeNewBuff = false;
//                                System.out.println("this buff already exist so you can't change properties");
                                }

                                int count = Integer.parseInt(hashMaps.get(numberOfBuffHashMap).get("how many of this buff"));
                                int num = Integer.parseInt(hashMaps.get(numberOfBuffHashMap).get("for how many turn"));
                                System.out.println("count = " + count);
                                System.out.println("num = " + num);
                                ArrayList<Integer> array = new ArrayList<>();
                                for (int j = 0; j < count; j++) {
                                    array.add(num);
                                }
                                if (makeNewBuff) {
                                    Object object1 = fillObject("model.card.makeFile.BuffCopy", FilesType.BUFF, numberOfBuffHashMap);
                                    toJson(object1, pathOfBuff);
                                    changeInFile(pathOfBuff, "@type", "model.card.Buff");
                                }
                                numberOfBuffHashMap++;

                                hashMap.put(buffName, array);
                            }
                            field.set(object, hashMap);
                            break;
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

    private void toJson(Object object, String path) {
        try {
            YaGson altMapper = new YaGsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(path);
            altMapper.toJson(object, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            newCardMessages.printLine(e.getMessage());
        }
    }

    private void changeInFile(String path, String fieldName, String content) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getFieldNames(FilesType typeOfFile) {
        fieldNames.clear();
        changeFieldNames.clear();
        targetFieldNames.clear();
        buffFieldNames.clear();
        fillFieldNames(typeOfFile);
        return fieldNames;
    }

    private void fillFieldNames(FilesType typeOfFile) {
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

    private void fillFieldNamesOfObject(ArrayList<String> fieldNames, String className, FilesType typeOfFile) {
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

    private boolean ignoreField(String fieldName, FilesType typeOfFile) {
        if (fieldName.equals("coolDown") && typeOfFile != FilesType.HERO)
            return true;
        if (fieldName.equals("ActivationTimeOfSpecialPower") && typeOfFile != FilesType.MINION)
            return true;
        return !fieldName.equals("cost") && !fieldName.equals("name") && typeOfFile == FilesType.SPELL;
    }

    private void copyFile(String sourcePath, String destinationPath) {
        try {
            File sourceFile = new File(sourcePath);
            File destinationFile = new File(destinationPath);
            Files.copy(sourceFile.toPath(), destinationFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHashMaps(ArrayList<HashMap<String, String>> hashMaps) {
        this.hashMaps = hashMaps;
    }

    public ArrayList<String> getBuffFieldNames() {
        return buffFieldNames;
    }

    public ArrayList<String> getChangeFieldNames() {
        return changeFieldNames;
    }

    public ArrayList<String> getTargetFieldNames() {
        return targetFieldNames;
    }

    public ArrayList<String> getFieldAnnotatedTyped() {
        return fieldAnnotatedTyped;
    }

}