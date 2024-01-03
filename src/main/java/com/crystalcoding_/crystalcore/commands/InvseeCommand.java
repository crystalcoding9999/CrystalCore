package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            if (!sender.hasPermission("crystalcore.invsee")) {
                Core.noPermission(sender);
                return false;
            }
        } else {
            Core.playerOnly(sender);
            return false;
        }

        if (args.length == 0) {
            Core.message(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("invsee", "<player>"), sender);
            return false;
        }

        Player player = (Player) sender;
        Player targetPlayer = Bukkit.getPlayer(args[0]);

        if (targetPlayer == null) {
            Core.message(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(args[0]), player);
            return false;
        }

        player.openInventory(targetPlayer.getInventory());

        return false;
    }
}
