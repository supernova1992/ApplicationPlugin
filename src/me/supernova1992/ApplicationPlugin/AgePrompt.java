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
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

public class AgePrompt extends NumericPrompt{

	
private ServerForms plugin;
//private String formName = ServerForms.formName(); not in use
private Integer num;
private String[] app;
private String[] question;
private String[] type;
private Integer numb;
private Integer j;

 public Integer getInput(){
	 
	 return this.num;
 }
 
	public AgePrompt(ServerForms instance, Integer i, String[] qs, String [] ts){
		
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
				
			    e.printStackTrace();
			}
		
		 if (type[j].equalsIgnoreCase("integer")) {
				numb++;
			return new AgePrompt(plugin, numb, question, type);
			}
			if(type[j].equalsIgnoreCase("String")){
				numb++;
				return new FirstPrompt(plugin, numb, question, type);
				
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
