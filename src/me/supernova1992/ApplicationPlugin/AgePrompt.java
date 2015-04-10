package me.supernova1992.ApplicationPlugin;

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

 public static Integer getInput(){
	 
	 return num;
 }
 
	
	public AgePrompt(ApplicationPlugin instance){
		
		plugin = instance;
		
	}
	@Override
	public String getPromptText(ConversationContext arg0){
		
		return ChatColor.BLUE + "Please submit your age!" + ChatColor.RED +"Type quit to exit.";
		
	}

	@Override
	public Prompt acceptValidatedInput(ConversationContext c, Number n){
		Conversable cpo = c.getForWhom();
		cpo.sendRawMessage("Thank you.");
		
		num = (Integer) n;
		
		app = Application.applicationValues();
		
		Application.AnalyzeApp(app[0], app[1], app[2]);
		
		
		
		return END_OF_CONVERSATION;
	}
	
}
