package model.requirment;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
