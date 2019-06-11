package view.Graphic;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BattleHeaderGraphic {
    Group root;
    Group rightHeader = new Group();
    Group leftHeader = new Group();

    public BattleHeaderGraphic(Group root) {
        this.root = root;
        test();
        root.getChildren().addAll(rightHeader, leftHeader);
    }

    private void addTextWithShadow(String textString, double x, double y, Group group){
        Text text = new Text(textString);
        text.relocate(x, y);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 27));
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0f);
        text.setEffect(dropShadow);
        group.getChildren().add(text);
    }

    private void addPortraitBorder(double x, double y, Group group){
        GeneralGraphicMethods.addImage(group, "pics/profile/speech_portrait_abyssianalt@2x.png", x + 13,  y + 13, 100, 100);
        ImageView imageView1 = GeneralGraphicMethods.addImage(group, "pics/battle_catagorized/general_portrait_border@2x.png", x, y, 130, 130);
        ImageView imageView2 = GeneralGraphicMethods.addImage(group, "pics/battle_catagorized/general_portrait_border_highlight@2x.png", x, y, 130, 130);
        group.getChildren().remove(imageView2);
        imageView2.setOnMouseExited(event -> {
            group.getChildren().add(imageView1);
            group.getChildren().remove(imageView2);
        });
        imageView1.setOnMouseEntered(event -> {
            group.getChildren().add(imageView2);
            group.getChildren().remove(imageView1);
        });
    }


    private void addMana(double x, double y, int numberOfMana, Group group){
        for(int i = 0; i < 9; i++){
            if(i < numberOfMana)
            GeneralGraphicMethods.addImage(group,"pics/icon_mana@2x.png", x + i * 28, y, 25, 25);
            else
                GeneralGraphicMethods.addImage(group,"pics/icon_mana_inactive@2x.png", x + i * 28, y, 25, 25);
        }
    }
    /*
    private void addManaRight(double x, double y, int numberOfMana){
        double round = 0;
        for(int i = 0; i < 9; i++){
            round=-Math.sqrt(660000-Math.pow(x + i * 28-900,2))+900;
            GeneralGraphicMethods.addImage(group,"pics/icon_mana@2x.png",
                    x + i * 28, round, 25, 25);
        }
    }
    public void addManaLeft(double x, double y, int numberOfMana){
        double round = 0;
        for(int i = 0; i < 9; i++){
            round=-Math.sqrt(700000-Math.pow(x + i * 28-500,2))+920;
            GeneralGraphicMethods.addImage(group,"pics/icon_mana@2x.png",
                    x + i * 28, round, 25, 25);
        }
    }
    */

    public void makeHeader() {
        //Left header:
        addTextWithShadow("YOU", 248, 78, leftHeader);
        addMana(245,100 ,4, leftHeader);
        addPortraitBorder(120, 25, leftHeader);

        /*
        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();
        perspectiveTransform.setUlx(115);
        perspectiveTransform.setUly(30);
        perspectiveTransform.setUrx(546);
        perspectiveTransform.setUry(21);
        perspectiveTransform.setLlx(115);
        perspectiveTransform.setLly(200);
        perspectiveTransform.setLrx(546);
        perspectiveTransform.setLry(177);

        leftHeader.setEffect(perspectiveTransform);
*/
        //Right header:
        addTextWithShadow("OPPONENT", 1010, 78, rightHeader);
        addMana(911,100,5, rightHeader);
        addPortraitBorder(1165, 25, rightHeader);
        /*
        addManaLeft(248, 96, 9);
        addManaRight(920, 96, 9);
        */
    }

    public void test() {
        makeHeader();
    }
}
