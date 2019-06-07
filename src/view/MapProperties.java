package view;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.card.makeFile.MakeNewFile;
import view.enums.StateType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MapProperties {
    public int cellWidth;
    public int cellHeight;
    public int gap;
    public int ulx;
    public int uly;
    public int urx;
    public int ury;
    public int llx;
    public int lly;
    public int lrx;
    public int lry;

    public static void main(String[] args) {
        Request request = new Request(StateType.BATTLE);
        request.getNewLine();
        int numberOfMap = Integer.parseInt(request.getCommand());
        MapProperties mapProperties = new MapProperties();
        request.getNewLine();
        mapProperties.cellWidth = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.cellHeight = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.gap = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.ulx = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.uly = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.urx = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.ury = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.llx = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.lly = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.lrx = Integer.parseInt(request.getCommand());
        request.getNewLine();
        mapProperties.lry = Integer.parseInt(request.getCommand());


        String path = "pics/maps_categorized/map" + numberOfMap + "/property.json";
        YaGson altMapper = new YaGsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(path);
            altMapper.toJson(mapProperties, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
