package view;

import javafx.scene.image.ImageView;

import java.util.Observable;

public class ObservableClass extends Observable {
    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public ObservableClass(ImageView imageView){
        this.imageView = imageView;
    }
    public void relocateImage(int x,int y){
        imageView.relocate(x,y);
        setChanged();
        notifyObservers();
    }

}

