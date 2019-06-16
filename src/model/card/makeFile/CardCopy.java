package model.card.makeFile;


public class CardCopy {
    public String name;
    public int cost;
    public int mp;
    public int hp;
    public int ap;
    public String counterAttack;
    public int attackRange;
    public String ActivationTimeOfSpecialPower;
    public int coolDown;

    private String pathOfThePicture;
    private String pathOfAnimation;
    private int countOfAnimation = 16;


    private ChangeCopy change = new ChangeCopy();
    private TargetCopy target = new TargetCopy();

    public void setChange(ChangeCopy change) {
        this.change = change;
    }
    public void setTarget(TargetCopy target) {
        this.target = target;
    }

    public void setPathOfThePicture(String pathOfThePicture) {
        this.pathOfThePicture = pathOfThePicture;
    }

    public void setPathOfAnimation(String pathOfAnimation) {
        this.pathOfAnimation = pathOfAnimation;
    }

    public void setCountOfAnimation(int countOfAnimation) {
        this.countOfAnimation = countOfAnimation;
    }


}
