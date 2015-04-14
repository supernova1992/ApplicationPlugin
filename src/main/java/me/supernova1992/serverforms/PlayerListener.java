package me.supernova1992.serverforms;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(final PlayerJoinEvent e) {

        String perms = ServerForms.getPlugin().getConfig().getString("LoginAdPermission");

        if (!e.getPlayer().hasPermission(perms)) {
            Title title = new Title("Hello " + e.getPlayer().getName() + "!");
            title.setSubtitle("Please apply by using /apply");
            title.setTitleColor(ChatColor.YELLOW);
            title.setSubtitleColor(ChatColor.GREEN);

            title.send(e.getPlayer());

        }
    }
}
