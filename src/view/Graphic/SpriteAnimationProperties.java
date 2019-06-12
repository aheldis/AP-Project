package view.Graphic;

import javafx.scene.image.Image;
import model.account.FilesType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpriteAnimationProperties {
    public int rows;
    public int columns;
    public int count;
    public long millis;
    public double widthOfEachFrame;
    public double heightOfEachFrame;
    public String gifPath = null;
    public String spriteSheetPath = null;

    public SpriteAnimationProperties(String name, FilesType filesType, int count) {
        if(filesType.equals(FilesType.MINION) || filesType.equals(FilesType.HERO)){
            gifPath = "pics/" + filesType.getName() + "/" + name + ".gif";
            spriteSheetPath = "pics/" + filesType.getName() + "/" + name + ".png";

            try {
                Image gifImage = new Image(new FileInputStream(gifPath));
                double gifHeight = gifImage.getHeight();
                double gifWidth = gifImage.getWidth();

                Image pngImage = new Image(new FileInputStream(spriteSheetPath));
                double pngHeight = pngImage.getHeight();
                double pngWidth = pngImage.getWidth();

                this.count = count;
                rows = (int) (pngHeight / gifHeight);
                columns = (int) (pngWidth / gifWidth);
                millis = count * 100; //todo ZAHRA ino set kon khodet

                widthOfEachFrame = gifWidth;
                heightOfEachFrame = gifHeight + 1;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(filesType.equals(FilesType.SPELL)){
            spriteSheetPath = "pics/Spell/" + name + ".png";
            rows = 5;
            columns = 5;
            this.count = count;
            millis = 3000;
            widthOfEachFrame = 48;
            heightOfEachFrame = 48;
        }
        if(filesType.equals(FilesType.USABLE)){
            spriteSheetPath = "pics/items/" + name + ".png";
            rows = 5;
            columns = 6;
            this.count = 29;
            millis = 3000;
            widthOfEachFrame = 48;
            heightOfEachFrame = 48;

        }
    }
}
