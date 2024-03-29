package com.crystalcoding_.crystalcore.commands;

import com.crystalcoding_.crystalcore.Core;
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
    private final MessageManager messageManager;

    public RankCommand() {
        this.rankManager = CrystalCore.getInstance().rankManager;
        this.messageManager = CrystalCore.getInstance().messageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Core.playerOnly(sender);
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("crystalcore.ranks")) {
            Core.noPermission(player);
            return true;
        }

        if (args.length >= 1) {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "set":
                    if (player.hasPermission("crystalcore.ranks.set")) {
                        if (args.length >= 3) {
                            String playerName = args[1];
                            String rankName = args[2];

                            Player targetPlayer = Bukkit.getPlayer(playerName);
                            if (targetPlayer == null) {
                                Core.message(messageManager.getPlayerNotFoundMessage(playerName), player);
                                return true;
                            }

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return true;
                            }

                            rankManager.setRank(targetPlayer.getUniqueId(), rankManager.getRank(rankName), true);
                            Core.message(messageManager.getRankChangedOtherMessage(playerName, rankName), player);
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<player> <rank>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                        return true;
                    }
                    break;

                case "list":
                    Core.message(messageManager.getPrefix() + " These are all the ranks: ", player);
                    for (int i = 0; i < rankManager.getRanks().size(); i++) {
                        Rank rank = rankManager.getRanks().get(i);
                        Core.message(Core.color(i + ". " + rank.getPrefix() + " " + rank.getName()), player);
                    }
                    break;

                case "info":
                    if (player.hasPermission("crystalcore.ranks.info")) {
                        if (args.length >= 2) {
                            String rankName = args[1];

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return false;
                            }

                            Rank rank = rankManager.getRank(rankName);

                            Core.message(messageManager.getPrefix() + " &aThis is the information for&r " + rank.getName() + "&a:", player);
                            if (rankManager.isDefault(rankName)) Core.message("&aThis rank is the default rank!", player);
                            Core.message("&aName: &r" + rankName, player);
                            Core.message("&aPrefix: &r" + rank.getPrefix(), player);
                            if (rank.getParents().isEmpty()) {
                                Core.message("&aParents: &cNo Parents", player);
                            } else {
                                if (rank.getParents().size() == 1) {
                                    Core.message("&aParent: &r" + rank.getParents().get(0), player);
                                } else {
                                    Core.message("&aParents: ", player);
                                    for (int p = 0; p < rank.getParents().size(); p++) {
                                        Core.message("&r" + (p+1) + ". &r" + rank.getParents().get(p), player);
                                    }
                                }
                            }
                            if (rank.getPermissions().isEmpty()) {
                                Core.message("&aPermissions: &cNo Permissions", player);
                            } else {
                                Core.message("&aPermissions: ", player);
                                for (int p = 0; p < rank.getPermissions().size(); p++) {
                                    String permission = rank.getPermissions().get(p);
                                    if (permission.startsWith("!")) {
                                        Core.message("&r" + (p+1) + ". &c" + rank.getPermissions().get(p), player);
                                    } else {
                                        Core.message("&r" + (p+1) + ". &a" + rank.getPermissions().get(p), player);
                                    }
                                }
                            }
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<rank>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                    }
                    break;

                case "addparent":
                    if (player.hasPermission("crystalcore.ranks.addparent")) {
                        if (args.length >= 3) {
                            String rankName = args[1];
                            String parentName = args[2];

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return true;
                            }
                            rankManager.addParentRank(rankName, parentName);
                            Core.message("Added " + parentName + " as a parent to " + rankName, player);
                            return true;
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<child> <parent>"), player);
                        }
                    }else {
                        Core.noPermission(player);
                        return true;
                    }
                    break;

                case "removeparent":
                    if (player.hasPermission("crystalcore.ranks.removeparent")) {
                        if (args.length >= 3) {
                            String rankName = args[1];
                            String parentName = args[2];

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return true;
                            }

                            rankManager.removeParentRank(rankName, parentName);
                            Core.message("Removed " + parentName + " as a parent from " + rankName, player);
                            return true;
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<child> <parent>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                        return true;
                    }
                    break;

                case "create":
                    if (player.hasPermission("crystalcore.ranks.create")) {
                        if (args.length >= 2) {
                            String rankName = args[1].toLowerCase();

                            if (rankManager.getRank(rankName) != null) {
                                Core.message(messageManager.getRankExistsMessage(rankName), player);
                                return false;
                            }

                            if (rankName.contains("&")) {
                                Core.message(messageManager.getNoColorCodesMessage(), player);
                                return false;
                            }

                            Rank newRank = new Rank(rankName, rankName);
                            rankManager.addRank(newRank);
                            Core.message(messageManager.getRankCreatedMessage(newRank.getName()), player);
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<name>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                    }
                    break;

                case "delete":
                case "remove":
                    if (player.hasPermission("crystalcore.ranks.delete")) {
                        if (args.length >= 2) {
                            String rankName = args[1].toLowerCase();

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return false;
                            }

                            if (rankManager.isDefault(rankName)) {
                                Core.message(messageManager.getRankIsDefaultMessage(), player);
                                return false;
                            }

                            rankManager.removeRank(rankManager.getRank(rankName));
                            Core.message(messageManager.getRankDeletedCMDMessage(rankName), player);
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<rank>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                    }
                    break;

                case "settemp":
                case "settemporary":
                    if (player.hasPermission("crystalcore.rank.set.temporary")) {
                        if (args.length >= 3) {
                            String playerName = args[1];
                            String rankName = args[2];

                            Player targetPlayer = Bukkit.getPlayer(playerName);
                            if (targetPlayer == null) {
                                Core.message(messageManager.getPlayerNotFoundMessage(playerName), player);
                                return true;
                            }
                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return true;
                            }
                            rankManager.setTemporaryRank(targetPlayer.getUniqueId(), rankManager.getRank(rankName));
                            Core.message(messageManager.getTempRankChangedOtherMessage(playerName, rankName), player);
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<player> <rank>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                    }
                    break;
                case "setprefix":
                    if (player.hasPermission("crystalcore.ranks.setprefix")) {
                        if (args.length >= 3) {
                            String rankName = args[1].toLowerCase();
                            String newPrefix = Core.color(args[2]);

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return false;
                            }

                            rankManager.setPrefix(rankName, Core.color(newPrefix));
                            Core.message(messageManager.getRankPrefixChangedMessage(rankName, newPrefix).replace("{rank}", rankName), player);
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<rank> <prefix>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                    }
                    break;
                case "addpermission":
                    if (player.hasPermission("crystalcore.ranks.permissions.add")) {
                        if (args.length >= 3) {
                            String rankName = args[1].toLowerCase();
                            String permission = args[2].toLowerCase();

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return false;
                            }

                            Rank targetRank = rankManager.getRank(rankName);

                            if (targetRank.getPermissions().contains(permission)) {
                                Core.message(messageManager.getPermissionAlreadyFoundMessage(rankName, permission), player);
                                return false;
                            }

                            targetRank.getPermissions().add(permission);

                            rankManager.addPermission(permission, rankName);
                            Core.message(messageManager.getRankPermissionAddedMessage(rankName, permission), player);
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand + "<rank> <permission>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                    }
                    break;
                case "removepermission":
                    if (player.hasPermission("crystalcore.ranks.permissions.remove")) {
                        if (args.length >= 3) {
                            String rankName = args[1].toLowerCase();
                            String permission = args[2].toLowerCase();

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return false;
                            }

                            Rank targetRank = rankManager.getRank(rankName);

                            if (!targetRank.getPermissions().contains(permission)) {
                                Core.message(messageManager.getPermissionNotFoundMessage(rankName, permission), player);
                                return false;
                            }

                            targetRank.getPermissions().remove(permission);

                            rankManager.removePermission(permission, rankName);
                            Core.message(messageManager.getRankPermissionRemovedMessage(rankName, permission), player);
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<rank> <permission>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                    }
                    break;
                case "setdefault":
                    if (player.hasPermission("crystalcore.ranks.setdefault")) {
                        if (args.length >= 2) {
                            String rankName = args[1].toLowerCase();

                            if (rankManager.getRank(rankName) == null) {
                                Core.message(messageManager.getRankNotFoundMessage(rankName), player);
                                return false;
                            }

                            if (rankManager.isDefault(rankName)) {
                                Core.message(messageManager.getRankAlreadyDefaultMessage(), player);
                                return false;
                            }

                            rankManager.setDefaultRank(rankName);
                            Core.message(messageManager.getDefaultRankChangedMessage(rankName), player);
                        } else {
                            Core.message(messageManager.getMissingArgumentsMessage("rank " + subCommand, "<rank>"), player);
                        }
                    } else {
                        Core.noPermission(player);
                    }
                    break;

                // Other cases for more commands...

                default:
                    break;
            }
        } else {
            Core.message(messageManager.getMissingArgumentsMessage("rank", "<addparent|create|delete|list|remove|removeparent|set> [arguments]"), player);
        }
        return true;
    }
}
