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
	
	public void loadActivity(){
		String advertisement = "LoginAd";
		String logadcon = "modifyworld.*";
		String help1 = "#Commands to be run when user meets age requirement";
		String commands1 = "MeetsAgeReq";
		String commands2 = "DoesNotMeetAgeReq";
		String condition = "LoginAdPermission";
		String q1 = "Question1";
		String q2 = "Question2";
		String a = "MinimumAge";
		List<String> list1 = Arrays.asList("msg \"+player+ \" You've been granted build permission! Relog for changes to take effect!","pex user \"+ pid + \" group set Everyone");
		List<String> list2 = Arrays.asList("msg \"+player+ \" Your application has been sent to the moderators!");
		String q1text = "Please explain why you'd like to join the server!";
		String q2text = "Please submit your age!";
		Integer age = 16;
		String header = "LoginAdPermission checks for the listed permission. If the user has it, the ad will not run. The following Variables may be used: pid = player UIN player = playername (for use in commands ex /kick playername) ";
		getConfig().options().header(header);
		getConfig().addDefault(condition,logadcon);
		getConfig().addDefault(advertisement, "Please apply by using /apply");
		
		getConfig().addDefault(a, age);
		getConfig().addDefault(commands1, list1);
		getConfig().addDefault(commands2, list2);
		getConfig().addDefault(q1, q1text);
		getConfig().addDefault(q2, q2text);
		getConfig().options().copyDefaults(true);
		
		saveConfig();
		
	}
	
	public static String getPlayer(){
		
		return pname;
	}
	
	@Override
	public void onEnable(){
		getLogger().info("Applications is listening for new users");
		new PlayerListener(this);
		loadActivity(); 
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
			
			if(!(sender.hasPermission("modifyworld.*"))){
			
			ConversationFactory factory = new ConversationFactory(this);
			Conversation conv = factory.withFirstPrompt(new FirstPrompt(this)).withLocalEcho(true).withEscapeSequence("quit").buildConversation((Player)sender);
			
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
