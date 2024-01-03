package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffchatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("crystalcore.staffchat")) {
                Core.noPermission(sender);
                return false;
            }

            Player p = (Player) sender;

            if (args.length == 0) {
                if (CrystalCore.getInstance().staffchatManager.inStaffChat(p)) {
                    CrystalCore.getInstance().staffchatManager.disableStaffChat(p);
                } else {
                    CrystalCore.getInstance().staffchatManager.enableStaffChat(p);
                }
                return false;
            }

            StringBuilder contentBuilder = new StringBuilder();
            for (String string : args) {
                contentBuilder.append(string).append(" ");
            }

            CrystalCore.getInstance().staffchatManager.sendStaffMessage(p, contentBuilder.toString());
            return false;
        }

        StringBuilder contentBuilder = new StringBuilder();
        for (String string : args) {
            contentBuilder.append(string).append(" ");
        }

        CrystalCore.getInstance().staffchatManager.sendStaffMessage("Console", contentBuilder.toString());

        return false;
    }
}
