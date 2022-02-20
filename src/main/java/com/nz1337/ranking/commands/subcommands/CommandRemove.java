package com.nz1337.ranking.commands.subcommands;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.commands.SubCommandManager;
import com.nz1337.ranking.configs.Lang;
import com.nz1337.ranking.configs.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemove extends SubCommandManager {

    @Override
    public void execute(final Ranking ranking, final CommandSender sender, final String[] args) {
        final Settings settings = ranking.getSettings();
        final String prefix = Lang.COMMAND_PREFIX.get();
        if (!sender.hasPermission(settings.getPermission())) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        if (args.length != 4) {
            sender.sendMessage(prefix + Lang.COMMAND_REMOVE_MISSING.get());
            return;
        }
        final String uncheckedAmount = args[3];
        if (!this.isInteger(uncheckedAmount)) {
            sender.sendMessage(prefix + Lang.COMMAND_REMOVE_INVALID_VALUE.get().replace("%value%", uncheckedAmount));
            return;
        }
        final String type = args[1];
        if (!type.equalsIgnoreCase("farm") && !type.equalsIgnoreCase("pvp")) {
            sender.sendMessage(prefix + Lang.COMMAND_REMOVE_INVALID_VALUE.get().replace("%type%", type));
            return;
        }
        final Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            sender.sendMessage(prefix + Lang.COMMAND_REMOVE_OFFLINE.get().replace("%player%", args[2]));
            return;
        }
        final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(target);
        if (!fPlayer.hasFaction()) {
            sender.sendMessage(prefix + Lang.COMMAND_REMOVE_WILDERNESS.get().replace("%player%", target.getName()));
            return;
        }
        final String faction = fPlayer.getFaction().getTag();
        int amount = Integer.parseInt(uncheckedAmount);
        if (type.equalsIgnoreCase("farm") || type.equalsIgnoreCase("pvp")) {
            final String typeLower = type.toLowerCase();
            ranking.getDatabaseManager().getSqlHandler().setTable(typeLower).removePoints(faction, amount);
            sender.sendMessage(prefix + Lang.COMMAND_REMOVE_SUCCESS.get().replace("%faction%", faction).replace("%type%", typeLower).replace("%value%", String.valueOf(amount)));
        }
    }

    @Override
    public String name() {
        return "remove";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    private boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
        } catch (final NumberFormatException | NullPointerException ignore) {
            return false;
        }
        return true;
    }
}