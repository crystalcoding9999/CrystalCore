package com.crystalcoding_.crystalcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TablistManager {

    private static final int REFRESH_TICKS = 20; // 1 second

    public void enableTablist() {
        Bukkit.getScheduler().runTaskTimer(CrystalCore.getInstance(), this::updateTablist, 0, REFRESH_TICKS);
    }

    private void updateTablist() {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        String header = ChatColor.YELLOW + "Welcome to the Server!";
        String footer = ChatColor.GREEN + "Online Players: " + ChatColor.WHITE + onlinePlayers;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeader(header);
            player.setPlayerListFooter(footer);
        }
    }
}





