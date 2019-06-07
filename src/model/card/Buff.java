package model.card;

import com.gilecode.yagson.YaGson;
import model.account.Shop;
import model.battle.Player;

import java.io.*;
import java.util.ArrayList;

//in ke bafha ro dorost misaze o ina (getNewBuffByName va makeNewFromFile) ro check kardam

//todo for sba 2 nobat baraye json

public class Buff {
    private static ArrayList<Buff> buffs = new ArrayList<>();
    private static String pathOfFiles = Shop.getPathOfFiles() + "Buff";
    private String name;
    private boolean goodBuff;
    private boolean haveUnAffect;
    private int apChange = 0;
    private int hpChange = 0;
    private int manaChange = 0;
    private boolean canMove = true;
    private boolean canAttack = true;
    private boolean canCounterAttack = true;
    private boolean hpChangeAfterAttack = false;
    private int numberOfTimesBuffAffected = 0;
    private boolean continuous = false;
    private int forHowManyTurn = -1;
    private boolean changeInSecondTurn = false;

    static {
        File folder = new File(pathOfFiles);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            makeNewFromFile(listOfFiles[i].getPath());
        }
    }

    public static String getPathOfFiles() {
        return pathOfFiles;
    }

    static public Buff getNewBuffByName(String name, int forHowManyTurn) {
        for (Buff buff : buffs) {
            if (buff.getName().equals(name)) {
                makeNewFromFile(pathOfFiles + "/" + buff.getName() + ".json");
                buffs.remove(buff);
                if (forHowManyTurn == -1)
                    buff.continuous = true;
                else
                    buff.forHowManyTurn = forHowManyTurn;
                return buff;
            }
        }
        return null;
    }

    public void setApChange(int apChange) {
        this.apChange = apChange;
    }

    public void setHpChange(int hpChange) {
        this.hpChange = hpChange;
    }

    public void setManaChange(int manaChange) {
        this.manaChange = manaChange;
    }

    public String getName() {
        return name;
    }

    public static void makeNewFromFile(String path) {
        try {
            InputStream input = new FileInputStream(path);
            Reader reader = new InputStreamReader(input);
            YaGson mapper = new YaGson();
            Buff buff = mapper.fromJson(reader, model.card.Buff.class);
            buffs.add(buff);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void affect(Player player) {
        player.manaChange(manaChange);
    }

    public void affect(Card card) {

        if (numberOfTimesBuffAffected == 0 || continuous) {
            card.changeAp(apChange);
            if (!changeInSecondTurn)
                card.changeHp(hpChange);
        }

        if (changeInSecondTurn && numberOfTimesBuffAffected == 1)
            card.changeHp(hpChange);

        if (canMove == false)
            card.setCanMove(false, 1);
        if (canAttack == false)
            card.setCanAttack(false, 1);
        if (canCounterAttack == false)
            card.setCanCounterAttack(false, 1);
        if (hpChangeAfterAttack == true)
            card.setHpChangeAfterAttack(1);


        numberOfTimesBuffAffected++;
    }

    public void unAffect(Card card) {
        card.changeAp(-apChange);
        card.changeHp(-hpChange);
        //in chiza too khod player handle mishe
/*        if (canMove == false)
            card.setCanMove(true, 1);
        if (canAttack == false)
            card.setCanAttack(true, 1);
        if (canCounterAttack == false)
            card.setCanCounterAttack(true, 1);*/
        if (hpChangeAfterAttack == true)
            card.setHpChangeAfterAttack(-1);
    }

    public boolean isGoodBuff() {
        return goodBuff;
    }

    public boolean isHaveUnAffect() {
        return haveUnAffect;
    }

    public int getForHowManyTurn() {
        return forHowManyTurn;
    }

    public boolean isContinuous() {
        return continuous;
    }
}
