package com.nz1337.ranking.commands.subcommands;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.commands.SubCommandManager;
import com.nz1337.ranking.configs.Lang;
import com.nz1337.ranking.utils.PluginHelper;
import org.bukkit.command.CommandSender;

public class CommandReload extends SubCommandManager {

    @Override
    public void execute(final Ranking ranking, final CommandSender sender, final String[] args) {
        if (!sender.hasPermission(ranking.getSettings().getPermission())) {
            sender.sendMessage(Lang.COMMAND_NO_PERMISSION.get());
            return;
        }
        PluginHelper.unload(sender, "Ranking");
        PluginHelper.load(sender, "Ranking", ranking.getLauncher());
    }

    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}