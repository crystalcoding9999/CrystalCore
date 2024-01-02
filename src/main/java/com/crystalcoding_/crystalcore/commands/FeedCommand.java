package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (sender.hasPermission("crystalcore.feed")) {
                    ((Player) sender).setFoodLevel(20);
                    Core.message(CrystalCore.getInstance().messageManager.getFeedMessage(), sender);
                } else {
                    Core.noPermission(sender);
                }
            } else {
                Core.playerOnly(sender);
            }
        } else {
            if (sender instanceof Player) {
                if (!sender.hasPermission("crystalcore.feed")) {
                    Core.noPermission(sender);
                    return false;
                }
            }
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);
            if (target == null) {
                Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(targetName), sender);
                return false;
            }

            target.setFoodLevel(20);
            if (sender instanceof Player) {
                if (sender.getName().equalsIgnoreCase(targetName)) {
                    Core.message(CrystalCore.getInstance().messageManager.getFeedMessage(), target);
                } else {
                    Core.message(CrystalCore.getInstance().messageManager.getFeedMessage(), target);
                    Core.message(CrystalCore.getInstance().messageManager.getFeedOtherMessage(targetName), sender);
                }
            } else {
                Core.message(CrystalCore.getInstance().messageManager.getFeedMessage(), target);
                Core.message(CrystalCore.getInstance().messageManager.getFeedOtherMessage(targetName), sender);
            }
        }
        return false;
    }
}
