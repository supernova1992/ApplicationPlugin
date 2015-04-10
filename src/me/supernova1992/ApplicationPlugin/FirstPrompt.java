package me.supernova1992.ApplicationPlugin;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class FirstPrompt extends StringPrompt {

	ApplicationPlugin plugin;
	
	static String input;
	static String player;
	
	public static String getInput(){
		
		return input;
	}
	
	public FirstPrompt(ApplicationPlugin instance){
		
		plugin = instance;
		
	}
	@Override
	public String getPromptText(ConversationContext arg0){
		
		return ChatColor.BLUE + "Please explain why you'd like to join the server!" + ChatColor.RED +"Type quit to exit.";
		
	}

	@Override
	public Prompt acceptInput(ConversationContext c, String s){
		Conversable cpo = c.getForWhom();
		cpo.sendRawMessage("Thank you.");
		 
		player = cpo.toString();
		 input = s;
		
		return new AgePrompt(plugin);
	}
}
