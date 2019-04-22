package model.account;


import model.card.MakeNewCard;
import model.card.Spell;
import view.NewCardMessages;
import view.Request;

import java.io.*;

public class CardFiles {
    private String path = "D:\\project-Duelyst\\Duelyst\\CardsFile";

    public void makeNewCardByPlayer() throws IOException {
        Request request = new Request("menu");
        request.getNewLine();
        String command = request.getCommand();


        if (command.equals("Spell")) {
            MakeNewCard.makeNewCardFile("Spell");
//todo it is for check syntax don't delete these comments

//            NewCardMessages message = NewCardMessages.getInstance();
//            request.getNewLine();
//            command = request.getCommand();
//            command = path + "\\Spell\\" + command + ".txt";
//
//            File file = new File(command);
//            if (!file.createNewFile()) {
//                System.out.println("Card already exists.");
//                return;
//            }
//            FileWriter writer = new FileWriter(file);
//            writer.write("Spell\n");
//            do {
//                message.showFormatForSpells("cost");
//                request.getNewLine();
//                command = request.getCommand();
//            } while (!command.matches("cost: \\d+"));
//            writer.write(request.getCommand() + "\n");
//
//            do {
//                message.showFormatForSpells("Mp");
//                request.getNewLine();
//                command = request.getCommand();
//            } while (!command.matches("Mp: \\d+"));
//            writer.write(request.getCommand() + "\n");
//
//            do {
//                message.showFormatForSpells("ApChange");
//                request.getNewLine();
//                command = request.getCommand();
//            } while (!command.matches("ApChanges-TurnOfEffect: \\d+ - \\d+"));
//            writer.write(request.getCommand() + "\n");
//
//            do {
//                message.showFormatForSpells("HpChange");
//                request.getNewLine();
//                command = request.getCommand();
//            } while (!command.matches("HpChanges-TurnOfEffect: \\d+ - \\d+"));
//            writer.write(request.getCommand() + "\n");
//
//            do {
//                message.showFormatForSpells("buff");
//                request.getNewLine();
//                command = request.getCommand();
//            } while (!command.matches("Buffs-TurnOfEffect: \\w+ - \\d+"));
//            writer.write(request.getCommand() + "\n");
//
//            do {
//                message.showFormatForSpells("target");
//                request.getNewLine();
//                command = request.getCommand();
//            } while (!command.matches("target: (Enemy|Friend|(Enemy\\|Friend) - All|One|Range\\(m-m',n-n'\\) - Hero|Card)|(square(m*n))"));
//            writer.write(request.getCommand() + "\n");
//
//            do {
//                message.showFormatForSpells("desc");
//                request.getNewLine();
//                command = request.getCommand();
//            } while (!command.matches("desc: (\\w+)+"));
//            writer.write(request.getCommand());
//
//
//            writer.close();

        } else if (command.equals("Minion")) {
            MakeNewCard.makeNewCardFile("Minion");

        } else if (command.equals("Hero")) {
            MakeNewCard.makeNewCardFile("Hero");

        } else if (command.equals("Item")) {
            //todo
        }
    }

    public void makeCardFromFile(String fileName) {
        fileName += ".txt";
        String line = null;
        String answer;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if ((line = bufferedReader.readLine()) != null) {
                if (line.equals("Spell")) {
                    Spell spell = new Spell();
                    answer=line.split(" ")[1];
                    spell.setCost(Integer.parseInt(answer));
                    line=bufferedReader.readLine();
                    answer=line.split(" ")[1];
                    spell.setMp(Integer.parseInt(answer));
                    line=bufferedReader.readLine();
                    answer=line.split(" ")[1];
                    spell.setApChanges(Integer.parseInt(answer.split(" - ")[0]));
                    spell.setTurnForApChanges(Integer.parseInt(answer.split(" - ")[1]));
                    line=bufferedReader.readLine();
                    answer=line.split(" ")[1];
                    spell.setHpChanges(Integer.parseInt(answer.split(" - ")[0]));
                    spell.setTurnForHpChanges(Integer.parseInt(answer.split(" - ")[1]));
                    //TODO buff
                    //TODO target
                    line=bufferedReader.readLine();
                    answer=line.substring(6);
                    spell.setDescription(answer);


                } else if (line.equals("Minion")) {

                } else if (line.equals("Hero")) {

                }
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");

        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

}



