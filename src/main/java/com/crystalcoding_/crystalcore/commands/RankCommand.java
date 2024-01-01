package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.messages.MessageManager;
import com.crystalcoding_.crystalcore.ranks.Rank;
import com.crystalcoding_.crystalcore.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {

    private final RankManager rankManager;

    public RankCommand() {
        this.rankManager = CrystalCore.getInstance().rankManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("crystalcore.ranks")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        if (args.length >= 1) {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "set":
                    if (args.length >= 3) {
                        String playerName = args[1];
                        String rankName = args[2];

                        if (player.hasPermission("crystalcore.ranks.set")) {
                            Player targetPlayer = Bukkit.getPlayer(playerName);
                            if (targetPlayer == null) {
                                player.sendMessage(CrystalCore.getInstance().messageManager.getPlayerNotFoundMessage(playerName));
                                return true;
                            }
                            if (rankManager.getRank(rankName) == null) {
                                player.sendMessage(CrystalCore.getInstance().messageManager.getRankNotFoundMessage(rankName));
                                return true;
                            }
                            rankManager.setRank(targetPlayer.getUniqueId(), rankManager.getRank(rankName));
                        } else {
                            player.sendMessage(CrystalCore.getInstance().messageManager.getNoPermissionMessage());
                            return true;
                        }
                    } else {
                            player.sendMessage(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("rank set", "<player> <rank>"));
                        }
                    break;

                case "list":
                    player.sendMessage(CrystalCore.getInstance().messageManager.getPrefix() + " These are all the ranks: ");
                    for (int i = 0; i < CrystalCore.getInstance().rankManager.getRanks().size(); i++) {
                        Rank rank = CrystalCore.getInstance().rankManager.getRanks().get(i);
                        player.sendMessage(MessageManager.color(i + ". " + rank.getPrefix() + " " + rank.getName()));
                    }
                    break;

                case "addparent":
                    if (args.length >= 3) {
                        String rankName = args[1];
                        String parentName = args[2];

                        if (player.hasPermission("crystalcore.ranks.addparent")) {
                            if (rankManager.getRank(rankName) == null) {
                                player.sendMessage(CrystalCore.getInstance().messageManager.getRankNotFoundMessage(rankName));
                                return true;
                            }
                            rankManager.addParentRank(rankName, parentName);
                            player.sendMessage("Added " + parentName + " as a parent to " + rankName);
                            return true;
                        } else {
                            player.sendMessage(CrystalCore.getInstance().messageManager.getNoPermissionMessage());
                            return true;
                        }
                    } else {
                        player.sendMessage(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("rank addparent", "<child> <parent>"));
                    }
                    break;

                case "removeparent":
                    if (args.length >= 3) {
                        String rankName = args[1];
                        String parentName = args[2];

                        if (player.hasPermission("crystalcore.ranks.removeparent")) {
                            if (rankManager.getRank(rankName) == null) {
                                player.sendMessage(CrystalCore.getInstance().messageManager.getRankNotFoundMessage(rankName));
                                return true;
                            }
                            rankManager.removeParentRank(rankName, parentName);
                            player.sendMessage("Removed " + parentName + " as a parent from " + rankName);
                            return true;
                        } else {
                            player.sendMessage(CrystalCore.getInstance().messageManager.getNoPermissionMessage());
                            return true;
                        }
                    } else {
                        player.sendMessage(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("rank removeparent", "<child> <parent>"));
                    }
                    break;

                // Other cases for more commands...

                default:
                    break;
            }
        } else {
            player.sendMessage(CrystalCore.getInstance().messageManager.getMissingArgumentsMessage("rank", "<set|list|addparent|removeparent> [arguments]"));
        }
        return true;
    }
}
