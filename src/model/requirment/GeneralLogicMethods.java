package model.requirment;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GeneralLogicMethods {
    public static void saveInFile(String path, Object object) {
        try {
            File file = new File(path);
            if (file.exists())
                file.delete();
            YaGson altMapper = new YaGsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(file);
            altMapper.toJson(object, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLines(ArrayList<String> lines, String path) {
        try {
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
