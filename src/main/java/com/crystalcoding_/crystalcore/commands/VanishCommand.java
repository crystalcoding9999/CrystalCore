package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
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
            Core.message(CrystalCore.getInstance().messageManager.getPlayerOnlyMessage(), sender);
            return false;
        }

        Player p = (Player) sender;

        if (!(p.hasPermission("crystalcore.vanish"))) {
            Core.message(CrystalCore.getInstance().messageManager.getNoPermissionMessage(), p);
            return false;
        }

        if (CrystalCore.getInstance().vanishManager.isVanished(p)) {
            Core.message(CrystalCore.getInstance().messageManager.getSelfUnvanishMessage(), p);
            CrystalCore.getInstance().vanishManager.setVanished(p, false);

            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.getUniqueId().equals(p.getUniqueId())) {
                    continue;
                }
                if (op.hasPermission("crystalcore.vanish")) {
                    Core.message(CrystalCore.getInstance().messageManager.getOtherUnvanishMessage(p), op);
                } else {
                    op.showPlayer(CrystalCore.getInstance(), p);
                    Core.message(CrystalCore.getInstance().messageManager.getJoinMessage(p), op);
                }
            }
        } else {
            Core.message(CrystalCore.getInstance().messageManager.getSelfVanishMessage(), p);
            CrystalCore.getInstance().vanishManager.setVanished(p, true);

            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.getUniqueId().equals(p.getUniqueId())) {
                    continue;
                }
                if (op.hasPermission("crystalcore.vanish")) {
                    Core.message(CrystalCore.getInstance().messageManager.getOtherVanishMessage(p), op);
                } else {
                    op.hidePlayer(CrystalCore.getInstance(), p);
                    Core.message(CrystalCore.getInstance().messageManager.getLeaveMessage(p), op);
                }
            }
        }

        return false;
    }
}
