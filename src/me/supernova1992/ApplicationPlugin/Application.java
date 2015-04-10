package me.supernova1992.ApplicationPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;



import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Application {

	static String username;
	static String about;
	static Integer playerage;
	
	/**
	 * @param args
	 */
	public Application(String user, String message, Integer age) {
		
		/*username = user;
		about = message;
		playerage = age;*/
		

	}
	
	public static String[] applicationValues(){
		
		username = ApplicationPlugin.getPlayer();
		
	
		about = FirstPrompt.getInput();
		playerage = AgePrompt.getInput();
		
		String[] app ={username, about,playerage.toString()};
		
		return app;
	}
	
	public static void AnalyzeApp(String user, String info, String num){
		
		Integer age = Integer.valueOf(num);
		
		String[] app = {user,info,num};
		
		String player = ApplicationPlugin.getPlayer();
		
		Player p = Bukkit.getServer().getPlayer(player);
		
		UUID uid = p.getUniqueId();
		
		String pid = uid.toString();
		
		
		
		
		if(age >= 16){
			
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "msg "+player+ " You've been granted build permission! Relog for changes to take effect!");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user "+ pid + " group set Everyone");
			
			
		}else{
			Bukkit.getLogger().info("A new application has arrived. Please check Applications.txt!");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "msg "+player+ " Your application has been sent to the moderators!" );
			try {
			    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Applications.txt", true)));
			    out.println(Arrays.toString(app));
			    out.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
			
			
			
		}
		
		
		
	
	}

}
