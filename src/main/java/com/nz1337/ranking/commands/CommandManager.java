package com.nz1337.ranking.commands;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.commands.subcommands.*;
import com.nz1337.ranking.configs.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager implements CommandExecutor {

    private final ArrayList<SubCommandManager> commands = new ArrayList<>();
    private final Ranking ranking;

    public CommandManager(final Ranking ranking) {
        this.ranking = ranking;
    }

    public void registerCommands() {
        this.ranking.getLauncher().getCommand("classement").setExecutor(this);
        this.commands.add(new CommandAdd());
        this.commands.add(new CommandRemove());
        this.commands.add(new CommandHelp());
        this.commands.add(new CommandReload());
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {
        if (command.getName().equalsIgnoreCase("classement")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + Lang.COMMAND_ONLY_PLAYER.get());
                    return true;
                }
                this.ranking.getGuiManager().getMainGUI().open(((Player) sender).getPlayer());
                return true;
            }
            final SubCommandManager target = this.get(args[0]);
            if (target == null) {
                sender.sendMessage(Lang.COMMAND_NO_SUB.get());
                return true;
            }
            new ArrayList<>(Arrays.asList(args)).remove(0);
            try {
                target.execute(this.ranking, sender, args);
            } catch (final Exception e) {
                sender.sendMessage(Lang.COMMAND_ERROR.get());
                e.printStackTrace();
            }
        }
        return true;
    }

    private SubCommandManager get(final String name) {
        for (final SubCommandManager sc : this.commands) {
            if (sc.name().equalsIgnoreCase(name)) return sc;
            String[] aliases;
            for (int var5 = 0; var5 < (aliases = sc.aliases()).length; ++var5)
                if (name.equalsIgnoreCase(aliases[var5])) return sc;
        }
        return null;
    }
}