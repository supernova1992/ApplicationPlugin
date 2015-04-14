package me.supernova1992.serverforms;

import org.bukkit.ChatColor;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;

public class AgePrompt {

    private Integer input;
    private String[] questions;
    private String[] types;
    private Integer number;
    private Integer j;

    public AgePrompt(Integer number, String[] questions, String[] types) {
        this.number = number;
        this.j = number + 1;
        this.questions = questions;
        this.types = types;

    }

    public Integer getInput() {
        return input;
    }

    public String getPromptText(ConversationContext arg0) {
        return ChatColor.BLUE + questions[number] + ChatColor.RED + "Type 'quit' to exit.";
    }

    @Override
    public Prompt acceptValidatedInput(ConversationContext c, Number n) {
        Conversable cpo = c.getForWhom();
        cpo.sendRawMessage("Thank you.");

        input = (Integer) n;

        String input = this.input.toString();
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("inputs.txt", true)));
            out.println(input);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (types[j].equalsIgnoreCase("integer")) {
            number++;
            return new AgePrompt(number, questions, types);
        }
        if (types[j].equalsIgnoreCase("String")) {
            number++;
            return new FirstPrompt(number, questions, types);

        }
        if (types[j].equals("END_OF_CONVERSATION")) {
            String[] app = Application.applicationValues();

            Application.AnalyzeApp(app);

            String p = "inputs.txt";
            Path path = Paths.get(p);

            try {
                Files.delete(path);
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", path);
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", path);
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
            }
            return END_OF_CONVERSATION;
        }
        return END_OF_CONVERSATION;
    }

}
