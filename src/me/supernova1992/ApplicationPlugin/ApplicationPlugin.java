package me.supernova1992.ApplicationPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ApplicationPlugin extends JavaPlugin {

	static String pname;
	
	static ApplicationPlugin plugin;
	public static String getPlayer(){
		
		return pname;
	}
	
	@Override
	public void onEnable(){
		plugin = this;
		getLogger().info("Applications is listening for new users");
		new PlayerListener(this);
		
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		//new WriteQuestions("test");
		
	}
	public static ApplicationPlugin getAppPlug(){
		
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
			
			pname = player.getPlayerListName();
			String perms = plugin.getConfig().getString("FormApplyPerm");
			
			if(!(sender.hasPermission(perms))){
				String[] questions = null;
			
				Object[] f1 = plugin.getConfig().getList("Form1").toArray();
				
				questions = Arrays.copyOf(f1, f1.length, String[].class);
				String[] types = null;
				Object[] t1 =  plugin.getConfig().getList("Form1types").toArray();
				types = Arrays.copyOf(t1, t1.length, String[].class);
				int i = 0;
				
			ConversationFactory factory = new ConversationFactory(this);
			Conversation conv = factory.withFirstPrompt(new FirstPrompt(this, i, questions, types)).withLocalEcho(true).withEscapeSequence("quit").buildConversation((Player)sender);
			
			 Boolean abandoned = false;
			
			conv.addConversationAbandonedListener(new ConversationAbandonedListener(){
					@Override
			        public void conversationAbandoned(
			                ConversationAbandonedEvent event)
			        {
			            if (event.gracefulExit())
			            {
			                Bukkit.getLogger().info("graceful exit");
			                Boolean abandoned = true;
			            }
			            try
			            {
			                Bukkit.getLogger().info(
			                        "Canceller"
			                                + event.getCanceller()
			                                        .toString());
			                Boolean abandoned = true;
			            }
			            catch (NullPointerException n)
			            {
			                // Was null
			                Bukkit.getLogger().info(
			                        "null Canceller");
			                
			            }
			        }
			}
			    );
			conv.begin();
			
			
			return true;
		}else{
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "msg "+player.getPlayerListName()+" You already have been whitelisted!");
			return true;
			
		}
		}
		if(cmd.getName().equalsIgnoreCase("readapp")){
			FileArrayProvider fap = new FileArrayProvider();
			try{
			String[] line = fap.readLines("Applications.txt");
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
}
