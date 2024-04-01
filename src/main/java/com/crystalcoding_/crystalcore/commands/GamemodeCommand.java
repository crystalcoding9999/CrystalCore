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
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                Core.playerOnly(sender);
                return false;
            }
            Player p = (Player) sender;
            switch (command.getName()) {
                case "gms":
                case "gmc":
                case "gmsp":
                case "gma":
                    GameMode gameMode = null;

                    if (command.getName().endsWith("s")) gameMode = GameMode.SURVIVAL;
                    else if (command.getName().endsWith("c")) gameMode = GameMode.CREATIVE;
                    else if (command.getName().endsWith("sp")) gameMode = GameMode.SPECTATOR;
                    else if (command.getName().endsWith("a")) gameMode = GameMode.ADVENTURE;

                    String perm = "crystalcore.gamemode." + gameMode.name().toLowerCase();
                    if (p.hasPermission(perm)) {
                        p.setGameMode(gameMode);
                    } else {
                        Core.noPermission(p);
                    }
                    return true;
            }
        } else if (args.length == 1 && command.getName().equalsIgnoreCase("gamemode")) {
            if (!(sender instanceof Player)) {
                Core.playerOnly(sender);
                return false;
            }
            Player p = (Player) sender;
            String arg = args[0].toLowerCase();
            if (!p.hasPermission("crystalcore.gamemode." + arg)) {
                Core.noPermission(p);
                return false;
            }
            GameMode mode;
            try {
                mode = GameMode.valueOf(arg.toUpperCase());
            } catch (IllegalArgumentException e) {
                Core.message(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("gamemode", "<survival|creative|spectator|adventure> ?player?"), p);
                return false;
            }
            p.setGameMode(mode);
            return true;
        } else if (args.length >= 2 && command.getName().equalsIgnoreCase("gamemode")) {
            String arg = args[0].toLowerCase();
            GameMode mode;
            try {
                mode = GameMode.valueOf(arg.toUpperCase());
            } catch (IllegalArgumentException e) {
                Core.message(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("gamemode", "<survival|creative|spectator|adventure> ?player?"), sender);
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(args[1]), sender);
                return false;
            }
            if (!(sender instanceof Player) || ((Player) sender).hasPermission("crystalcore.gamemode." + arg)) {
                target.setGameMode(mode);
                Core.message(CrystalCore.getInstance().messageManager.getGamemodeChangeOtherMessage(target.getName(), arg), sender);
            } else {
                Core.noPermission(sender);
            }
            return true;
        }
        return false;
    }

}
