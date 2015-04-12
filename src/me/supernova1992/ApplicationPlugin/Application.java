package me.supernova1992.ApplicationPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;



import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Application {

	static String username;
	static String about;
	static Integer playerage;
	static ApplicationPlugin plugin = ApplicationPlugin.getAppPlug();
	
	/**
	 * @param args
	 */
	public Application(String user, String message, Integer age) {
		
		/*username = user;
		about = message;
		playerage = age;*/
		

	}
	
	public static Integer getAge(String[] app){
		
		Integer age = 0;
		String[] questions = null;
		
		Object[] f1 = plugin.getConfig().getList("Form1").toArray();
		
		questions = Arrays.copyOf(f1, f1.length, String[].class);
		String[] types = null;
		Object[] t1 =  plugin.getConfig().getList("Form1types").toArray();
		types = Arrays.copyOf(t1, t1.length, String[].class);
		Boolean useagereq = plugin.getConfig().getBoolean("UseAgeReq");
		if (useagereq.equals(true)){
			int i = 0;
			while(i < types.length){
				if (types[i].equalsIgnoreCase("integer")){
					age = Integer.valueOf(app[i]);
					
					Bukkit.getLogger().info(app[i]);
					return age;
					}
				i++;
				}
			}else{
			
			 age = 0;
			 return age;
			}
			return age;
		}
		
		
	
	
	public static String[] applicationValues(){
		
		username = ApplicationPlugin.getPlayer();
		
	
		/*about = FirstPrompt.getInput();
		playerage = AgePrompt.getInput();*/
		String[] questions = null;
		String[] app = null;
		
		Object[] q1 =  plugin.getConfig().getList("Form1").toArray();
		questions = Arrays.copyOf(q1, q1.length, String[].class);
		
		FileArrayProvider fap = new FileArrayProvider();
		try{
		String[] line = fap.readLines("inputs.txt");
		Integer l = line.length - 1;
		String ar = line[l];
		 app = line;
		
		Bukkit.getLogger().info(Arrays.toString(app));
		}catch(IOException ex){
			
			System.out.print(ex);
		}
		
		
		//String[] app ={username, about,playerage.toString()};
		
		return app;
	}
	
	@SuppressWarnings({ "null", "unused" })
	public static void AnalyzeApp(String[] app){
		
		Integer age = getAge(app);
		
		//String[] app = {user,info,num};
		
		String player = ApplicationPlugin.getPlayer();
		
		Player p = Bukkit.getServer().getPlayer(player);
		
		UUID uid = p.getUniqueId();
		
		String pid = uid.toString();
		
		String pname = p.getPlayerListName();
		
		Integer agereq =  plugin.getConfig().getInt("MinimumAge");
		
		Bukkit.getLogger().info(age.toString());
		
		
		
		
		if(age >= agereq){
			
			//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "msg "+player+ " You've been granted build permission! Relog for changes to take effect!");
			//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user "+ pid + " group set Everyone");
			
			//Object[] c1 = plugin.getConfig().getList("MeetsAgeReq").toArray();
			
			//String[] cmds = Arrays.copyOf(c1, c1.length,String[].class);
			
			ConfigurationSection entries = plugin.getConfig().getConfigurationSection("MeetsAgeReq.");
			for(String key : entries.getKeys(false)){
				
				ArrayList<String> arlst = new ArrayList<String>();
				
				
				for(Object obj : entries.getList(key)){
					
					String arr = obj.toString();
					
					//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), arr);
					arr = arr.replace("[Player]", Bukkit.getServer().getPlayer(player).getPlayerListName());
					arr = arr.replace("[UIN]", pid);
					
					arlst.add(arr);
				}
				
				Object[] a1 = arlst.toArray();
				String[] args = Arrays.copyOf(a1, a1.length, String[].class);
				int i =0;
				String cmd = null;
				while(i< args.length){
					if(i == 0){
						
						cmd = args[0];
					}else{
					cmd = cmd +" "+ args[i];
					}
					i++;
				}
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
			}
			
			/*for(String cmd : cmds){
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
				Bukkit.getLogger().info(cmd);
			}*/
			
			
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
