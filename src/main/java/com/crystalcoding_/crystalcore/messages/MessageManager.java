package com.crystalcoding_.crystalcore.messages;

import com.crystalcoding_.crystalcore.CrystalCore;
import com.crystalcoding_.crystalcore.ranks.Rank;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MessageManager {
    private final String Prefix;

    // Join/Leave messages
    private final String JoinMessage;
    private final String LeaveMessage;
    private final String SilentJoinMessage;
    private final String SilentLeaveMessage;

    // Vanish messages
    private final String SelfVanishMessage;
    private final String SelfUnvanishMessage;
    private final String OtherVanishMessage;
    private final String OtherUnvanishMessage;

    // Rank messages
    private final String RankChangedMessage;
    private final String RankChangedOtherMessage;
    private final String TempRankChangedMessage;
    private final String TempRankChangedOtherMessage;
    private final String RankDeletedMessage;

    // Nickname messages
    private final String NicknameChangedMessage;
    private final String NicknameResetMessage;

    // Message command Messages
    private final String DirectMessageSentMessage;
    private final String DirectMessageReceivedMessage;

    // Staffchat related Messages
    private final String StaffChatEnabledMessage;
    private final String StaffChatDisabledMessage;
    private final String StaffChatMessage;


    // Command error messages
    private final String PlayerOnlyMessage;
    private final String NoPermissionMessage;
    private final String MissingArgumentsMessage;
    private final String RankNotFoundMessage;
    private final String RankExistsMessage;
    private final String RankIsDefaultMessage;
    private final String RankAlreadyDefaultMessage;
    private final String PlayerNotFoundMessage;
    private final String NoColorCodesMessage;
    private final String PermissionNotFoundMessage;
    private final String PermissionAlreadyFoundMessage;
    private final String SomethingWentWrongMessage;

    // Command Responses
    private final String GamemodeChangedMessage;
    private final String GamemodeChangeOtherMessage;
    private final String RankCreatedMessage;
    private final String RankDeletedCMDMessage;
    private final String RankPrefixChangedMessage;
    private final String RankPermissionAddedMessage;
    private final String RankPermissionRemovedMessage;
    private final String DefaultRankChangedMessage;
    private final String HealedMessage;
    private final String HealedOtherMessage;
    private final String FeedMessage;
    private final String FeedOtherMessage;
    private final String FlightOnMessage;
    private final String FlightOnOtherMessage;
    private final String FlightOffMessage;
    private final String FlightOffOtherMessage;

    public MessageManager(CrystalCore plugin) {
        FileConfiguration config = plugin.getConfig();

        Prefix = color(getFromConfig("messages.prefix", config));

        // Join/Leave messages
        JoinMessage = color(getFromConfig("messages.JoinMessage", config));
        LeaveMessage = color(getFromConfig("messages.LeaveMessage", config));
        SilentJoinMessage = color(getFromConfig("messages.SilentJoinMessage", config));
        SilentLeaveMessage = color(getFromConfig("messages.SilentLeaveMessage", config));

        // Vanish messages
        SelfVanishMessage = color(getFromConfig("messages.SelfVanishMessage", config));
        SelfUnvanishMessage = color(getFromConfig("messages.SelfUnvanishMessage", config));
        OtherVanishMessage = color(getFromConfig("messages.OtherVanishMessage", config));
        OtherUnvanishMessage = color(getFromConfig("messages.OtherUnvanishMessage", config));

        // Rank messages
        RankChangedMessage = color(getFromConfig("messages.RankChangedMessage", config));
        RankChangedOtherMessage = color(getFromConfig("messages.RankChangedOtherMessage", config));
        TempRankChangedMessage = color(getFromConfig("messages.TempRankChangedMessage", config));
        TempRankChangedOtherMessage = color(getFromConfig("messages.TempRankChangedOtherMessage", config));
        RankDeletedMessage = color(getFromConfig("messages.RankDeletedMessage", config));

        // Nickname messages
        NicknameChangedMessage = color(getFromConfig("messages.NicknameChangedMessage", config));
        NicknameResetMessage = color(getFromConfig("messages.NicknameResetMessage", config));
        PlayerOnlyMessage = color(getFromConfig("messages.PlayerOnlyMessage", config));

        // Message command Messages
        DirectMessageSentMessage = color(getFromConfig("messages.DirectMessageSentMessage", config));
        DirectMessageReceivedMessage = color(getFromConfig("messages.DirectMessageReceivedMessage", config));

        // Staffchat related Messages
        StaffChatEnabledMessage = color(getFromConfig("messages.StaffChatEnabledMessage", config));
        StaffChatDisabledMessage = color(getFromConfig("messages.StaffChatDisabledMessage", config));
        StaffChatMessage = color(getFromConfig("messages.StaffChatMessage", config));

        // Command error messages
        NoPermissionMessage = color(getFromConfig("messages.NoPermissionMessage", config));
        MissingArgumentsMessage = color(getFromConfig("messages.MissingArgumentsMessage", config));
        RankNotFoundMessage = color(getFromConfig("messages.RankNotFoundMessage", config));
        RankExistsMessage = color(getFromConfig("messages.RankExistsMessage", config));
        RankIsDefaultMessage = color(getFromConfig("messages.RankIsDefaultMessage", config));
        RankAlreadyDefaultMessage = color(getFromConfig("messages.RankAlreadyDefaultMessage", config));
        PlayerNotFoundMessage = color(getFromConfig("messages.PlayerNotFoundMessage", config));
        NoColorCodesMessage = color(getFromConfig("messages.NoColorCodesMessage", config));
        PermissionNotFoundMessage = color(getFromConfig("messages.PermissionNotFoundMessage", config));
        PermissionAlreadyFoundMessage = color(getFromConfig("messages.PermissionAlreadyFoundMessage", config));
        SomethingWentWrongMessage = color(getFromConfig("messages.SomethingWentWrongMessage", config));

        // Command Responses
        GamemodeChangedMessage = color(getFromConfig("messages.GamemodeChangedMessage", config));
        GamemodeChangeOtherMessage = color(getFromConfig("messages.GamemodeChangeOtherMessage", config));
        RankCreatedMessage = color(getFromConfig("messages.RankCreatedMessage", config));
        RankDeletedCMDMessage = color(getFromConfig("messages.RankDeletedCMDMessage", config));
        RankPrefixChangedMessage = color(getFromConfig("messages.RankPrefixChangedMessage", config));
        RankPermissionAddedMessage = color(getFromConfig("messages.RankPermissionAddedMessage", config));
        RankPermissionRemovedMessage = color(getFromConfig("messages.RankPermissionRemovedMessage", config));
        DefaultRankChangedMessage = color(getFromConfig("messages.DefaultRankChangedMessage", config));
        HealedMessage = color(getFromConfig("messages.HealedMessage", config));
        HealedOtherMessage = color(getFromConfig("messages.HealedOtherMessage", config));
        FeedMessage = color(getFromConfig("messages.FeedMessage", config));
        FeedOtherMessage = color(getFromConfig("messages.FeedOtherMessage", config));
        FlightOnMessage = color(getFromConfig("messages.FlightOnMessage", config));
        FlightOnOtherMessage = color(getFromConfig("messages.FlightOnOtherMessage", config));
        FlightOffMessage = color(getFromConfig("messages.FlightOffMessage", config));
        FlightOffOtherMessage = color(getFromConfig("messages.FlightOffOtherMessage", config));
    }

    String getFromConfig(String key, FileConfiguration config) {
        if (Prefix == null) {
            return config.getString(key, "");
        }
        return config.getString(key, "").replace("{prefix}", Prefix);
    }

    String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public String getPrefix() {
        return Prefix;
    }

    String getPlayerRankPrefix(Player p) {
        return CrystalCore.getInstance().rankManager.getPlayerRank(p).getPrefix();
    }

    //region Join/Leave Messages
    public String getJoinMessage(Player p) {
        return color(JoinMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getLeaveMessage(Player p) {
        return color(LeaveMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getSilentJoinMessage(Player p) {
        return color(SilentJoinMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getSilentLeaveMessage(Player p) {
        return color(SilentLeaveMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    //endregion

    //region Vanish Messages

    public String getSelfVanishMessage() {
        return SelfVanishMessage;
    }

    public String getSelfUnvanishMessage() {
        return SelfUnvanishMessage;
    }

    public String getOtherVanishMessage(Player p) {
        return color(OtherVanishMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    public String getOtherUnvanishMessage(Player p) {
        return color(OtherUnvanishMessage.replace("{player}", p.getName()).replace("{rank}", getPlayerRankPrefix(p)));
    }

    //endregion

    //region Rank Messages

    public String getRankChangedMessage(Rank r) {
        return color(RankChangedMessage.replace("{rank}", r.getName()));
    }

    public String getRankChangedOtherMessage(String player, String rank) {
        return RankChangedOtherMessage.replace("{player}", player).replace("{rank}", rank);
    }

    public String getTempRankChangedMessage(Rank r) {
        return color(TempRankChangedMessage.replace("{rank}", r.getName()));
    }

    public String getTempRankChangedOtherMessage(String player, String rank) {
        return TempRankChangedOtherMessage.replace("{player}", player).replace("{rank}", rank);
    }

    public String getRankDeletedMessage() {
        return RankDeletedMessage;
    }

    //endregion

    //region Nickname Messages

    public String getNicknameChangedMessage(String nickname) {
        return NicknameChangedMessage.replace("{nick}", nickname);
    }

    public String getNicknameResetMessage() {
        return NicknameResetMessage;
    }

    //endregion

    //region Message command Messages


    public String getDirectMessageSentMessage(String sender, String receiver, String message) {
        return DirectMessageSentMessage.replace("{sender}", sender).replace("{receiver}", receiver).replace("{message}", message);
    }

    public String getDirectMessageReceivedMessage(String sender, String receiver, String message) {
        return DirectMessageReceivedMessage.replace("{sender}", sender).replace("{receiver}", receiver).replace("{message}", message);
    }


    //endregion

    //region staffchat related

    public String getStaffChatEnabledMessage() {
        return StaffChatEnabledMessage;
    }

    public String getStaffChatDisabledMessage() {
        return StaffChatDisabledMessage;
    }

    public String getStaffChatMessage(String player, String rank, String message) {
        return StaffChatMessage.replace("{player}", player).replace("{rank}", rank).replace("{message}", message);
    }

    //endregion

    //region Command error Messages

    public String getPlayerOnlyMessage() {
        return PlayerOnlyMessage;
    }

    public String getNoPermissionMessage() {
        return NoPermissionMessage;
    }

    public String getMissingArgumentsMessage(String command) {
        return color(MissingArgumentsMessage.replace("{command}", command));
    }

    public String getMissingArgumentsMessage(String command, String args) {
        return color(getMissingArgumentsMessage(command).replace("{args}", args));
    }

    public String getRankNotFoundMessage(String rank) {
        return RankNotFoundMessage.replace("{rank}", rank);
    }

    public String getRankExistsMessage(String rank) {
        return RankExistsMessage.replace("{rank}", rank);
    }

    public String getRankIsDefaultMessage() {
        return RankIsDefaultMessage;
    }

    public String getRankAlreadyDefaultMessage() {
        return RankAlreadyDefaultMessage;
    }

    public String getPlayerNotFoundMessage(String player) {
        return PlayerNotFoundMessage.replace("{player}", player);
    }

    public String getNoColorCodesMessage() {
        return NoColorCodesMessage;
    }

    public String getPermissionNotFoundMessage(String rank, String permission) {
        return PermissionNotFoundMessage.replace("{rank}", rank).replace("{permission}", permission);
    }

    public String getPermissionAlreadyFoundMessage(String rank, String permission) {
        return PermissionAlreadyFoundMessage.replace("{rank}", rank).replace("{permission}", permission);
    }

    public String getSomethingWentWrongMessage() {
        return SomethingWentWrongMessage;
    }

    //endregion

    //region Command Responses

    public String getGamemodeChangedMessage(String gamemode) {
        return GamemodeChangedMessage.replace("{gamemode}", gamemode);
    }

    public String getGamemodeChangeOtherMessage(String player, String gamemode) {
        return GamemodeChangeOtherMessage.replace("{player}", player).replace("{gamemode}", gamemode);
    }

    public String getRankCreatedMessage(String rank) {
        return RankCreatedMessage.replace("{rank}", rank);
    }

    public String getRankDeletedCMDMessage(String rank) {
        return RankDeletedCMDMessage.replace("{rank}", rank);
    }

    public String getRankPrefixChangedMessage(String rank, String prefix) {
        return color(RankPrefixChangedMessage.replace("{rank}", rank).replace("{rankPrefix}", prefix));
    }

    public String getRankPermissionAddedMessage(String rank, String permission) {
        return RankPermissionAddedMessage.replace("{rank}", rank).replace("{permission}", permission);
    }

    public String getRankPermissionRemovedMessage(String rank, String permission) {
        return RankPermissionRemovedMessage.replace("{rank}", rank).replace("{permission}", permission);
    }

    public String getDefaultRankChangedMessage(String rank) {
        return DefaultRankChangedMessage.replace("{rank}", rank);
    }

    public String getHealedMessage() {
        return HealedMessage;
    }

    public String getHealedOtherMessage(String player) {
        return HealedOtherMessage.replace("{player}", player);
    }

    public String getFeedMessage() {
        return FeedMessage;
    }

    public String getFeedOtherMessage(String player) {
        return FeedOtherMessage.replace("{player}", player);
    }

    public String getFlightOnMessage() {
        return FlightOnMessage;
    }

    public String getFlightOnOtherMessage(String player) {
        return FlightOnOtherMessage.replace("{player}", player);
    }

    public String getFlightOffMessage() {
        return FlightOffMessage;
    }

    public String getFlightOffOtherMessage(String player) {
        return FlightOffOtherMessage.replace("{player}", player);
    }

    //endregion
}
