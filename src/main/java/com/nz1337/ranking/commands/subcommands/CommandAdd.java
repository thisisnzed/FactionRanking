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

public class CommandAdd extends SubCommandManager {

    @Override
    public void execute(Ranking ranking, CommandSender sender, String[] args) {
        final Settings settings = ranking.getSettings();
        final String prefix = Lang.COMMAND_PREFIX.get();
        if (!sender.hasPermission(settings.getPermission())) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        if (args.length != 4) {
            sender.sendMessage(prefix + Lang.COMMAND_ADD_MISSING.get());
            return;
        }
        String uncheckedAmount = args[3];
        if (!isInteger(uncheckedAmount)) {
            sender.sendMessage(prefix + Lang.COMMAND_ADD_INVALID_VALUE.get().replace("%value%", uncheckedAmount));
            return;
        }
        String type = args[1];
        if (!type.equalsIgnoreCase("farm") && !type.equalsIgnoreCase("pvp")) {
            sender.sendMessage(prefix + Lang.COMMAND_ADD_INVALID_VALUE.get().replace("%type%", type));
            return;
        }
        Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            sender.sendMessage(prefix + Lang.COMMAND_ADD_OFFLINE.get().replace("%player%", args[2]));
            return;
        }
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(target);
        if (!fPlayer.hasFaction()) {
            sender.sendMessage(prefix + Lang.COMMAND_ADD_WILDERNESS.get().replace("%player%", target.getName()));
            return;
        }
        String faction = fPlayer.getFaction().getTag();
        int amount = Integer.parseInt(uncheckedAmount);
        if (type.equalsIgnoreCase("farm") || type.equalsIgnoreCase("pvp")) {
            String typeLower = type.toLowerCase();
            ranking.getDatabaseManager().getSqlHandler().setTable(typeLower).addPoints(faction, amount);
            sender.sendMessage(prefix + Lang.COMMAND_ADD_SUCCESS.get().replace("%faction%", faction).replace("%type%", typeLower).replace("%value%", String.valueOf(amount)));
        }
    }

    @Override
    public String name() {
        return "add";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}