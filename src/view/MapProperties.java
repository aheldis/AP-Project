package view;

import model.land.LandOfGame;

public class MapProperties {
    public double cellWidth;
    public double cellHeight;
    public double gap = 4;
    public double ulx;
    public double uly;
    public double urx;
    public double ury;
    public double llx;
    public double lly;
    public double lrx;
    public double lry;

    public void init() {
        setCellSize();
        double changeXRatio = GeneralGraphicMethods.getRatioX() / 0.7563451776649747;
        double changeYRatio = GeneralGraphicMethods.getRatioY() / 0.8592592592592593 ;
        cellWidth /= changeXRatio;
        cellHeight /= changeYRatio;
        ulx /= changeXRatio;
        uly /= changeYRatio;
        urx /= changeXRatio;
        ury /= changeYRatio;
        llx /= changeXRatio;
        lly /= changeYRatio;
        lrx /= changeXRatio;
        lry /= changeYRatio;
    }

    public void setCellSize() {
        cellWidth = (((urx + lrx) / 2 - (ulx + llx) / 2) - gap * (LandOfGame.getNumberOfColumns() - 1)) / LandOfGame.getNumberOfColumns();
        cellHeight = ((lly - uly) - gap * (LandOfGame.getNumberOfColumns() - 1)) / LandOfGame.getNumberOfColumns();
    }
/*
    public static void main(String[] args) {
        Request request = new Request(StateType.BATTLE);
        request.getNewLine();
        int numberOfMap = Integer.parseInt(request.getCommand());
        MapProperties mapProperties = new MapProperties();
        mapProperties.ulx = Double.parseDouble(request.getCommand());
        request.getNewLine();
        mapProperties.uly = Double.parseDouble(request.getCommand());
        request.getNewLine();
        mapProperties.urx = Double.parseDouble(request.getCommand());
        request.getNewLine();
        mapProperties.ury = Double.parseDouble(request.getCommand());
        request.getNewLine();
        mapProperties.llx = Double.parseDouble(request.getCommand());
        request.getNewLine();
        mapProperties.lly = Double.parseDouble(request.getCommand());
        request.getNewLine();
        mapProperties.lrx = Double.parseDouble(request.getCommand());
        request.getNewLine();
        mapProperties.lry = Double.parseDouble(request.getCommand());
        
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
    }*/
}
