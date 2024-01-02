package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
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
            Core.playerOnly(sender);
            return false;
        } else if (args.length == 0) {
            Player p = (Player) sender;

            switch (command.getName()) {
                case "gms":
                    if (p.hasPermission("crystalcore.gamemode.survival")) {
                        p.setGameMode(GameMode.SURVIVAL);
                    } else {
                        Core.noPermission(p);
                    }
                    return true;
                case "gmc":
                    if (p.hasPermission("crystalcore.gamemode.creative")) {
                        p.setGameMode(GameMode.CREATIVE);
                    } else {
                        Core.noPermission(p);
                    }
                    return true;
                case "gmsp":
                    if (p.hasPermission("crystalcore.gamemode.spectator")) {
                        p.setGameMode(GameMode.SPECTATOR);
                    } else {
                        Core.noPermission(p);
                    }
                    return true;
                case "gma":
                    if (p.hasPermission("crystalcore.gamemode.adventure")) {
                        p.setGameMode(GameMode.ADVENTURE);
                    } else {
                        Core.noPermission(p);
                    }
                    return true;
            }
        } else if (args.length == 1) {
            if (command.getName().equalsIgnoreCase("gamemode")) {
                if (!(sender instanceof Player)) {
                    Core.playerOnly(sender);
                    return false;
                }
                Player p = (Player) sender;

                if (!p.hasPermission("crystalcore.gamemode." + args[0].toLowerCase())) {
                    Core.noPermission(p);
                    return false;
                }

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
                        Core.message(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("gamemode", "<survival|creative|spectator|adventure> ?player?"), p);
                        return false;
                }
            } else {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(args[0]), sender);
                    return false;
                }

                switch (command.getName()) {
                    case "gms":
                        if (sender.hasPermission("crystalcore.gamemode.survival")) {
                            target.setGameMode(GameMode.SURVIVAL);
                            Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "survival"), sender);
                        } else {
                            Core.noPermission(sender);
                        }
                        return true;
                    case "gmc":
                        if (sender.hasPermission("crystalcore.gamemode.creative")) {
                            target.setGameMode(GameMode.CREATIVE);
                            Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "creative"), sender);
                        } else {
                            Core.noPermission(sender);
                        }
                        return true;
                    case "gmsp":
                        if (sender.hasPermission("crystalcore.gamemode.spectator")) {
                            target.setGameMode(GameMode.SPECTATOR);
                            Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "spectator"), sender);
                        } else {
                            Core.noPermission(sender);
                        }
                        return true;
                    case "gma":
                        if (sender.hasPermission("crystalcore.gamemode.adventure")) {
                            target.setGameMode(GameMode.ADVENTURE);
                            Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "adventure"), sender);
                        } else {
                            Core.noPermission(sender);
                        }
                        return true;
                }
            }
        } else if (args.length >= 2) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(args[1]), sender);
                return false;
            }

            if (command.getName().equalsIgnoreCase("gamemode")) {
                if (sender instanceof Player) {
                    if (!sender.hasPermission("crystalcore.gamemode." + args[0].toLowerCase())) {
                        Core.noPermission(sender);
                        return false;
                    }
                }

                switch (args[0].toLowerCase()) {
                    case "survival":
                        target.setGameMode(GameMode.SURVIVAL);
                        Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "survival"), sender);
                        return true;
                    case "creative":
                        target.setGameMode(GameMode.CREATIVE);
                        Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "creative"), sender);
                        return true;
                    case "spectator":
                        target.setGameMode(GameMode.SPECTATOR);
                        Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "spectator"), sender);
                        return true;
                    case "adventure":
                        target.setGameMode(GameMode.ADVENTURE);
                        Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), "adventure"), sender);
                        return true;
                    default:
                        Core.message(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("gamemode", "<survival|creative|spectator|adventure> ?player?"), sender);
                        return false;
                }
            }
        }

        return false;
    }
}
