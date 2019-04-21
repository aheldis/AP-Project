package model.account;


import view.NewCardMessages;
import view.Request;

import java.io.*;

public class CreateNew {
    private String path = "D:\\project-Duelyst\\Duelyst";

    public void makeNewCardByPlayer() throws IOException {
        Request request = new Request("menu");
        request.getNewLine();
        String command = request.getCommand();
        if (command.equals("Spell")) {
            NewCardMessages message = NewCardMessages.getInstance();
            request.getNewLine();
            command = request.getCommand();
            command = command.concat(path);

            File file = new File(command);
            if (!file.createNewFile()) {
                System.out.println("Card already exists.");
                return;
            }
            FileWriter writer = new FileWriter(file);
            do {
                message.showFormatForSpells("cost");
                request.getNewLine();
                command = request.getCommand();
            } while (!command.matches("cost: \\d+"));
            writer.write(request.getCommand() + "\n");

            do {
                message.showFormatForSpells("Mp");
                request.getNewLine();
                command = request.getCommand();
            } while (!command.matches("Mp: \\d+"));
            writer.write(request.getCommand() + "\n");

            do {
                message.showFormatForSpells("ApChange");
                request.getNewLine();
                command = request.getCommand();
            } while (!command.matches("ApChanges-TurnOfEffect: \\d+ - \\d+"));
            writer.write(request.getCommand() + "\n");

            do {
                message.showFormatForSpells("HpChange");
                request.getNewLine();
                command = request.getCommand();
            } while (!command.matches("HpChanges-TurnOfEffect: \\d+ - \\d+"));
            writer.write(request.getCommand() + "\n");

            do {
                message.showFormatForSpells("buff");
                request.getNewLine();
                command = request.getCommand();
            } while (!command.matches("Buffs-TurnOfEffect: \\w+ - \\d+"));
            writer.write(request.getCommand() + "\n");

            do {
                message.showFormatForSpells("target");
                request.getNewLine();
                command = request.getCommand();
            } while (!command.matches("target: (Enemy|Friend|(Enemy\\|Friend) - All|One|Range\\(m-m',n-n'\\) - Hero|Card)|(square(m*n))"));
            writer.write(request.getCommand() + "\n");

            do {
                message.showFormatForSpells("desc");
                request.getNewLine();
                command = request.getCommand();
            } while (!command.matches("desc: (\\w+)+"));
            writer.write(request.getCommand() + "\n");


            writer.close();


        } else if (command.equals("Minion")) {

        } else if (command.equals("Hero")) {

        } else if (command.equals("Item")) {

        }
    }
}
