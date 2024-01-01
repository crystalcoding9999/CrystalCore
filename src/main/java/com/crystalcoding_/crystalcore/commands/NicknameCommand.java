package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(CrystalCore.getInstance().messageManager.getPlayerOnlyMessage());
            return false;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("crystalcore.nick")) {
            p.sendMessage(CrystalCore.getInstance().messageManager.getNoPermissionMessage());
            return false;
        }

        if (args.length == 0) {
            p.sendMessage(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("nick", "<nickname|reset>"));
            return false;
        }

        String nickname = args[0];

        if (nickname.equalsIgnoreCase("reset")) {
            CrystalCore.getInstance().nameManager.resetNickName(p);
            p.sendMessage(CrystalCore.getInstance().messageManager.getNicknameResetMessage());
        } else {
            CrystalCore.getInstance().nameManager.setNickName(p, nickname);
            p.sendMessage(CrystalCore.getInstance().messageManager.getNicknameChangedMessage(nickname));
        }

        return false;
    }
}
