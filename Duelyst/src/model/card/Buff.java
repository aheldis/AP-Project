package model.card;

import com.gilecode.yagson.YaGson;
import model.account.Shop;

import java.io.*;
import java.util.ArrayList;

//todo check saba

public class Buff {
    private static ArrayList<Buff> buffs;
    private static String pathOfFiles = Shop.getPathOfFiles() + "Buff";
    private String name;
    private boolean goodBuff;
    private boolean haveUnAffect;
    private int apChange = 0;
    private int hpChange = 0;
    private boolean canMove = true;
    private boolean canAttack = true;
    private boolean canCounterAttack = true;
    private boolean hpChangeAfterAttack = false;
    private boolean firstTime = true;

    static {
        File folder = new File(pathOfFiles);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            makeNewFromFile(listOfFiles[i].getPath());
        }
    }

    static public Buff getNewBuffByName(String name) {
        for (Buff buff : buffs) {
            if (buff.getName().equals(name)) {
                makeNewFromFile(pathOfFiles + "/" + buff.getName());
                buffs.remove(buff);
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

    public String getName() {
        return name;
    }

    public static void makeNewFromFile(String path) {
        try {
            InputStream input = new FileInputStream(path);
            Reader reader = new InputStreamReader(input);

            YaGson mapper = new YaGson();
            Buff buff = mapper.fromJson(reader, Buff.class);
            buffs.add(buff);
        } catch (Exception e) {

        }
    }

    public void affect(Card card) {
        if(firstTime) {
            card.changeAp(apChange);
            card.changeHp(hpChange);
            firstTime = false;
        }
        if (canMove == false)
            card.setCanMove(false, 1);
        if (canAttack == false)
            card.setCanAttack(false, 1);
        if (canCounterAttack == false)
            card.setCanCounterAttack(false, 1);
        if (hpChangeAfterAttack == true)
            card.setHpChangeAfterAttack(1);
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
}
