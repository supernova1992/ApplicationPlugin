package me.supernova1992.applicationplugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
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
	static ServerForms plugin = ServerForms.getAppPlug();
	
	/**
	 * @param args
	 */
	public Application(String user, String message, Integer age) {
		
		
		

	}
	
	public static Integer getAge(String[] app){
		
		String formName = ServerForms.formName();
		Integer age = 0;
		String[] questions = null;
		
		Object[] f1 = plugin.getConfig().getList("Forms."+formName).toArray();
		
		questions = Arrays.copyOf(f1, f1.length, String[].class);
		String[] types = null;
		Object[] t1 =  plugin.getConfig().getList("FormTypes."+formName).toArray();
		types = Arrays.copyOf(t1, t1.length, String[].class);
		Boolean useagereq = plugin.getConfig().getBoolean("UseAgeReq."+formName);
		//Bukkit.getLogger().info(useagereq.toString());
		if (useagereq = true ){
			int i = 0;
			while(i < types.length){
				if (types[i].equalsIgnoreCase("integer")){
					age = Integer.valueOf(app[i+1]);
					
					
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
		String formName = ServerForms.formName();
		username = ServerForms.getPlayer();
		String[] questions = null;
		String[] app = null;
		
		FileArrayProvider fap = new FileArrayProvider();
		try{
		String[] line = fap.readLines("inputs.txt");
		Integer l = line.length - 1;
		String ar = line[l];
		int n = 0;
		ArrayList<String> arlst= new ArrayList<String>();
		arlst.add(username);
		while(n< line.length){
			
			String arr = line[n].toString();
			
			
			arlst.add(arr);
			n++;
			
		}
		
		Object[] a1 = arlst.toArray();
		 app = Arrays.copyOf(a1, a1.length, String[].class);
		 
		arlst.clear();
		}catch(IOException ex){
			
			System.out.print(ex);
		}
		
		
		
		
		return app;
	}
	
	@SuppressWarnings({ "null", "unused" })
	public static void AnalyzeApp(String[] app){
		
		String formName = ServerForms.formName();
		Integer age = getAge(app);
		
		String player = ServerForms.getPlayer();
		
		Player p = Bukkit.getServer().getPlayer(player);
		
		UUID uid = p.getUniqueId();
		
		String pid = uid.toString();
		
		String pname = p.getPlayerListName();
		
		Integer agereq =  plugin.getConfig().getInt("MinimumAge."+formName);
		
		
		
		
		
		
		if(age >= agereq){
			
			ConfigurationSection entries = plugin.getConfig().getConfigurationSection("MeetsAgeReq.");
			for(String key : entries.getKeys(true)){
				
				if (key.equals(formName)){
				ArrayList<String> arlst = new ArrayList<String>();
				
					
					
					for(Object obj : entries.getList(key)){
						 
						
							
							
							@SuppressWarnings("unchecked")
							LinkedHashMap<String, List<String>> test =  (LinkedHashMap<String, List<String>>) obj;
							List<List<String>> l = new ArrayList<List<String>>(test.values());
							
							
							Object[] newArr =  l.get(0).toArray();
							
							
							
							int n = 0;
							while(n< newArr.length){
								
								String arr = newArr[n].toString();
								arr = arr.replace("[Player]", Bukkit.getServer().getPlayer(player).getPlayerListName());
								arr = arr.replace("[UIN]", pid);
								
								arlst.add(arr);
								n++;
								
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
							arlst.clear();
						}
							
						}
					
						
					
					
				
				
				
			
			}
		}else{
			Bukkit.getLogger().info("A new application has arrived. Please check plugins/ServerForms/"+formName+".txt!");
			
			ConfigurationSection entries2 = plugin.getConfig().getConfigurationSection("DoesNotMeetAgeReq.");
				for(String key : entries2.getKeys(true)){
				
				if (key.equals(formName)){
				ArrayList<String> arlst = new ArrayList<String>();
				
					
					
					for(Object obj : entries2.getList(key)){
						 
						
							
							
							@SuppressWarnings("unchecked")
							LinkedHashMap<String, List<String>> test =  (LinkedHashMap<String, List<String>>) obj;
							List<List<String>> l = new ArrayList<List<String>>(test.values());
							
							
							Object[] newArr =  l.get(0).toArray();
							
							
							
							int n = 0;
							while(n< newArr.length){
								
								String arr = newArr[n].toString();
								arr = arr.replace("[Player]", Bukkit.getServer().getPlayer(player).getPlayerListName());
								arr = arr.replace("[UIN]", pid);
								
								arlst.add(arr);
								n++;
								
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
							arlst.clear();
					}
			try {
			    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("plugins/ServerForms/"+formName+".txt", true)));
			    out.println(Arrays.toString(app));
			    out.close();
			    
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			
			
		}
		
				}
		
	
	}
	}
}
