package view.Graphic;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
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
    }

    private void addTextWithShadow(String textString, double x, double y){
        Text text = new Text(textString);
        text.relocate(x, y);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 27));
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(3.0f);
        text.setEffect(dropShadow);
        root.getChildren().add(text);
    }

    private void addPortraitBorder(double x, double y){
        ImageView imageView1 = GeneralGraphicMethods.addImage(root, "pics/battle_catagorized/general_portrait_border@2x.png", x, y, 125, 125);
        ImageView imageView2 = GeneralGraphicMethods.addImage(root, "pics/battle_catagorized/general_portrait_border_highlight@2x.png", x, y, 125, 125);
        root.getChildren().remove(imageView2);
        imageView2.setOnMouseExited(event -> {
            root.getChildren().add(imageView1);
            root.getChildren().remove(imageView2);
        });
        imageView1.setOnMouseEntered(event -> {
            root.getChildren().add(imageView2);
            root.getChildren().remove(imageView1);
        });
    }

    public void makeHeader() {
        addTextWithShadow("YOU", 248, 87);
        addTextWithShadow("OPPONENT", 1010, 87);
        addPortraitBorder(120, 25);
        addPortraitBorder(1165, 25);
    }

    public void test() {
        makeHeader();
    }
}
