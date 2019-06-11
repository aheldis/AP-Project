package view.Graphic;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import model.account.FilesType;
import model.card.makeFile.NewFileAsli;

import javax.swing.text.html.ImageView;
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
    public String pngPath = null;

    public SpriteAnimationProperties(String name, FilesType filesType) {
        if(filesType.equals(FilesType.MINION)){
            gifPath = "pics/Minion/" + name + ".gif";
            pngPath = "pics/Minion/" + name + ".png";

            try {
                Image gifImage = new Image(new FileInputStream(gifPath));
                double gifHeight = gifImage.getHeight();
                double gifWidth = gifImage.getWidth();

                Image pngImage = new Image(new FileInputStream(pngPath));
                double pngHeight = pngImage.getHeight();
                double pngWidth = pngImage.getWidth();

                rows = (int) (pngHeight / gifHeight);
                columns = (int) (pngWidth / gifWidth);
                count = rows * columns;
                millis = count * 100; //todo ZAHRA ino set kon khodet

                widthOfEachFrame = gifWidth;
                heightOfEachFrame = gifHeight;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(filesType.equals(FilesType.SPELL)){
            pngPath = "pics/Spell/" + name + ".png";
            rows = 5;
            columns = 5;
            count = 25;
            millis = 3000;
            widthOfEachFrame = 51;
            heightOfEachFrame = 51;
        }
    }
}
