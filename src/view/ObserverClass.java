package view;

import javax.swing.text.html.ImageView;
import java.util.Observable;
import java.util.Observer;

public class ObserverClass implements Observer {
    ImageView ap,hp,card;

    public void addToObserver(ImageView hp,ImageView ap,ImageView card){
        this.hp=hp;
        this.ap=ap;
        this.card=card;
    }

    @Override
    public void update(Observable o, Object arg) {
        //if an observable changes it comes here

        if(o instanceof ObservableClass){//i just de
           // ((ObservableClass) o).getImageView().relocate();
        }
    }
}
