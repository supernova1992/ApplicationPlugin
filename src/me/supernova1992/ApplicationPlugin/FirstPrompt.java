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

import org.bukkit.ChatColor;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class FirstPrompt extends StringPrompt {

	private ServerForms plugin;
	//private String formName = ServerForms.formName();
	private String[] app;
	private String input;
	//private String player;
	private String[] question;
	private String[] type;
	private Integer num;
	private Integer j;
	
	public String getInput(){
		
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
		//player = cpo.toString();
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
			Application myApp = new Application();
			app = myApp.getApplicationValues();
			myApp.AnalyzeApp(app);
			
			String p = "inputs.txt";
			Path path = Paths.get(p);
			
			try {
			    Files.delete(path);
			} catch (NoSuchFileException x) {
			    System.err.format("%s: no such" + " file or directory%n", path);
			} catch (DirectoryNotEmptyException x) {
			    System.err.format("%s not empty%n", path);
			} catch (IOException ex) {
			    ex.printStackTrace();
			}
			return END_OF_CONVERSATION;
		}
		return END_OF_CONVERSATION;
	}
}
