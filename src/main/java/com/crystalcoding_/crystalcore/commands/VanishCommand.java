package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.messages.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CrystalCore.getInstance().messageManager.getPlayerOnlyMessage());
            return false;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("crystalcore.vanish"))) {
            p.sendMessage(CrystalCore.getInstance().messageManager.getNoPermissionMessage());
            return false;
        }

        if (CrystalCore.getInstance().vanishManager.isVanished(p)) {
            p.sendMessage(CrystalCore.getInstance().messageManager.getSelfUnvanishMessage());
            CrystalCore.getInstance().vanishManager.setVanished(p, false);

            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.getUniqueId().equals(p.getUniqueId())) {
                    continue;
                }
                if (op.hasPermission("crystalcore.vanish")) {
                    op.sendMessage(CrystalCore.getInstance().messageManager.getOtherUnvanishMessage(p));
                } else {
                    op.showPlayer(CrystalCore.getInstance(), p);
                    op.sendMessage(CrystalCore.getInstance().messageManager.getJoinMessage(p));
                }
            }
        } else {
            p.sendMessage(CrystalCore.getInstance().messageManager.getSelfVanishMessage());
            CrystalCore.getInstance().vanishManager.setVanished(p, true);

            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.getUniqueId().equals(p.getUniqueId())) {
                    continue;
                }
                if (op.hasPermission("crystalcore.vanish")) {
                    op.sendMessage(CrystalCore.getInstance().messageManager.getOtherVanishMessage(p));
                } else {
                    op.hidePlayer(CrystalCore.getInstance(), p);
                    op.sendMessage(CrystalCore.getInstance().messageManager.getLeaveMessage(p));
                }
            }
        }

        return false;
    }
}
