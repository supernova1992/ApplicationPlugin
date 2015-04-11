package me.supernova1992.ApplicationPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

public class AgePrompt extends NumericPrompt{

	
ApplicationPlugin plugin;

static Integer num;
static String[] app;
static String[] question;
static String[] type;
static Integer numb;
static Integer j;

 public static Integer getInput(){
	 
	 return num;
 }
 
	
	public AgePrompt(ApplicationPlugin instance, Integer i, String[] qs, String [] ts){
		
		plugin = instance;
		numb = i;
		j = i+1;
		question = qs;
		type = ts;
		
	}
	@Override
	public String getPromptText(ConversationContext arg0){
		
		return ChatColor.BLUE + question[numb] + ChatColor.RED +"Type quit to exit.";
		
	}

	@Override
	public Prompt acceptValidatedInput(ConversationContext c, Number n){
		Conversable cpo = c.getForWhom();
		cpo.sendRawMessage("Thank you.");
		
		num = (Integer) n;
		
		String input = num.toString();
		 try {
			    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("inputs.txt", true)));
			    out.println(input);
			    out.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		
		
		 if (type[j].equalsIgnoreCase("integer")){
				numb++;
			return new AgePrompt(plugin, numb, question, type);
			}
			if(type[j].equalsIgnoreCase("String")){
				numb++;
				return new FirstPrompt(plugin, numb, question, type);
				
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
