package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length >= 2) {
            String senderName = sender.getName();
            if (!(sender instanceof Player)) {
                senderName = "Console";
            }
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);

            if (target == null) {
                Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(targetName), sender);
                return false;
            }

            if (sender instanceof Player) {
                if (!(((Player) sender).canSee(target))) {
                    Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(targetName), sender);
                    return false;
                }
            }

            StringBuilder messageBuilder = new StringBuilder();

            for (int i = 1; i < args.length; i++) {
                messageBuilder.append(args[i]).append(" ");
            }

            sender.sendMessage(CrystalCore.getInstance().messageManager.getDirectMessageSentMessage(senderName, targetName, messageBuilder.toString()));
            target.sendMessage(CrystalCore.getInstance().messageManager.getDirectMessageReceivedMessage(senderName, targetName, messageBuilder.toString()));
        } else {
            Core.message(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage(command.getName(), "<target> <message>"), sender);
        }

        return false;
    }
}
