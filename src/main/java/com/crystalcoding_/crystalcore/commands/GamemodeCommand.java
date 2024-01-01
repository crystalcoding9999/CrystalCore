package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0 && !(sender instanceof Player)) {
            sender.sendMessage(CrystalCore.getInstance().messageManager.getPlayerOnlyMessage());
            return false;
        } else if (args.length == 0) {
            Player p = (Player) sender;

            switch (command.getName()) {
                case "gms":
                    p.setGameMode(GameMode.SURVIVAL);
                    return true;
                case "gmc":
                    p.setGameMode(GameMode.CREATIVE);
                    return true;
                case "gmsp":
                    p.setGameMode(GameMode.SPECTATOR);
                    return true;
                case "gma":
                    p.setGameMode(GameMode.ADVENTURE);
                    return true;
            }
        } else if (args.length == 1) {
            if (command.getName().equalsIgnoreCase("gamemode")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(CrystalCore.getInstance().messageManager.getPlayerOnlyMessage());
                    return false;
                }
                Player p = (Player) sender;

                switch (args[0].toLowerCase()) {
                    case "survival":
                        p.setGameMode(GameMode.SURVIVAL);
                        return true;
                    case "creative":
                        p.setGameMode(GameMode.CREATIVE);
                        return true;
                    case "spectator":
                        p.setGameMode(GameMode.SPECTATOR);
                        return true;
                    case "adventure":
                        p.setGameMode(GameMode.ADVENTURE);
                        return true;
                    default:
                        p.sendMessage(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("gamemode", "<survival|creative|spectator|adventure> ?player?"));
                        return false;
                }
            } else {
                Player p = (Player) sender;
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    p.sendMessage(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(args[0]));
                    return false;
                }

                switch (command.getName()) {
                    case "gms":
                        target.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "survival"));
                        return true;
                    case "gmc":
                        target.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "creative"));
                        return true;
                    case "gmsp":
                        target.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "spectator"));
                        return true;
                    case "gma":
                        target.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "adventure"));
                        return true;
                }
            }
        } else if (args.length >= 2) {
            Player p = (Player) sender;
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                p.sendMessage(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(args[1]));
                return false;
            }

            if (command.getName().equalsIgnoreCase("gamemode")) {
                switch (args[0].toLowerCase()) {
                    case "survival":
                        target.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "survival"));
                        return true;
                    case "creative":
                        target.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "creative"));
                        return true;
                    case "spectator":
                        target.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "spectator"));
                        return true;
                    case "adventure":
                        target.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "adventure"));
                        return true;
                    default:
                        target.sendMessage(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("gamemode", "<survival|creative|spectator|adventure> ?player?"));
                        return false;
                }
            }
        }

        return false;
    }
}
