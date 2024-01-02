package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
import com.crystalcoding_.crystalcore.CrystalCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player)) {
            Core.playerOnly(sender);
            return false;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("crystalcore.nick")) {
            Core.noPermission(p);
            return false;
        }

        if (args.length == 0) {
            Core.message(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("nick", "<nickname|reset>"), p);
            return false;
        }

        String nickname = args[0];

        if (nickname.equalsIgnoreCase("reset")) {
            CrystalCore.getInstance().nameManager.resetNickName(p);
            Core.message(CrystalCore.getInstance().messageManager.getNicknameResetMessage(), p);
        } else {
            CrystalCore.getInstance().nameManager.setNickName(p, nickname);
            Core.message(CrystalCore.getInstance().messageManager.getNicknameChangedMessage(nickname), p);
        }

        return false;
    }
}
