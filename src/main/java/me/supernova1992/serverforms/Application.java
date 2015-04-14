package me.supernova1992.serverforms;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Application {

    private String username;
    private String message;
    private Integer age;

    public Application(String username, String message, Integer age) {
        this.username = username;
        this.message = message;
        this.age = age;
    }

    public Integer getAge(String[] app) {

        String formName = ServerForms.formName();
        Integer age = 0;
        List<String> types = ServerForms.getPlugin().getConfig().getStringList("FormTypes." + formName);
        Boolean useagereq = ServerForms.getPlugin().getConfig().getBoolean("UseAgeReq." + formName);

        if (useagereq) {
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equalsIgnoreCase("integer")) {
                    age = Integer.valueOf(app[i + 1]);
                    return age;
                }
            }
        } else {
            return age;
        }
        return age;
    }


    public String[] applicationValues() {
        String[] app = null;

        FileArrayProvider fap = new FileArrayProvider();
        try {
            String[] line = fap.readLines("inputs.txt");
            ArrayList<String> arlst = new ArrayList<String>();
            arlst.add(username);

            arlst.addAll(Arrays.asList(line).subList(0, arlst.size()));

            Object[] a1 = arlst.toArray();
            app = Arrays.copyOf(a1, a1.length, String[].class);
            arlst.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return app;
    }

    public void AnalyzeApp(String[] app) {

        String formName = ServerForms.formName();
        Integer age = getAge(app);
        String player = ServerForms.getPlayer();
        Player p = Bukkit.getServer().getPlayer(player);
        UUID uid = p.getUniqueId();
        String pid = uid.toString();
        int agereq = ServerForms.getPlugin().getConfig().getInt("MinimumAge." + formName);

        if (age >= agereq) {

            ConfigurationSection entries = ServerForms.getPlugin().getConfig().getConfigurationSection("MeetsAgeReq.");
            for (String key : entries.getKeys(true)) {

                if (key.equals(formName)) {
                    ArrayList<String> arlst = new ArrayList<String>();

                    for (Object obj : entries.getList(key)) {

                        LinkedHashMap<String, List<String>> test = (LinkedHashMap<String, List<String>>) obj;
                        List<List<String>> l = new ArrayList<List<String>>(test.values());


                        Object[] newArr = l.get(0).toArray();


                        int n = 0;
                        while (n < newArr.length) {

                            String arr = newArr[n].toString();
                            arr = arr.replace("[Player]", Bukkit.getServer().getPlayer(player).getPlayerListName());
                            arr = arr.replace("[UIN]", pid);

                            arlst.add(arr);
                            n++;

                        }
                        Object[] a1 = arlst.toArray();
                        String[] args = Arrays.copyOf(a1, a1.length, String[].class);
                        int i = 0;
                        String cmd = null;
                        while (i < args.length) {
                            if (i == 0) {

                                cmd = args[0];
                            } else {
                                cmd = cmd + " " + args[i];
                            }
                            i++;
                        }

                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
                        arlst.clear();
                    }

                }


            }
        } else {
            Bukkit.getLogger().info("A new application has arrived. Please check ServerForms.getPlugin()s/ServerForms/" + formName + ".txt!");

            ConfigurationSection entries2 = ServerForms.getPlugin().getConfig().getConfigurationSection("DoesNotMeetAgeReq.");
            for (String key : entries2.getKeys(true)) {

                if (key.equals(formName)) {
                    ArrayList<String> arlst = new ArrayList<String>();


                    for (Object obj : entries2.getList(key)) {


                        @SuppressWarnings("unchecked")
                        LinkedHashMap<String, List<String>> test = (LinkedHashMap<String, List<String>>) obj;
                        List<List<String>> l = new ArrayList<List<String>>(test.values());


                        Object[] newArr = l.get(0).toArray();


                        int n = 0;
                        while (n < newArr.length) {

                            String arr = newArr[n].toString();
                            arr = arr.replace("[Player]", Bukkit.getServer().getPlayer(player).getPlayerListName());
                            arr = arr.replace("[UIN]", pid);

                            arlst.add(arr);
                            n++;

                        }
                        Object[] a1 = arlst.toArray();
                        String[] args = Arrays.copyOf(a1, a1.length, String[].class);
                        int i = 0;
                        String cmd = null;
                        while (i < args.length) {
                            if (i == 0) {

                                cmd = args[0];
                            } else {
                                cmd = cmd + " " + args[i];
                            }
                            i++;
                        }

                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
                        arlst.clear();
                    }
                    try {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ServerForms.getPlugin()s/ServerForms/" + formName + ".txt", true)));
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
