package me.supernova1992.applicationplugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;



public class FirstPrompt extends StringPrompt {

	ServerForms plugin;
	static String formName = ServerForms.formName();
	static String[] app;
	static String input;
	static String player;
	static String[] question;
	static String[] type;
	static Integer num;
	static Integer j;
	
	public static String getInput(){
		
		return input;
	}
	
	public FirstPrompt(ServerForms instance, Integer i, String[] qs, String [] ts){
		
		plugin = instance;
		num = i;
		j = i+1;
		question = qs;
		type = ts;
		
	}
	@Override
	public String getPromptText(ConversationContext arg0){
		
		return ChatColor.BLUE + question[num] + ChatColor.RED +"Type quit to exit.";
		
	}

	@Override
	public Prompt acceptInput(ConversationContext c, String s){
		Conversable cpo = c.getForWhom();
		cpo.sendRawMessage("Thank you.");
		 
		player = cpo.toString();
		 input = s;
		 
		 
		 try {
			    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("inputs.txt", true)));
			    out.println(input);
			    out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (type[j].equalsIgnoreCase("integer")){
			num++;
		return new AgePrompt(plugin, num, question, type);
		}
		if(type[j].equalsIgnoreCase("String")){
			num++;
			return new FirstPrompt(plugin, num, question, type);
			
		} 
		if(type[j].equals("END_OF_CONVERSATION")){
			app = Application.applicationValues();
			
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
