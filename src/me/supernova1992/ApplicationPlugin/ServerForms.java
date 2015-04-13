package me.supernova1992.ApplicationPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerForms extends JavaPlugin {

	static String pname;
	
	static ServerForms plugin;
	public static String getPlayer(){
		
		return pname;
	}
	
	static String formName;
	
	
	@Override
	public void onEnable(){
		plugin = this;
		getLogger().info("ServerForoms is listening for new applicants!");
		new PlayerListener(this);
		
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		//new WriteQuestions("test");
		
	}
	public static ServerForms getAppPlug(){
		
		return plugin;
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		
		if(cmd.getName().equalsIgnoreCase("apply")){
			
			final Player player = (Player) sender;
			final UUID uuid  = ((Player) sender).getPlayer().getUniqueId();
			final String pid = uuid.toString();
			
			if(args.length > 0){
				
				ConfigurationSection ent = plugin.getConfig().getConfigurationSection("Forms.");
				
				for(String key : ent.getKeys(false)){
					Bukkit.getLogger().info(key);
					if(args[0].equalsIgnoreCase(key)){
						
						formName = key;
					}
					
					
					
					}
					
				
				Bukkit.getLogger().info(formName);
			
			
				pname = player.getPlayerListName();
				String perms = plugin.getConfig().getString("FormApplyPerm."+formName);
				
				if(!(sender.hasPermission(perms))){
					String[] questions = null;
				
					Object[] f1 = plugin.getConfig().getList("Forms."+formName).toArray();
					
					questions = Arrays.copyOf(f1, f1.length, String[].class);
					String[] types = null;
					Object[] t1 =  plugin.getConfig().getList("FormTypes."+formName).toArray();
					types = Arrays.copyOf(t1, t1.length, String[].class);
					int i = 0;
					
				ConversationFactory factory = new ConversationFactory(this);
				if(types[0].equalsIgnoreCase("string")){
				Conversation conv = factory.withFirstPrompt(new FirstPrompt(this, i, questions, types)).withLocalEcho(true).withEscapeSequence("quit").buildConversation((Player)sender);
				
				conv.begin();
				}
				if(types[0].equalsIgnoreCase("integer")){
					
					Conversation conv = factory.withFirstPrompt(new AgePrompt(this, i, questions, types)).withLocalEcho(true).withEscapeSequence("quit").buildConversation((Player)sender);
					
					conv.begin();
				}
				
				
				return true;
			}else{
				ConfigurationSection entries = plugin.getConfig().getConfigurationSection("MeetsAgeReq."+formName+".");
				for(String key : entries.getKeys(false)){
					
					ArrayList<String> arlst = new ArrayList<String>();
					
					
					for(Object obj : entries.getList(key)){
						
						String arr = obj.toString();
						
						//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), arr);
						arr = arr.replace("[Player]", player.getPlayerListName());
						arr = arr.replace("[UIN]", pid);
						
						arlst.add(arr);
					}
					
					Object[] a1 = arlst.toArray();
					String[] ars = Arrays.copyOf(a1, a1.length, String[].class);
					int i =0;
					String cd = null;
					while(i< ars.length){
						if(i == 0){
							
							cd = ars[0];
						}else{
						cd = cd +" "+ ars[i];
						}
						i++;
					}
					
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cd);
				}
				//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "msg "+player.getPlayerListName()+" You already have been whitelisted!");
				return true;
				
			}
			}else{
				return false;
			}
			
		}
		if(cmd.getName().equalsIgnoreCase("readapp")){
			FileArrayProvider fap = new FileArrayProvider();
			try{
			String[] line = fap.readLines("plugins/ServerForms/"+formName+".txt");
			Integer l = line.length - 1;
			String ar = line[l];
			String app = ar;
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "msg "+sender+" "+app );
			Bukkit.getLogger().info(app);
			}catch(IOException ex){
				
				System.out.print(ex);
			}
			
			
			return true;
		}
			
		return false;
	}

	
public static String formName(){
		
		return formName;
	}
}
