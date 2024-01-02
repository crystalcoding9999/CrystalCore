package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            Core.playerOnly(sender);
            return false;
        } else if (!(sender instanceof Player) && args.length == 1) {
            if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")) {
                Core.playerOnly(sender);
                return false;
            }
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.setAllowFlight(!player.getAllowFlight());
                if (player.getAllowFlight()) {
                    Core.message(CrystalCore.getInstance().messageManager.getFlightOnMessage(), player);
                } else {
                    Core.message(CrystalCore.getInstance().messageManager.getFlightOffMessage(), player);
                }
                return false;
            } else {
                if (args[0].equalsIgnoreCase("on")) {
                    player.setAllowFlight(true);
                    Core.message(CrystalCore.getInstance().messageManager.getFlightOnMessage(), player);
                    return false;
                } else if (args[0].equalsIgnoreCase("off")) {
                    player.setAllowFlight(false);
                    Core.message(CrystalCore.getInstance().messageManager.getFlightOffMessage(), player);
                    return false;
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(args[0]), player);
                        return false;
                    }

                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("on")) {
                            target.setAllowFlight(true);
                            Core.message(CrystalCore.getInstance().messageManager.getFlightOnMessage(), target);
                            Core.message(CrystalCore.getInstance().messageManager.getFlightOnOtherMessage(args[0]), player);
                            return false;
                        } else if (args[1].equalsIgnoreCase("off")) {
                            target.setAllowFlight(false);
                            Core.message(CrystalCore.getInstance().messageManager.getFlightOffMessage(), target);
                            Core.message(CrystalCore.getInstance().messageManager.getFlightOffOtherMessage(args[0]), player);
                            return false;
                        }
                    }

                    target.setAllowFlight(!target.getAllowFlight());
                    if (target.getAllowFlight()) {
                        Core.message(CrystalCore.getInstance().messageManager.getFlightOnMessage(), target);
                        if (!player.getName().equalsIgnoreCase(target.getName())) Core.message(CrystalCore.getInstance().messageManager.getFlightOnOtherMessage(args[0]), player);
                    } else {
                        Core.message(CrystalCore.getInstance().messageManager.getFlightOffMessage(), target);
                        if (!player.getName().equalsIgnoreCase(target.getName())) Core.message(CrystalCore.getInstance().messageManager.getFlightOffOtherMessage(args[0]), player);
                    }
                }
            }
        } else {
            if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")) {
                Core.playerOnly(sender);
                return false;
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(args[0]), sender);
                    return false;
                }

                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("on")) {
                        target.setAllowFlight(true);
                        Core.message(CrystalCore.getInstance().messageManager.getFlightOnMessage(), target);
                        Core.message(CrystalCore.getInstance().messageManager.getFlightOnOtherMessage(args[0]), sender);
                        return false;
                    } else if (args[1].equalsIgnoreCase("off")) {
                        target.setAllowFlight(false);
                        Core.message(CrystalCore.getInstance().messageManager.getFlightOffMessage(), target);
                        Core.message(CrystalCore.getInstance().messageManager.getFlightOffOtherMessage(args[0]), sender);
                        return false;
                    }
                }

                target.setAllowFlight(!target.getAllowFlight());
                if (target.getAllowFlight()) {
                    Core.message(CrystalCore.getInstance().messageManager.getFlightOnMessage(), target);
                    Core.message(CrystalCore.getInstance().messageManager.getFlightOnOtherMessage(args[0]), sender);
                } else {
                    Core.message(CrystalCore.getInstance().messageManager.getFlightOffMessage(), target);
                    Core.message(CrystalCore.getInstance().messageManager.getFlightOffOtherMessage(args[0]), sender);
                }
            }
        }

        return false;
    }
}
