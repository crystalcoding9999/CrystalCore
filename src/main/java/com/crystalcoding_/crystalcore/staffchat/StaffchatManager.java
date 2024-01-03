package com.crystalcoding_.crystalcore.staffchat;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffchatManager {

    ArrayList<Player> inStaffChat;

    public StaffchatManager() {
        inStaffChat = new ArrayList<>();
    }

    public void playerLeft(Player p) {
        inStaffChat.remove(p);
    }

    public void enableStaffChat(Player player) {
        if (inStaffChat.contains(player)) {
            return;
        }

        inStaffChat.add(player);
        Core.message(CrystalCore.getInstance().messageManager.getStaffChatEnabledMessage(), player);
    }

    public void disableStaffChat(Player player) {
        if (!inStaffChat.contains(player)) {
            return;
        }

        inStaffChat.remove(player);
        Core.message(CrystalCore.getInstance().messageManager.getStaffChatDisabledMessage(), player);
    }

    public boolean inStaffChat(Player player) {
        return this.inStaffChat.contains(player);
    }

    public ArrayList<Player> getInStaffChat() {
        return inStaffChat;
    }

    public void sendStaffMessage(Player player, String content) {
        Rank playerRank = CrystalCore.getInstance().rankManager.getPlayerRank(player);
        String message = CrystalCore.getInstance().messageManager.getStaffChatMessage(player.getName(), playerRank.getPrefix(), content);
        Core.console(message);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("crystalcore.staffchat") || inStaffChat(p)) {
                Core.message(message, p);
            }
        }
    }

    public void sendStaffMessage(String sender, String content) {
        String message = CrystalCore.getInstance().messageManager.getStaffChatMessage(sender, "", content);
        Core.console(message);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("crystalcore.staffchat") || inStaffChat(p)) {
                Core.message(CrystalCore.getInstance().messageManager.getStaffChatMessage(sender, "", content), p);
            }
        }
    }
}
